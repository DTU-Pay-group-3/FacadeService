package dtupay.service;

import messaging.MessageQueue;
import reporting.service.ReportingService;

/*Author Marian s233481 and Sandra s233484*/
public class DTUPayService {

    private final AccountManagementService accountService;
    private final PaymentService paymentService;
    private final ReportingService reportingService;
    private final TokenService tokenService;

    public DTUPayService(MessageQueue q){
        this.accountService = new AccountManagementService(q);
        this.paymentService = new PaymentService(q);
        this.reportingService = new ReportingService(q);
        this.tokenService = new TokenService(q);
    }

    public AccountManagementService getAccountManagementService(){ return accountService; }
    public ReportingService getReportingService() {return reportingService; };
    public PaymentService getPaymentService() { return paymentService; }
    public TokenService getTokenService() {return tokenService; }
}