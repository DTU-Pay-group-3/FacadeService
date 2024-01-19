package merchant.service.rest;

import customer.service.DTUPayAccount;
import dtupay.service.AccountManagementService;
import dtupay.service.PaymentService;
import utils.Payment;
import utils.ServiceFactory;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

@Path("/merchant")
public class MerchantResource {

	AccountManagementService accountService = new ServiceFactory().getService().getAccountManagementService();
	PaymentService paymentService = new ServiceFactory().getService().getPaymentService();
	@GET
	@Path("/")
	public String getTest(){
		return "Hello world";
	}

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
