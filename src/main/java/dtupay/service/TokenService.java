package dtupay.service;

import messaging.Event;
import messaging.MessageQueue;
import utils.CorrelationId;

import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;

public class TokenService {
    private MessageQueue queue;
    private Map<CorrelationId, CompletableFuture<String[]>> correlations = new ConcurrentHashMap<>();

    public TokenService(MessageQueue q){
        this.queue = q;
        this.queue.addHandler("GenerateTokenCompleted", this::handleTokenGenComplete);

    }

    public String[] generateTokens(String account) {
        var correlationId = CorrelationId.randomId();
        correlations.put(correlationId,new CompletableFuture<>());
        Event event = new Event("GenerateToken", new Object[] { account, correlationId });
        queue.publish(event);
        return correlations.get(correlationId).join();
    }

    public void handleTokenGenComplete(Event e) {
        var s = e.getArgument(0, String[].class);
        var correlationid = e.getArgument(1, CorrelationId.class);
        correlations.get(correlationid).complete(s);
    }

}
