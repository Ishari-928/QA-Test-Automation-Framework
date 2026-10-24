@api @categories
Feature: Categories API

  @API_CAT_01
  Scenario: Admin lists categories
    Given I have an admin API token
    When I request the "categories" API list
    Then the response status should be 200
    And the response should contain valid JSON

  @API_CAT_02
  Scenario: Create and read back a category
    Given I have an admin API token
    When I create a valid category through the API
    Then the created category should have the submitted name

  @API_CAT_03
  Scenario: Retrieve a known category
    Given I have an admin API token
    Given a scenario-owned category exists
    When I retrieve that category by ID
    Then the created category should have the submitted name

  @API_CAT_04
  Scenario: Reject a blank category name
    Given I have an admin API token
    When I submit a category with a blank name
    Then the response status should be 400

  @API_CAT_05
  Scenario: Reject anonymous category access
    When I request the "categories" API list without authentication
    Then the response status should be 401

