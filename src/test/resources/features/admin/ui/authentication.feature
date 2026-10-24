@ui @authentication @admin
Feature: Admin login UI automation
  Verify successful administrator authentication and login form validation.

  @UI_AUTH_01 @smoke @admin-login
  Scenario: Admin signs in with configured credentials
    Given I am logged in as "admin"
    Then the dashboard should be displayed

  @UI_AUTH_03 @negative @admin-login
  Scenario: Admin login rejects invalid credentials
    Given I open the login page
    When I submit login with username "qa_unknown_account" and password "wrong_password"
    Then the login error should be "Invalid username or password."

  @UI_AUTH_04 @negative @admin-login
  Scenario: Admin login requires username
    Given I open the login page
    When I submit login with username "" and password "some_password"
    Then the "username" login validation should be "Username is required"

  @UI_AUTH_05 @negative @admin-login
  Scenario: Admin login requires password
    Given I open the login page
    When I submit login with username "some_user" and password ""
    Then the "password" login validation should be "Password is required"
