
package acme.features.manager.project;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import acme.client.controllers.AbstractController;
import acme.entities.projects.Project;
import acme.roles.Manager;

@Controller
public class ManagerProjectController extends AbstractController<Manager, Project> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private ManagerProjectShowService		showService;

	@Autowired
	private ManagerProjectListMineService	listMineService;

	@Autowired
	private ManagerProjectCreateService		createService;

	@Autowired
	private ManagerProjectDeleteService		deleteService;

	@Autowired
	private ManagerProjectUpdateService		updateService;

	@Autowired
	private ManagerProjectListAllService	listAllService;

	@Autowired
	private ManagerProjectPublishService	publishService;

	// Constructors -----------------------------------------------------------


	@PostConstruct
	protected void initialise() {
		super.addBasicCommand("show", this.showService);
		super.addBasicCommand("create", this.createService);
		super.addBasicCommand("update", this.updateService);
		super.addBasicCommand("delete", this.deleteService);

		super.addCustomCommand("list-mine", "list", this.listMineService);
		super.addCustomCommand("list-all", "list", this.listAllService);
		super.addCustomCommand("publish", "update", this.publishService);
	}
}
