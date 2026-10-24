@ui @sales
Feature: Sales UI
  @UI_SALE_01 @admin
  Scenario: Admin sees expected list columns
    Given I am logged in as "admin"
    When I open the "sales" list
    Then the management table should have its expected columns

  @UI_SALE_02 @admin
  Scenario: Admin opens the create form
    Given I am logged in as "admin"
    When I open the "sales" list
    And I open its create form
    Then the create form should show its required controls

  @UI_SALE_03 @admin
  Scenario: Cancel returns to the list
    Given I am logged in as "admin"
    When I open the "sales" list
    And I open its create form
    And I cancel the create form
    Then the management list should be displayed

  @UI_SALE_04 @admin
  Scenario: Empty form displays required validation
    Given I am logged in as "admin"
    When I open the "sales" list
    And I open its create form
    And I submit the empty create form
    Then the form validation should include "Plant is required"

