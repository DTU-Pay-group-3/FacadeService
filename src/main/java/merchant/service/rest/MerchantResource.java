package merchant.service.rest;

import customer.service.DTUPayAccount;
import dtupay.service.AccountManagementService;
import dtupay.service.PaymentService;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import utils.Payment;
import utils.ServiceFactory;


@Path("/merchant")
public class MerchantResource {

	AccountManagementService accountService = new ServiceFactory().getService().getAccountManagementService();
	PaymentService paymentService = new ServiceFactory().getService().getPaymentService();

	@POST
	@Path("/register")
	public DTUPayAccount registerMerchant(DTUPayAccount account){
		return accountService.register(account);
	}

	@POST
	@Path("/unregister")
	public String unregisterMerchant(){
		//send merchant id
		return null;
	}

	@POST
	@Path("/report")
	public String requestReport(String id){
		//send merchant id
		return "Empty method";

	}

	@POST
	@Path("/pay")
	public String pay(Payment payment){
		//send payment object
		return paymentService.makePayment(payment);
	}
}
