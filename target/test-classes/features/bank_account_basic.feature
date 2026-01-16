Feature: Basic bank account behavior
  Scenario: A new account has zero balance
    Given I have a new bank account
    When I check its balance
    Then the balance should be 0
