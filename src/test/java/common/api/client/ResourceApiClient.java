package common.api.client;

import static io.restassured.RestAssured.given;

import common.api.spec.RequestSpecFactory;
import io.restassured.response.Response;

/** Keep transport details out of Cucumber steps. Authentication is per request. */
public class ResourceApiClient {
  public Response get(String path, String token) {
    return given().spec(RequestSpecFactory.create(token)).get(path);
  }

  public Response post(String path, Object body, String token) {
    return given().spec(RequestSpecFactory.create(token)).body(body).post(path);
  }

  public Response sell(long plantId, int quantity, String token) {
    return given()
        .spec(RequestSpecFactory.create(token))
        .queryParam("quantity", quantity)
        .post("/api/sales/plant/" + plantId);
  }

  public Response put(String path, Object body, String token) {
    return given().spec(RequestSpecFactory.create(token)).body(body).put(path);
  }

  public Response delete(String path, String token) {
    return given().spec(RequestSpecFactory.create(token)).delete(path);
  }
}
