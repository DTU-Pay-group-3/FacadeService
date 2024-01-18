package behaviourtests;

import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

import dtupay.service.AccountManagementService;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import merchant.service.Merchant;
import messaging.Event;
import messaging.MessageQueue;

public class MerchantSteps {

	private CompletableFuture<Event> publishedEvent = new CompletableFuture<>();
	Merchant merchant;

	private MessageQueue q = new MessageQueue() {

		@Override
		public void publish(Event event) {
			publishedEvent.complete(event);
		}

		@Override
		public void addHandler(String eventType, Consumer<Event> handler) {
		}
		
	};
	AccountManagementService accountService= new AccountManagementService(q);
	private CompletableFuture<Merchant> registeredMerchant = new CompletableFuture<>();

	@Given("there is a merchant with a bank account")
	public void thereIsAMerchantWithABankAccount() {
		merchant = new Merchant("BkA1","ZZZZ",500);
	}

	@When("the merchant is being registered")
	public void theMerchantIsBeingRegistered() {
		// We have to run the registration in a thread, because
		// the register method will only finish after the next @When
		// step is executed.
		new Thread(() -> {
			var result = accountService.register(merchant);
			registeredMerchant.complete(result);
		}).start();
	}

	@Then("the {string} event is sent")
	public void theEventIsSent(String string) {
		Event event = new Event(string, new Object[] { merchant });
		assertEquals(event,publishedEvent.join());
	}

	@When("the {string} event is sent with non-empty id")
	public void theEventIsSentWithNonEmptyId(String string) {
		// This step simulate the event created by a downstream service.
		var c = new Merchant();
		accountService.handleRegistrationCompleted(new Event("..",new Object[] {c}));
	}




    @Then("the merchant is registered and his id is set")
    public void theMerchantIsRegisteredAndHisIdIsSet() {
		assertNotNull(registeredMerchant.join());
    }
}
