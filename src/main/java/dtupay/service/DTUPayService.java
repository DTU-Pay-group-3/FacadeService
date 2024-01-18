package dtupay.service;

import customer.service.CustomerService;
import messaging.MessageQueue;
import reporting.service.ReportingService;

public class DTUPayService {

    private final AccountManagementService accountService;
    private final PaymentService paymentService;
    private final ReportingService reportingService;
    private final CustomerService customerService;

    public DTUPayService(MessageQueue q){
        this.accountService = new AccountManagementService(q);
        this.paymentService = new PaymentService(q);
        this.reportingService = new ReportingService(q);
        this.customerService = new CustomerService(q);
    }

    public AccountManagementService getAccountManagementService(){ return accountService; }
    public ReportingService getReportingService() {return reportingService; };
    public PaymentService getPaymentService() { return paymentService; }
    public CustomerService getCustomerService() {return customerService; }
}