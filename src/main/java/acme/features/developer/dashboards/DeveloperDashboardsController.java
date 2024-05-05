
package acme.features.developer.dashboards;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import acme.client.controllers.AbstractController;
import acme.forms.DeveloperDashboard;
import acme.roles.Developer;

@Controller
public class DeveloperDashboardsController extends AbstractController<Developer, DeveloperDashboard> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private DeveloperDashboardsShowService showService;

	// Constructors -----------------------------------------------------------


	@PostConstruct
	public void initialise() {
		super.addBasicCommand("show", this.showService);
	}
}
