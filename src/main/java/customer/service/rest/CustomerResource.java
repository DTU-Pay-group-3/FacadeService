package customer.service.rest;

import customer.service.DTUPayAccount;
import customer.service.CustomerService;
import dtupay.service.TokenService;
import jakarta.ws.rs.*;
import utils.ServiceFactory;

import static jakarta.ws.rs.core.MediaType.APPLICATION_JSON;

@Path("/customers")
public class CustomerResource {
    // Services
    CustomerService customerService = new ServiceFactory().getService().getCustomerService();
    TokenService tokenService = new ServiceFactory().getService().getTokenService();

    @GET
    @Path("/{id}")
    @Consumes(APPLICATION_JSON)
    @Produces(APPLICATION_JSON)
    public DTUPayAccount getAccount(@PathParam("id") String id) {
        return customerService.getAccount(id);
    }

    @POST
    @Consumes(APPLICATION_JSON)
    @Produces(APPLICATION_JSON)
    public DTUPayAccount registerAccount(DTUPayAccount account) {
        return customerService.register(account);
    }

    @POST
    @Path("/tokens")
    public String[] generateTokens(String account) {
        return tokenService.generateTokens(account);
    }
}
