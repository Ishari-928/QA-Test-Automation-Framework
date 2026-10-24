@api @authentication
Feature: API authentication
  @API_AUTH_01 @admin
  Scenario: Admin receives a token
    When I request an API token as "admin"
    Then the API login should return a nonempty token

  @API_AUTH_03
  Scenario: Unknown account is rejected
    When I request an API token with username "qa_unknown_account" and password "wrong_password"
    Then the API login should be rejected

  @API_AUTH_04
  Scenario: Blank username is rejected
    When I request an API token with username "" and password "some_password"
    Then the API login should be rejected

  @API_AUTH_05
  Scenario: Blank password is rejected
    When I request an API token with username "some_user" and password ""
    Then the API login should be rejected
