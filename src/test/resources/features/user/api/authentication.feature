@api @user @authentication
Feature: User authentication API

  @API_AUTH_02 @user
  Scenario: User receives a token
    When I request an API token as "user"
    Then the API login should return a nonempty token

