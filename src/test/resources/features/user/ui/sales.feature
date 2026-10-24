@ui @user @sales
Feature: User sales UI

  @UI_SALE_05 @user
  Scenario: User cannot see the create action
    Given I am logged in as "user"
    When I open the "sales" list
    Then the create action should be hidden
