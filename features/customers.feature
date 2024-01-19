Feature: Account service features
  #Author: Andreas
  Scenario: Successfully register customer
    Given a customer with a bank account
    When the customer registers with DTUPay
    Then the "RegisterAccountRequested" event is sent to the service
    When the "AccountCreated" event is returned
    Then a customer with the same information as the bank customer exists in DTUPay

  Scenario: User already exists
    Given a customer with a bank account
    When the customer registers with DTUPay
    Then the "RegisterAccountRequested" event is sent to the service
    When the "AccountAlreadyExists" event is returned with an empty response
    Then a customer is not created

  Scenario: User not found
    Given a customer with a bank account
    When an actor requests that account
    Then a "GetDTUPayAccount" event for an account with that id is sent to the service
    When the "DTUPayAccountNotFound" event is returned with no account found
    Then no account is found