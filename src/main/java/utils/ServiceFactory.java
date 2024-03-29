package utils;

import dtupay.service.DTUPayService;
import messaging.implementations.RabbitMqQueue;

public class ServiceFactory {
    static DTUPayService DTUPay = null;

    public synchronized DTUPayService getService() {
        // The singleton pattern.
        // Ensure that there is at most
        // one instance of a PaymentService
        if (DTUPay != null) {
            return DTUPay;
        }
        // Hookup the classes to send and receive
        // messages via RabbitMq, i.e. RabbitMqSender and
        // RabbitMqListener.
        // This should be done in the factory to avoid
        // the PaymentService knowing about them. This
        // is called dependency injection.
        // At the end, we can use the PaymentService in tests
        // without sending actual messages to RabbitMq.
        var mq = new RabbitMqQueue("rabbitMq");
        DTUPay = new DTUPayService(mq);
        return DTUPay;
    }
}