@ui @user @categories
Feature: User category management

  @TC_USER_UI_CAT_02
  Scenario: User cannot see Add Category
    Given I am logged in as "user"
    When I navigate to the category list page
    Then the category add action should be hidden
