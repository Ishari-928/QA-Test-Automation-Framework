package common.stepdefinitions.api;

import static org.junit.Assert.*;

import common.api.client.AuthApiClient;
import common.config.ConfigReader;
import common.context.ScenarioContext;
import io.cucumber.java.en.*;

public class AuthApiSteps {
  private final ScenarioContext context;
  private final AuthApiClient client;

  public AuthApiSteps(ScenarioContext context, AuthApiClient client) {
    this.context = context;
    this.client = client;
  }

  @When("I request an API token as {string}")
  public void role(String role) {
    context.response =
        client.login(ConfigReader.get(role + ".username"), ConfigReader.get(role + ".password"));
  }

  @When("I request an API token with username {string} and password {string}")
  public void credentials(String u, String p) {
    context.response = client.login(u, p);
  }

  @Then("the API login should return a nonempty token")
  public void token() {
    context.response.then().statusCode(200);
    String token = context.response.jsonPath().getString("token");
    assertNotNull("Token missing", token);
    assertFalse("Token empty", token.isBlank());
    assertEquals("Bearer", context.response.jsonPath().getString("tokenType"));
  }

  @Then("the API login should be rejected")
  public void rejected() {
    int code = context.response.statusCode();
    assertTrue("Expected rejected login (4xx), received " + code, code >= 400 && code < 500);
    assertNull("Rejected login must not return a token", context.response.jsonPath().getString("token"));
  }
}
