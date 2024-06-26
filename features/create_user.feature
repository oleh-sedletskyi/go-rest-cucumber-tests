Feature: Create User API

  Scenario: Successfully create a new user
    Given I have the endpoint "https://gorest.co.in/public/v2/users"
    And I have the following user details:
      | name        | email                 | status | gender |
      | Test Name   | test.user@example.com | active | male   |
    When I send a POST request to create the user
    Then the response status should be 201
    And the response body should contain the user ID
    And the response body should contain the following user details:
      | name        | email                 | status | gender |
      | Test Name   | test.user@example.com | active | male   |
    When I send a GET request to get the user details
    Then the response should contain the same user details

  Scenario: Attempt to create a user with an existing email and missing mandatory fields
    Given I have the same endpoint
    And I have the following existing user without other fields:
      | email                 |
      | test.user@example.com |
    When I send a POST request to create the same user
    Then the existing user response status should be 422
    And the response body should contain the following error details:
      | field  | message                |
      | email  | has already been taken |
      | name   | can't be blank |
      | gender | can't be blank, can be male of female |
      | status | can't be blank |