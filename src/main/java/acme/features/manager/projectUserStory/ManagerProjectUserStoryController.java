
package acme.features.manager.projectUserStory;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import acme.client.controllers.AbstractController;
import acme.entities.projects.ProjectUserStory;
import acme.roles.Manager;

@Controller
public class ManagerProjectUserStoryController extends AbstractController<Manager, ProjectUserStory> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private ManagerProjectUserStoryCreateService		createService;

	@Autowired
	private ManagerProjectUserStoryListInProjectService	listProjectService;

	@Autowired
	private ManagerProjectUserStoryShowService			showService;

	@Autowired
	private ManagerProjectUserStoryDeleteService		deleteService;


	@PostConstruct
	protected void initialise() {
		super.addBasicCommand("create", this.createService);
		super.addBasicCommand("show", this.showService);
		super.addBasicCommand("delete", this.deleteService);

		super.addCustomCommand("list-project", "list", this.listProjectService);
	}

}
