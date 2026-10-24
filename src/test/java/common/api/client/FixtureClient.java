package common.api.client;

import common.context.ScenarioContext;
import common.utils.TestDataGenerator;
import io.restassured.response.Response;
import java.util.Map;

/** Creates only scenario-owned records. Tracks each success immediately for cleanup. */
public class FixtureClient {
  private final ScenarioContext context;
  private final ResourceApiClient client;

  public FixtureClient(ScenarioContext context, ResourceApiClient client) {
    this.context = context;
    this.client = client;
  }

  public void ensureAdmin() {
    if (context.adminToken == null) context.adminToken = new AuthApiClient().tokenFor("admin");
  }

  public void category() {
    ensureAdmin();
    context.categoryName = TestDataGenerator.categoryName();
    Response r =
        client.post("/api/categories", Map.of("name", context.categoryName), context.adminToken);
    r.then().statusCode(201);
    context.categoryId = id(r);
    context.cleanupPaths.add("/api/categories/" + context.categoryId);
  }

  public void plant() {
    category();
    Response sub =
        client.post(
            "/api/categories",
            Map.of(
                "name",
                TestDataGenerator.categoryName(),
                "parent",
                Map.of("id", context.categoryId)),
            context.adminToken);
    sub.then().statusCode(201);
    context.subCategoryId = id(sub);
    context.cleanupPaths.add("/api/categories/" + context.subCategoryId);
    context.plantName = TestDataGenerator.plantName();
    Response plant =
        client.post(
            "/api/plants/category/" + context.subCategoryId,
            Map.of("name", context.plantName, "price", 100.0, "quantity", 10),
            context.adminToken);
    plant.then().statusCode(201);
    context.plantId = id(plant);
    context.cleanupPaths.add("/api/plants/" + context.plantId);
  }

  public void sale() {
    plant();
    Response sale = client.sell(context.plantId, 2, context.adminToken);
    sale.then().statusCode(201);
    context.saleId = id(sale);
    context.cleanupPaths.add("/api/sales/" + context.saleId);
  }

  public static long id(Response response) {
    Object value = response.jsonPath().get("id");
    if (!(value instanceof Number)) throw new AssertionError("Expected numeric id in response");
    return ((Number) value).longValue();
  }
}
