package reporting.service.adapter.rest;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import utils.ServiceFactory;
import reporting.service.LoggedTransaction;
import reporting.service.ReportingService;

import java.util.List;

@Path("/reports")
public class ReportingResource {
	ReportingService service = new ServiceFactory().getService().getReportingService();

	//GET Reports as customer
	@GET
	@Path("/customer/{customerId}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<LoggedTransaction> getReportsCustomer(@PathParam("customerId") String customerId) {
		return service.getReportsCustomer(customerId);
	}

	//GET Reports as merchant
	@GET
	@Path("/merchant/{merchantId}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<LoggedTransaction> getReportsMerchant(@PathParam("merchantId") String merchantId) {
		return service.getReportsMerchant(merchantId);
	}

	//GET Reports as manager
	@GET
	@Path("/manager")
	@Produces(MediaType.APPLICATION_JSON)
	public List<LoggedTransaction> getReportsManager() {
		return service.getReportsManager();
	}
}
