
package acme.features.any.progressLog;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import acme.client.controllers.AbstractController;
import acme.client.data.accounts.Any;
import acme.entities.contracts.ProgressLog;

@Controller
public class AnyProgressLogController extends AbstractController<Any, ProgressLog> {

	// Internal state ---------------------------------------------------------
	@Autowired
	private AnyProgressLogListService	listService;

	@Autowired
	private AnyProgressLogShowService	listShowService;

	// Constructors -----------------------------------------------------------


	@PostConstruct
	protected void initialise() {
		super.addBasicCommand("list", this.listService);
		super.addBasicCommand("show", this.listShowService);

	}
}
