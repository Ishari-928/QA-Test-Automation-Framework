@ui @user @categories
Feature: User category management

  @TC_USER_UI_CAT_01
  Scenario: User views categories
    Given I am logged in as "user"
    When I navigate to the category list page
    Then Category list table should be displayed
