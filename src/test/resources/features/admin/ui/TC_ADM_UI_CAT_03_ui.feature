@ui @admin @categories
Feature: Admin category management

  @TC_ADM_UI_CAT_03
  Scenario: Category name is mandatory
    Given I am logged in as "admin"
    When I navigate to the category list page
    And I open the add category form
    And I submit category name ""
    Then the category name errors should include "Category name is required"
