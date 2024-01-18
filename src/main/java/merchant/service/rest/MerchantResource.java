package merchant.service.rest;

import customer.service.DTUPayAccount;
import dtupay.service.AccountManagementService;
import dtupay.service.PaymentService;
import merchant.service.Merchant;
import utils.Payment;
import utils.ServiceFactory;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("/merchant")
public class MerchantResource {

	AccountManagementService accountService = new ServiceFactory().getService().getAccountManagementService();
	PaymentService paymentService = new ServiceFactory().getService().getPaymentService();

	@POST
	@Path("/register")
	@Consumes(MediaType.APPLICATION_JSON)
	public DTUPayAccount registerMerchant(DTUPayAccount account){
		return accountService.register(account);
	}

	@POST
	@Path("/unregister")
	@Consumes(MediaType.APPLICATION_JSON)
	public String unregisterMerchant(){
		//send merchant id
		return null;
	}

	@POST
	@Path("/report")
	@Consumes(MediaType.APPLICATION_JSON)
	public String requestReport(String id){
		//send merchant id
		return "Empty method";

	}

	@POST
	@Path("/pay")
	@Consumes(MediaType.APPLICATION_JSON)
	public String pay(Payment payment){
		//send payment object
		return paymentService.makePayment(payment);
	}
}
