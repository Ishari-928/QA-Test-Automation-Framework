@ui @user @categories
Feature: User category management

  @TC_USER_UI_CAT_03
  Scenario: User cannot delete categories
    Given a category exists for this UI scenario
    And I am logged in as "user"
    When I navigate to the category list page
    Then the category delete actions should be disabled or absent
