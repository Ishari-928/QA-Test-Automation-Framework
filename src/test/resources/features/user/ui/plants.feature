@ui @user @plants
Feature: User plants UI

  @UI_PLANT_05 @user
  Scenario: User cannot see the create action
    Given I am logged in as "user"
    When I open the "plants" list
    Then the create action should be hidden
