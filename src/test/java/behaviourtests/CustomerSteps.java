package behaviourtests;

import customer.service.DTUPayAccount;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import messaging.Event;
import messaging.MessageQueue;

import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

import static org.junit.Assert.assertEquals;

public class CustomerSteps {
    private CompletableFuture<Event> publishedEvent = new CompletableFuture<>();

    private MessageQueue q = new MessageQueue() {

        @Override
        public void publish(Event event) {
            publishedEvent.complete(event);
        }

        @Override
        public void addHandler(String eventType, Consumer<Event> handler) {
        }

    };
    private CustomerService service = new CustomerService(q);
    private DTUPayAccount account, result;
    private CompletableFuture<DTUPayAccount> registeredAccount = new CompletableFuture<>();

    @Given("a customer with a bank account")
    public void aCustomerWithABankAccount() {
        this.account = new DTUPayAccount("Alice", "Aname", "1122330000", "1234");
    }

    @When("the customer registers with DTUPay")
    public void theCustomerRegistersWithDTUPay() {
        new Thread(() -> {
            var result = service.register(this.account);
            registeredAccount.complete(result);
        }).start();
    }

    @Then("the {string} event is sent to the service")
    public void theEventIsSentToTheService(String eventName) {
        Event event = new Event(eventName, new Object[] { this.account });
        assertEquals(event,publishedEvent.join());
    }

    @When("the {string} event is returned")
    public void theEventIsReturned(String eventName) {
        service.handleAccountCreated(new Event("..", new Object[] {this.account}));
    }

    @When("the {string} event is returned with an empty response")
    public void theEventIsReturnedWithAnEmptyResponse(String eventName) {
        this.result = new DTUPayAccount("", "", "", "");
        service.handleAccountCreated(new Event("..", new Object[] { this.result }));
    }

    @Then("a customer with the same information as the bank customer exists in DTUPay")
    public void aCustomerWithTheSameInformationAsTheBankCustomerExistsInDTUPay() {
        assertEquals(this.account, this.registeredAccount.join());
    }

    @Then("a customer is not created")
    public void aCustomerIsNotCreated() {
        assertEquals(this.result, this.registeredAccount.join());
    }

}
