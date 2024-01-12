Feature: Merchant

  Scenario: Merchant registration
  	Given there is a merchant with a bank account
  	When the merchant is being registered
  	Then the "MerchantRegistrationRequested" event is sent
  	When the "MerchantAssigned" event is sent with non-empty id
  	Then the merchant is registered and his id is set
