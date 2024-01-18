package dtupay.service;

import messaging.MessageQueue;

public class PaymentService {
    private MessageQueue queue;

    public PaymentService(MessageQueue q){
        this.queue = q;
    }
}
