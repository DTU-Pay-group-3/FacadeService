package merchant.service.rest;

import customer.service.DTUPayAccount;
import dtupay.service.AccountManagementService;
import dtupay.service.PaymentService;
import utils.Payment;
import utils.ServiceFactory;
import jakarta.ws.rs.*;

import static jakarta.ws.rs.core.MediaType.APPLICATION_JSON;

@Path("/merchant")
public class MerchantResource {

	AccountManagementService accountService = new ServiceFactory().getService().getAccountManagementService();
	PaymentService paymentService = new ServiceFactory().getService().getPaymentService();

	@POST
	@Path("/register")
	@Consumes(APPLICATION_JSON)
	public DTUPayAccount registerMerchant(DTUPayAccount account){
		return accountService.register(account);
	}

	@POST
	@Path("/unregister")
	@Consumes(APPLICATION_JSON)
	public String unregisterMerchant(){
		//send merchant id
		return null;
	}

	@POST
	@Path("/report")
	@Consumes(APPLICATION_JSON)
	public String requestReport(String id){
		//send merchant id
		return "Empty method";

	}

	@POST
	@Path("/pay")
	@Consumes(APPLICATION_JSON)
	public String pay(Payment payment){
		//send payment object
		return paymentService.makePayment(payment);
	}
}
