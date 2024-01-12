package merchant.service.rest;

import dtupay.service.AccountManagementService;
import merchant.service.Merchant;
import merchant.service.ServiceFactory;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("/merchant")
public class MerchantResource {

	AccountManagementService accountService = new ServiceFactory().getService().getAccountManagementService();

	@POST
	@Path("/register")
	@Consumes(MediaType.APPLICATION_JSON)
	public Merchant registerMerchant(Merchant merchant){
		return accountService.register(merchant);
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
	public String pay(){
		//send payment object
		return "Empty method";
	}
}
