package dtupay.service;

import messaging.Event;
import messaging.MessageQueue;
import utils.CorrelationId;
import utils.Payment;

import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;

public class PaymentService {
    private MessageQueue queue;
    private Map<CorrelationId, CompletableFuture<String>> correlations = new ConcurrentHashMap<>();

    public PaymentService(MessageQueue q){
        this.queue = q;
        this.queue.addHandler("PaymentCompleted", this::handlePaymentSuccess);
        this.queue.addHandler("PaymentFailed", this::handlePaymentFail);

    }

    public String makePayment(Payment payment) {
        var correlationId = CorrelationId.randomId();
        correlations.put(correlationId,new CompletableFuture<>());
        Event event = new Event("RequestPayment", new Object[] { payment, correlationId });
        queue.publish(event);
        return correlations.get(correlationId).join();
    }

    public void handlePaymentSuccess(Event e) {
        var s = e.getArgument(0, String.class);
        var correlationid = e.getArgument(1, CorrelationId.class);
        correlations.get(correlationid).complete(s);
    }

    public void handlePaymentFail(Event e) {
        var s = e.getArgument(0, String.class);
        var correlationid = e.getArgument(1, CorrelationId.class);
        correlations.get(correlationid).cancel(true);
    }
}
