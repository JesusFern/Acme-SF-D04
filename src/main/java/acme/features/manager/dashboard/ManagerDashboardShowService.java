
package acme.features.manager.dashboard;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.entities.projects.Priority;
import acme.forms.ManagerDashboard;
import acme.roles.Manager;

@Service
public class ManagerDashboardShowService extends AbstractService<Manager, ManagerDashboard> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private ManagerDashboardRepository repository;

	// AbstractService interface ----------------------------------------------


	@Override
	public void authorise() {
		super.getResponse().setAuthorised(true);
	}

	@Override
	public void load() {
		ManagerDashboard managerDashboard;
		Integer totalNumberOfMustUserStories;
		Integer totalNumberOfShouldUserStories;
		Integer totalNumberOfCouldUserStories;
		Integer totalNumberOfWontUserStories;
		Double averageEstimatedCostUserStories;
		Double deviationEstimatedCostUserStories;
		Integer minimumEstimatedCostUserStories;
		Integer maximumEstimatedCostUserStories;
		Double averageProjectsCost;
		Double deviationProjectsCost;
		Integer minimumProjectsCost;
		Integer maximumProjectsCost;
		int id;

		id = super.getRequest().getPrincipal().getActiveRoleId();
		totalNumberOfMustUserStories = this.repository.totalNumberOfUserStoriesByPriority(id, Priority.MUST);
		totalNumberOfShouldUserStories = this.repository.totalNumberOfUserStoriesByPriority(id, Priority.SHOULD);
		totalNumberOfCouldUserStories = this.repository.totalNumberOfUserStoriesByPriority(id, Priority.COULD);
		totalNumberOfWontUserStories = this.repository.totalNumberOfUserStoriesByPriority(id, Priority.WONT);
		averageEstimatedCostUserStories = this.repository.averageEstimatedCostUserStories(id);
		deviationEstimatedCostUserStories = this.repository.deviationEstimatedCostUserStories(id);
		minimumEstimatedCostUserStories = this.repository.minimumEstimatedCostUserStories(id);
		maximumEstimatedCostUserStories = this.repository.maximumEstimatedCostUserStories(id);
		averageProjectsCost = this.repository.averageProjectsCost(id);
		deviationProjectsCost = this.repository.averageProjectsCost(id);
		minimumProjectsCost = this.repository.minimumProjectsCost(id);
		maximumProjectsCost = this.repository.maximumEstimatedCostUserStories(id);

		managerDashboard = new ManagerDashboard();
		managerDashboard.setTotalNumberOfMustUserStories(totalNumberOfMustUserStories);
		managerDashboard.setTotalNumberOfShouldUserStories(totalNumberOfShouldUserStories);
		managerDashboard.setTotalNumberOfCouldUserStories(totalNumberOfCouldUserStories);
		managerDashboard.setTotalNumberOfWontUserStories(totalNumberOfWontUserStories);
		managerDashboard.setAverageEstimatedCostUserStories(averageEstimatedCostUserStories);
		managerDashboard.setDeviationEstimatedCostUserStories(deviationEstimatedCostUserStories);
		managerDashboard.setMinimumEstimatedCostUserStories(minimumEstimatedCostUserStories);
		managerDashboard.setMaximumEstimatedCostUserStories(maximumEstimatedCostUserStories);
		managerDashboard.setAverageProjectsCost(averageProjectsCost);
		managerDashboard.setDeviationProjectsCost(deviationProjectsCost);
		managerDashboard.setMinimumProjectsCost(minimumProjectsCost);
		managerDashboard.setMaximumProjectsCost(maximumProjectsCost);

		super.getBuffer().addData(managerDashboard);

	}
	@Override
	public void unbind(final ManagerDashboard object) {
		Dataset dataset;

		dataset = super.unbind(object, //
			"totalNumberOfMustUserStories", //
			"totalNumberOfShouldUserStories", //
			"totalNumberOfCouldUserStories", // 
			"totalNumberOfWontUserStories", //
			"averageEstimatedCostUserStories", //
			"deviationEstimatedCostUserStories", //
			"minimumEstimatedCostUserStories", // 
			"maximumEstimatedCostUserStories", //
			"averageProjectsCost", //
			"deviationProjectsCost", //
			"minimumProjectsCost", //
			"maximumProjectsCost" //
		);

		super.getResponse().addData(dataset);
	}

}
