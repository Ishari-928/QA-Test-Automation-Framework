@ui @admin @categories
Feature: Admin category management

  @TC_ADM_UI_CAT_05
  Scenario: Cancel category creation
    Given I am logged in as "admin"
    When I navigate to the category list page
    And I open the add category form
    And I cancel adding a category
    Then Category list table should be displayed
