package customer.service.rest;

import customer.service.DTUPayAccount;
import dtupay.service.AccountManagementService;
import dtupay.service.TokenService;

import utils.ServiceFactory;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
//@Authors: Sandra, Marian
@Path("/customers")
public class CustomerResource {
    // Services
    AccountManagementService accountManagementService = new ServiceFactory().getService().getAccountManagementService();
    TokenService tokenService = new ServiceFactory().getService().getTokenService();

    @GET
    @Path("/{id}")
    public DTUPayAccount getAccount(@PathParam("id") String id) {
        return accountManagementService.getAccount(id);
    }

    @POST
    public DTUPayAccount registerAccount(DTUPayAccount account) {
        return accountManagementService.register(account);
    }

    @POST
    @Path("/tokens")
    public String[] generateTokens(String account) {
        return tokenService.generateTokens(account);
    }
}
