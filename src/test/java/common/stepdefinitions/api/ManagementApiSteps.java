package common.stepdefinitions.api;

import static org.junit.Assert.*;

import common.api.client.*;
import common.context.ScenarioContext;
import io.cucumber.java.en.*;
import java.util.Map;

public class ManagementApiSteps {
  private final ScenarioContext context;
  private final ResourceApiClient client;
  private final FixtureClient fixtures;

  public ManagementApiSteps(
      ScenarioContext context, ResourceApiClient client, FixtureClient fixtures) {
    this.context = context;
    this.client = client;
    this.fixtures = fixtures;
  }

  @Given("I have an admin API token")
  public void auth() {
    fixtures.ensureAdmin();
    context.token = context.adminToken;
  }

  @When("I request the {string} API list")
  public void list(String module) {
    context.response = client.get(path(module), context.token);
  }

  @When("I request the {string} API list without authentication")
  public void anonymous(String module) {
    context.response = client.get(path(module), null);
  }

  @When("I create a valid category through the API")
  public void createCategory() {
    fixtures.category();
    context.response = client.get("/api/categories/" + context.categoryId, context.adminToken);
  }

  @Then("the created category should have the submitted name")
  public void categoryName() {
    context.response.then().statusCode(200);
    assertEquals(context.categoryName, context.response.jsonPath().getString("name"));
    assertEquals(context.categoryId, ((Number) context.response.jsonPath().get("id")).longValue());
  }

  @When("I submit a category with a blank name")
  public void blankCategory() {
    context.response = client.post("/api/categories", Map.of("name", ""), context.token);
    trackUnexpectedCreation("categories");
  }

  @Given("a scenario-owned category exists")
  public void category() {
    fixtures.category();
  }

  @When("I retrieve that category by ID")
  public void getCategory() {
    context.response = client.get("/api/categories/" + context.categoryId, context.token);
  }

  @When("I create a valid plant through the API")
  public void createPlant() {
    fixtures.plant();
    context.response = client.get("/api/plants/" + context.plantId, context.adminToken);
  }

  @Then("the created plant should match its submitted values")
  public void plantValues() {
    context.response.then().statusCode(200);
    assertEquals(context.plantName, context.response.jsonPath().getString("name"));
    assertEquals(100.0, ((Number) context.response.jsonPath().get("price")).doubleValue(), 0.001);
    assertEquals(10, ((Number) context.response.jsonPath().get("quantity")).intValue());
  }

  @Given("a scenario-owned plant exists")
  public void plant() {
    fixtures.plant();
  }

  @When("I submit a plant with a negative price")
  public void negativePrice() {
    context.response =
        client.post(
            "/api/plants/category/" + context.subCategoryId,
            Map.of("name", common.utils.TestDataGenerator.plantName(), "price", -1, "quantity", 10),
            context.token);
    trackUnexpectedCreation("plants");
  }

  @When("I retrieve that plant by ID")
  public void getPlant() {
    context.response = client.get("/api/plants/" + context.plantId, context.token);
  }

  @When("I sell two units of a scenario-owned plant")
  public void sell() {
    fixtures.plant();
    context.response = client.sell(context.plantId, 2, context.token);
    if (context.response.statusCode() == 201) {
      context.saleId = FixtureClient.id(context.response);
      context.cleanupPaths.add("/api/sales/" + context.saleId);
    }
  }

  @Then("the sale should total 200 and the remaining stock should be 8")
  public void saleValues() {
    context.response.then().statusCode(201);
    assertEquals(2, ((Number) context.response.jsonPath().get("quantity")).intValue());
    assertEquals(
        200.0, ((Number) context.response.jsonPath().get("totalPrice")).doubleValue(), 0.001);
    var plant = client.get("/api/plants/" + context.plantId, context.token);
    plant.then().statusCode(200);
    assertEquals(8, ((Number) plant.jsonPath().get("quantity")).intValue());
  }

  @When("I attempt to sell more units than its stock")
  public void tooMany() {
    context.response = client.sell(context.plantId, 11, context.token);
    trackUnexpectedCreation("sales");
  }

  @Then("the scenario plant stock should remain 10")
  public void unchanged() {
    var r = client.get("/api/plants/" + context.plantId, context.token);
    r.then().statusCode(200);
    assertEquals(10, ((Number) r.jsonPath().get("quantity")).intValue());
  }

  @Given("a scenario-owned sale exists")
  public void sale() {
    fixtures.sale();
  }

  @When("I retrieve that sale by ID")
  public void getSale() {
    context.response = client.get("/api/sales/" + context.saleId, context.token);
  }

  @Then("the retrieved sale should match its created values")
  public void retrievedSale() {
    context.response.then().statusCode(200);
    assertEquals(context.saleId, ((Number) context.response.jsonPath().get("id")).longValue());
    assertEquals(2, ((Number) context.response.jsonPath().get("quantity")).intValue());
  }

  private void trackUnexpectedCreation(String module) {
    if (context.response.statusCode() == 201)
      context.cleanupPaths.add("/api/" + module + "/" + FixtureClient.id(context.response));
  }

  private String path(String module) {
    if (!java.util.List.of("categories", "plants", "sales").contains(module))
      throw new IllegalArgumentException(module);
    return "/api/" + module;
  }
}
