
package acme.features.any.project;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import acme.client.controllers.AbstractController;
import acme.client.data.accounts.Any;
import acme.entities.projects.Project;

@Controller
public class AnyProjectController extends AbstractController<Any, Project> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private AnyProjectShowService		showService;

	@Autowired
	private AnyProjectListAllService	listAllService;

	// Constructors -----------------------------------------------------------


	@PostConstruct
	protected void initialise() {
		super.addBasicCommand("show", this.showService);

		super.addCustomCommand("list-all", "list", this.listAllService);
	}
}
