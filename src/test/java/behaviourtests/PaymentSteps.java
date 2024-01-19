package behaviourtests;

import customer.service.DTUPayAccount;
import dtupay.service.PaymentService;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import messaging.Event;
import messaging.MessageQueue;
import utils.CorrelationId;
import utils.Payment;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

import static org.junit.jupiter.api.Assertions.assertEquals;

/*Author Marian s233481 and Sandra s233484*/
public class PaymentSteps {

    private Map<Payment, CompletableFuture<Event>> publishedEvents = new HashMap<>();

    private MessageQueue q = new MessageQueue() {

        @Override
        public void publish(Event event) {
            var argument = event.getArgument(0, Payment.class);
            publishedEvents.get(argument).complete(event);
        }

        @Override
        public void addHandler(String eventType, Consumer<Event> handler) {
        }

    };
    private PaymentService service = new PaymentService(q);
    private CompletableFuture<String> paymentCompleted = new CompletableFuture<>();
     private Payment payment;
    private DTUPayAccount merchant;
    private String customerToken;
    private Map<String, CorrelationId> correlationIds = new HashMap<>();


    @Given("there is a merchant and a customer with DTUPay accounts")
    public void thereIsAMerchantAndACustomerWithDTUPayAccounts() {
        customerToken="token1";
        merchant=new DTUPayAccount("Bob","Builder","1212121","fdiogu-324dsff-32r32dfew");
    }

    @And("the merchant wants to request a payment")
    public void theMerchantWantsTo() {
        payment=new Payment(UUID.randomUUID().toString(),merchant.getId(),customerToken,"PaymentDesc1", BigDecimal.valueOf(100));
        publishedEvents.put(payment, new CompletableFuture<Event>());
    }

    @When("the payment is being processed")
    public void thePaymentIsBeingProcessed() {
        new Thread(() -> {
            var result = service.makePayment(payment);
            paymentCompleted.complete(result);
        }).start();
    }

    @Then("the {string} event is published")
    public void theEventIsPublished(String arg0) {
        Event event = publishedEvents.get(payment).join();
        var st = event.getArgument(0, Payment.class);
        var correlationId = event.getArgument(1, CorrelationId.class);
        correlationIds.put(st.getPaymentId(), correlationId);
    }

    @When("the {string} event is sent with PaymentGood")
    public void theEventIsSentWithPaymentGood(String arg0) {
        Event event = new Event(arg0, new Object[] { payment.getPaymentId(), correlationIds.get(payment.getPaymentId()) });
        service.handlePaymentSuccess(event);
    }

    @Then("the payment is registered and the money is received")
    public void thePaymentIsRegisteredAndTheMoneyIsReceived() {
        String result=paymentCompleted.join();
        assertEquals(result,payment.getPaymentId()+" Completed Successfully");
    }
}
