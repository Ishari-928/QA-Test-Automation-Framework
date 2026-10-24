package common.api.client;

import static io.restassured.RestAssured.given;

import common.api.spec.RequestSpecFactory;
import common.config.ConfigReader;
import io.restassured.response.Response;
import java.util.Map;

public class AuthApiClient {
  public Response login(String username, String password) {
    return given()
        .spec(RequestSpecFactory.create(null))
        .body(Map.of("username", username, "password", password))
        .post("/api/auth/login");
  }

  public String tokenFor(String role) {
    Response r = login(ConfigReader.get(role + ".username"), ConfigReader.get(role + ".password"));
    r.then().statusCode(200);
    String token = r.jsonPath().getString("token");
    if (token == null || token.isBlank()) throw new AssertionError("Login response has no token");
    if (!"Bearer".equals(r.jsonPath().getString("tokenType"))) throw new AssertionError("Expected Bearer token type");
    return token;
  }
}
