@ui @admin @categories
Feature: Admin category management

  @TC_ADM_UI_CAT_02
  Scenario: Create a main category
    Given I am logged in as "admin"
    When I navigate to the category list page
    And Admin adds a new main category
    Then New category should be created successfully
