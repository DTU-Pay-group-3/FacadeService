package reporting.service.adapter.rest;

import utils.ServiceFactory;
import reporting.service.LoggedTransaction;
import reporting.service.ReportingService;
import java.util.List;
import jakarta.ws.rs.*;

import static jakarta.ws.rs.core.MediaType.APPLICATION_JSON;

@Path("/reports")
public class ReportingResource {
	ReportingService service = new ServiceFactory().getService().getReportingService();

	//GET Reports as customer
	@GET
	@Path("/customer/{customerId}")
	@Produces(APPLICATION_JSON)
	public List<LoggedTransaction> getReportsCustomer(@PathParam("customerId") String customerId) {
		return service.getReportsCustomer(customerId);
	}

	//GET Reports as merchant
	@GET
	@Path("/merchant/{merchantId}")
	@Produces(APPLICATION_JSON)
	public List<LoggedTransaction> getReportsMerchant(@PathParam("merchantId") String merchantId) {
		return service.getReportsMerchant(merchantId);
	}

	//GET Reports as manager
	@GET
	@Path("/manager")
	@Produces(APPLICATION_JSON)
	public List<LoggedTransaction> getReportsManager() {
		return service.getReportsManager();
	}
}
