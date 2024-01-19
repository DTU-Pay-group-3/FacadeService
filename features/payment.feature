Feature: Merchant Payment
  Scenario: Merchant requests payment
    Given there is a merchant and a customer with DTUPay accounts
    And the merchant wants to
    When the payment is being processed
    Then the "PaymentRequest" event is published
    When the "PaymentCompleted" event is sent with PaymentGood
    Then the payment is registered and the money is received