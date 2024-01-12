package dtupay.service;

import messaging.MessageQueue;

public class DTUPayService {

    private final AccountManagementService accountService;
    private final PaymentService paymentService;
    public DTUPayService(MessageQueue q){
        this.accountService = new AccountManagementService(q);
        this.paymentService = new PaymentService(q);
    }

    public AccountManagementService getAccountManagementService(){ return accountService; }


    public PaymentService getPaymentService() { return paymentService; }

}