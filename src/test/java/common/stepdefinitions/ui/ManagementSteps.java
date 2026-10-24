package common.stepdefinitions.ui;

import static org.junit.Assert.*;

import common.context.ScenarioContext;
import common.pages.ManagementPage;
import io.cucumber.java.en.*;
import java.util.List;

public class ManagementSteps {
  private final ScenarioContext context;
  private ManagementPage page;
  private String module;

  public ManagementSteps(ScenarioContext context) {
    this.context = context;
  }

  @When("I open the {string} list")
  public void open(String module) {
    this.module = module;
    page = new ManagementPage(context.driver);
    page.open(module);
  }

  @Then("the management table should have its expected columns")
  public void table() {
    assertTrue(page.tableVisible());
    List<String> expected =
        switch (module) {
          case "categories" -> List.of("ID", "Name", "Parent", "Actions");
          case "plants" -> List.of("Name", "Category", "Price", "Stock", "Actions");
          default -> List.of("Plant", "Quantity", "Total Price", "Sold At", "Actions");
        };
    List<String> actual = page.headers();
    for (String column : expected)
      assertTrue(
          "Missing column " + column + " in " + actual,
          actual.stream().anyMatch(s -> s.contains(column)));
  }

  @When("I open its create form")
  public void form() {
    page.openForm();
  }

  @Then("the create form should show its required controls")
  public void controls() {
    assertTrue(page.formVisible());
  }

  @When("I cancel the create form")
  public void cancel() {
    page.cancel();
  }

  @Then("the management list should be displayed")
  public void list() {
    assertTrue(page.tableVisible());
  }

  @When("I submit the empty create form")
  public void empty() {
    page.submitEmpty();
  }

  @Then("the form validation should include {string}")
  public void validation(String text) {
    assertTrue("Expected validation: " + text, page.validationText().contains(text));
  }

  @Then("the create action should be hidden")
  public void hidden() {
    assertTrue(page.createLinkAbsent());
  }
}
