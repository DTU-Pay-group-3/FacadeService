package dtupay.service;

import messaging.Event;
import messaging.MessageQueue;
import utils.CorrelationId;
import utils.Payment;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;

/*Author Marian s233481 */
/*Author Sandra s233484 */
public class PaymentService {
    private MessageQueue queue;
    private Map<CorrelationId, CompletableFuture<String>> correlations = new ConcurrentHashMap<>();

    /*Author Marian s233481 */
    public PaymentService(MessageQueue q){
        this.queue = q;
        this.queue.addHandler("PaymentCompleted", this::handlePaymentSuccess);
        this.queue.addHandler("PaymentFailed", this::handlePaymentFail);

    }

    /*Author Marian s233481 */
    public String makePayment(Payment payment) {
        var correlationId = CorrelationId.randomId();
        correlations.put(correlationId,new CompletableFuture<>());
        Event event = new Event("RequestPayment", new Object[] { payment, correlationId });
        queue.publish(event);
        return correlations.get(correlationId).join();
    }

    /*Author Sandra s233484*/
    public void handlePaymentSuccess(Event e) {
        var s = e.getArgument(0, String.class);
        var correlationid = e.getArgument(1, CorrelationId.class);
        correlations.get(correlationid).complete(s+" Completed Successfully");
    }

    /*Author Sandra s233484*/
    public void handlePaymentFail(Event e) {
        var s = e.getArgument(0, String.class);
        var correlationid = e.getArgument(1, CorrelationId.class);
        correlations.get(correlationid).complete(s+" Failed");
//        correlations.get(correlationid).cancel(true);
    }
}
