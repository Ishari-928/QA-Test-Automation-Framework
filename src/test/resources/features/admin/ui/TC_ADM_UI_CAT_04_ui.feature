@ui @admin @categories
Feature: Admin category management

  @TC_ADM_UI_CAT_04
  Scenario: Reject a short category name
    Given I am logged in as "admin"
    When I navigate to the category list page
    And I open the add category form
    And I submit category name "AB"
    Then the category name errors should include "Category name must be between 3 and 10 characters"
