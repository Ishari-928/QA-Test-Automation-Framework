@api @user @categories
Feature: User category API permissions

  @TC_USER_API_CAT_01
  Scenario: User lists categories
    Given I have a user API token
    When I request the "categories" API list
    Then the response status should be 200
    And the response should contain valid JSON

  @TC_USER_API_CAT_02
  Scenario: User reads a category
    Given I have a user API token
    Given a scenario-owned category exists
    When the user retrieves its scenario category
    Then the created category should have the submitted name

  @TC_USER_API_CAT_03
  Scenario: User cannot create a category
    Given I have a user API token
    When the user attempts to create a category
    Then the response status should be 403

  @TC_USER_API_CAT_04
  Scenario: User cannot edit a category
    Given I have a user API token
    Given a scenario-owned category exists
    When the user attempts to edit its scenario category
    Then the response status should be 403
    And the scenario category should remain unchanged

  @TC_USER_API_CAT_05
  Scenario: User cannot delete a category
    Given I have a user API token
    Given a scenario-owned category exists
    When the user attempts to delete its scenario category
    Then the response status should be 403
    And the scenario category should remain unchanged

