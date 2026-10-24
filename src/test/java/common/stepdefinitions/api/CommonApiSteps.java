package common.stepdefinitions.api;

import static org.junit.Assert.*;

import common.context.ScenarioContext;
import io.cucumber.java.en.Then;
import io.restassured.http.ContentType;

public class CommonApiSteps {
  private final ScenarioContext context;

  public CommonApiSteps(ScenarioContext context) {
    this.context = context;
  }

  @Then("the response status should be {int}")
  public void status(int expected) {
    context.response.then().statusCode(expected);
  }

  @Then("the response should contain valid JSON")
  public void array() {
    context.response.then().contentType(ContentType.JSON);
    assertNotNull("Expected JSON content", context.response.getBody().asString());
    Object root = context.response.jsonPath().get();
    assertTrue("Expected JSON object or array", root instanceof java.util.Map || root instanceof java.util.List);
  }
}
