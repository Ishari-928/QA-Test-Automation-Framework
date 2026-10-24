@ui @admin @categories
Feature: Admin category management

  @TC_ADM_UI_CAT_01
  Scenario: Verify category list displays correctly
    Given I am logged in as "admin"
    When I navigate to the category list page
    Then Category list table should be displayed
