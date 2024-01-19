package behaviourtests;

import customer.service.DTUPayAccount;
import dtupay.service.AccountManagementService;
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
    private AccountManagementService service = new AccountManagementService(q);
    private DTUPayAccount account, result;
    private CompletableFuture<DTUPayAccount> registeredAccount = new CompletableFuture<>();
    private CompletableFuture<DTUPayAccount> getAccount = new CompletableFuture<>();

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

    @When("an actor requests that account")
    public void anActorRequestsThatAccount() {
        new Thread(() -> {
            var result = service.getAccount(this.account.getId());
            getAccount.complete(result);
        }).start();
    }

    @Then("a {string} event for an account with that id is sent to the service")
    public void aEventForAnAccountWithThatIdIsSentToTheService(String eventName) {
        Event event = new Event(eventName, new Object[] { this.account.getId() });
        assertEquals(event,publishedEvent.join());
    }

    @Then("no account is found")
    public void noAccountIsFound() {
        DTUPayAccount resultingAccount = getAccount.join();
        assertEquals("", resultingAccount.getId());
        assertEquals("", resultingAccount.getAccountNumber());
        assertEquals("", resultingAccount.getFirstName());
        assertEquals("", resultingAccount.getLastName());
        assertEquals("", resultingAccount.getCprNumber());
    }

    @When("the {string} event is returned with no account found")
    public void theEventIsReturnedWithNoAccountFound(String eventName) {
        this.result = new DTUPayAccount("", "", "", "");
        this.result.setId("");
        service.handleAccountNotFound(new Event("..", new Object[] { this.result }));
    }
}
