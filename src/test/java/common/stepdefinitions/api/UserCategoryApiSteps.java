package common.stepdefinitions.api;

import static org.junit.Assert.*;

import common.api.client.*;
import common.context.ScenarioContext;
import common.utils.TestDataGenerator;
import io.cucumber.java.en.*;
import java.util.Map;

public class UserCategoryApiSteps {
  private final ScenarioContext context;
  private final ResourceApiClient client;
  private final FixtureClient fixtures;
  private final AuthApiClient auth;

  public UserCategoryApiSteps(
      ScenarioContext context,
      ResourceApiClient client,
      FixtureClient fixtures,
      AuthApiClient auth) {
    this.context = context;
    this.client = client;
    this.fixtures = fixtures;
    this.auth = auth;
  }

  @Given("I have a user API token")
  public void token() {
    context.token = auth.tokenFor("user");
  }

  @When("the user retrieves its scenario category")
  public void get() {
    context.response = client.get("/api/categories/" + context.categoryId, context.token);
  }

  @When("the user attempts to create a category")
  public void create() {
    fixtures.ensureAdmin();
    context.response =
        client.post(
            "/api/categories", Map.of("name", TestDataGenerator.categoryName()), context.token);
    if (context.response.statusCode() == 201)
      context.cleanupPaths.add("/api/categories/" + FixtureClient.id(context.response));
  }

  @When("the user attempts to edit its scenario category")
  public void edit() {
    context.response =
        client.put(
            "/api/categories/" + context.categoryId, Map.of("name", "QAChanged"), context.token);
  }

  @When("the user attempts to delete its scenario category")
  public void delete() {
    context.response = client.delete("/api/categories/" + context.categoryId, context.token);
  }

  @Then("the scenario category should remain unchanged")
  public void unchanged() {
    var r = client.get("/api/categories/" + context.categoryId, context.adminToken);
    r.then().statusCode(200);
    assertEquals(context.categoryName, r.jsonPath().getString("name"));
  }
}
