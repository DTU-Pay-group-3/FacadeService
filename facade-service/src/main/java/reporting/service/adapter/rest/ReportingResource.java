package reporting.service.adapter.rest;

import utils.ServiceFactory;
import reporting.service.LoggedTransaction;
import reporting.service.ReportingService;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
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
