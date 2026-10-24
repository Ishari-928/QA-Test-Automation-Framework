@ui @user @categories
Feature: User category management

  @TC_USER_UI_CAT_05
  Scenario: Unknown category search displays empty state
    Given I am logged in as "user"
    When I navigate to the category list page
    And I search for a category name that does not exist
    Then the category empty message should be displayed
