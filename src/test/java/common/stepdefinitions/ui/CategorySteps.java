package common.stepdefinitions.ui;

import static org.junit.Assert.*;

import common.context.ScenarioContext;
import common.pages.*;
import common.utils.TestDataGenerator;
import io.cucumber.java.en.*;

public class CategorySteps {
  private final ScenarioContext context;
  private final common.api.client.FixtureClient fixtures;

  public CategorySteps(ScenarioContext context, common.api.client.FixtureClient fixtures) {
    this.context = context;
    this.fixtures = fixtures;
  }

  private CategoryListPage list() {
    return new CategoryListPage(context.driver);
  }

  private AddCategoryPage form() {
    return new AddCategoryPage(context.driver);
  }

  @Given("a category exists for this UI scenario")
  public void seedCategory() {
    fixtures.category();
  }

  @When("I navigate to the category list page")
  public void open() {
    list().navigateToCategoryList();
  }

  @Then("Category list table should be displayed")
  public void table() {
    assertTrue(list().isCategoryTableDisplayed());
  }

  @When("I open the add category form")
  public void add() {
    list().clickAddCategory();
  }

  @When("Admin adds a new main category")
  public void create() {
    context.categoryName = TestDataGenerator.categoryName();
    // Register cleanup by an exact generated name before submission, even if a later assertion
    // fails.
    context.uiCategoryNames.add(context.categoryName);
    list().clickAddCategory();
    form().enterCategoryName(context.categoryName);
    form().clickSave();
    new CategoryListPage(context.driver).awaitPath("/ui/categories");
  }

  @Then("New category should be created successfully")
  public void created() {
    list().search(context.categoryName);
    assertTrue("Created category not in list", list().hasCategory(context.categoryName));
  }

  @When("I submit category name {string}")
  public void submit(String name) {
    form().enterCategoryName(name);
    form().clickSave();
  }

  @Then("the category name errors should include {string}")
  public void error(String expected) {
    assertTrue(
        "Expected validation: " + expected,
        form().getErrorMessage().lines().map(String::trim).anyMatch(expected::equals));
  }

  @When("I cancel adding a category")
  public void cancel() {
    form().cancel();
  }

  @Then("the category add action should be hidden")
  public void hidden() {
    assertTrue(list().addHidden());
  }

  @Then("the category delete actions should be disabled or absent")
  public void disabled() {
    list().search(context.categoryName);
    assertTrue(list().hasCategory(context.categoryName));
    assertTrue(list().deleteDisabledOrAbsent());
  }

  @When("I open the category create URL directly")
  public void direct() {
    list().openCreateDirectly();
  }

  @Then("category creation access should be denied")
  public void denied() {
    assertTrue(list().accessDenied());
  }

  @When("I search for a category name that does not exist")
  public void search() {
    list().search("NO_MATCH_" + java.util.UUID.randomUUID());
  }

  @Then("the category empty message should be displayed")
  public void empty() {
    assertEquals("No category found", list().emptyMessage());
  }
}
