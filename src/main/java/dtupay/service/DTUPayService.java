package dtupay.service;

import messaging.MessageQueue;
import reporting.service.ReportingService;

public class DTUPayService {

    private final AccountManagementService accountService;
    private final PaymentService paymentService;
    private final ReportingService reportingService;

    public DTUPayService(MessageQueue q){
        this.accountService = new AccountManagementService(q);
        this.paymentService = new PaymentService(q);
        this.reportingService = new ReportingService(q);
    }

    public AccountManagementService getAccountManagementService(){ return accountService; }
    public ReportingService getReportingService() {return reportingService; };
    public PaymentService getPaymentService() { return paymentService; }

}