
package acme.features.authenticated.progressLog;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import acme.client.controllers.AbstractController;
import acme.client.data.accounts.Authenticated;
import acme.entities.contracts.ProgressLog;

@Controller
public class AuthenticatedProgressLogController extends AbstractController<Authenticated, ProgressLog> {

	// Internal state ---------------------------------------------------------
	@Autowired
	private AuthenticatedProgressLogListAllService	listAllService;

	@Autowired
	private AuthenticatedProgressLogShowService		listShowService;

	// Constructors -----------------------------------------------------------


	@PostConstruct
	protected void initialise() {
		super.addCustomCommand("list-all", "list", this.listAllService);
		super.addBasicCommand("show", this.listShowService);

	}
}
