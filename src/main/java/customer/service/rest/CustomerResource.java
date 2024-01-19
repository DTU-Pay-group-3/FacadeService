package customer.service.rest;

import customer.service.DTUPayAccount;
import customer.service.CustomerService;
import dtupay.service.TokenService;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import utils.ServiceFactory;


@Path("/customers")
public class CustomerResource {
    // Services
    CustomerService customerService = new ServiceFactory().getService().getCustomerService();
    TokenService tokenService = new ServiceFactory().getService().getTokenService();

    @GET
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public DTUPayAccount getAccount(@PathParam("id") String id) {
        return customerService.getAccount(id);
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public DTUPayAccount registerAccount(DTUPayAccount account) {
        return customerService.register(account);
    }

    @POST
    @Path("/tokens")
    public String[] generateTokens(String account) {
        return tokenService.generateTokens(account);
    }
}
