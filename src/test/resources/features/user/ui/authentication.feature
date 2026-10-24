@ui @user @authentication
Feature: User login UI automation
  Verify successful user authentication and login form validation.

  @UI_AUTH_02 @smoke @user-login
  Scenario: User signs in with configured credentials
    Given I am logged in as "user"
    Then the dashboard should be displayed

  @UI_USER_AUTH_03 @negative @user-login
  Scenario: User login rejects invalid credentials
    Given I open the login page
    When I submit login with username "qa_unknown_account" and password "wrong_password"
    Then the login error should be "Invalid username or password."

  @UI_USER_AUTH_04 @negative @user-login
  Scenario: User login requires username
    Given I open the login page
    When I submit login with username "" and password "some_password"
    Then the "username" login validation should be "Username is required"

  @UI_USER_AUTH_05 @negative @user-login
  Scenario: User login requires password
    Given I open the login page
    When I submit login with username "some_user" and password ""
    Then the "password" login validation should be "Password is required"
