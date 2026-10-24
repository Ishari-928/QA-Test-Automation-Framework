@api @sales
Feature: Sales API

  @API_SALE_01
  Scenario: Admin lists sales
    Given I have an admin API token
    When I request the "sales" API list
    Then the response status should be 200
    And the response should contain valid JSON

  @API_SALE_02
  Scenario: Sale reduces stock and computes total
    Given I have an admin API token
    When I sell two units of a scenario-owned plant
    Then the sale should total 200 and the remaining stock should be 8

  @API_SALE_03
  Scenario: Retrieve a known sale
    Given I have an admin API token
    Given a scenario-owned sale exists
    When I retrieve that sale by ID
    Then the retrieved sale should match its created values

  @API_SALE_04
  Scenario: Reject sale exceeding stock
    Given I have an admin API token
    Given a scenario-owned plant exists
    When I attempt to sell more units than its stock
    Then the response status should be 400
    And the scenario plant stock should remain 10

  @API_SALE_05
  Scenario: Reject anonymous sale access
    When I request the "sales" API list without authentication
    Then the response status should be 401

