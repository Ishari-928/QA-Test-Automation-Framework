package common.stepdefinitions.ui;

import static org.junit.Assert.*;

import common.config.ConfigReader;
import common.context.ScenarioContext;
import common.pages.LoginPage;
import io.cucumber.java.en.*;

public class LoginSteps {
  private final ScenarioContext context;

  public LoginSteps(ScenarioContext context) {
    this.context = context;
  }

  private LoginPage page() {
    return new LoginPage(context.driver);
  }

  @Given("I am logged in as {string}")
  public void loginAs(String role) {
    if (!role.equals("admin") && !role.equals("user")) throw new IllegalArgumentException(role);
    page().open();
    page().submit(ConfigReader.get(role + ".username"), ConfigReader.get(role + ".password"));
    page().awaitDashboard();
  }

  @Given("I open the login page")
  public void open() {
    page().open();
  }

  @When("I submit login with username {string} and password {string}")
  public void submit(String u, String p) {
    page().submit(u, p);
  }

  @Then("the dashboard should be displayed")
  public void dashboard() {
    page().awaitDashboard();
  }

  @Then("the login error should be {string}")
  public void error(String text) {
    assertEquals(text, page().error());
  }

  @Then("the {string} login validation should be {string}")
  public void fieldError(String field, String text) {
    assertEquals(text, page().fieldError(field));
  }
}
