@ui @user @categories
Feature: User category management

  @TC_USER_UI_CAT_04
  Scenario: User cannot access category creation directly
    Given I am logged in as "user"
    When I open the category create URL directly
    Then category creation access should be denied
