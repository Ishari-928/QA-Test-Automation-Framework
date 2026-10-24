@api @plants
Feature: Plants API

  @API_PLANT_01
  Scenario: Admin lists plants
    Given I have an admin API token
    When I request the "plants" API list
    Then the response status should be 200
    And the response should contain valid JSON

  @API_PLANT_02
  Scenario: Create and read back a plant
    Given I have an admin API token
    When I create a valid plant through the API
    Then the created plant should match its submitted values

  @API_PLANT_03
  Scenario: Retrieve a known plant
    Given I have an admin API token
    Given a scenario-owned plant exists
    When I retrieve that plant by ID
    Then the created plant should match its submitted values

  @API_PLANT_04
  Scenario: Reject a negative price
    Given I have an admin API token
    Given a scenario-owned plant exists
    When I submit a plant with a negative price
    Then the response status should be 400

  @API_PLANT_05
  Scenario: Reject anonymous plant access
    When I request the "plants" API list without authentication
    Then the response status should be 401

