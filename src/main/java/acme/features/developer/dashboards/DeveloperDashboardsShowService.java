
package acme.features.developer.dashboards;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.forms.DeveloperDashboard;
import acme.roles.Developer;

@Service
public class DeveloperDashboardsShowService extends AbstractService<Developer, DeveloperDashboard> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private DeveloperDashboardRepository repository;

	// AbstractService interface ----------------------------------------------


	@Override
	public void authorise() {
		super.getResponse().setAuthorised(true);
	}

	@Override
	public void load() {
		DeveloperDashboard dashboard;
		Integer totalNumberOfTrainingModulesWithUpdatedMoment;
		Integer totalNumberOfTrainingSessionsWithLink;
		Double averageTimeTrainingModules;
		Double deviationTimeTrainingModules;
		Integer minimumTimeTrainingModules;
		Integer maximumTimeTrainingModules;
		int id;

		id = super.getRequest().getPrincipal().getActiveRoleId();

		totalNumberOfTrainingModulesWithUpdatedMoment = this.repository.totalNumberOfTrainingModulesWithUpdatedMoment(id);
		totalNumberOfTrainingSessionsWithLink = this.repository.totalNumberOfTrainingSessionsWithLink(id);
		averageTimeTrainingModules = this.repository.averageTimeTrainingModules(id);
		deviationTimeTrainingModules = this.repository.deviationTimeTrainingModules(id);
		minimumTimeTrainingModules = this.repository.minimumTimeTrainingModules(id);
		maximumTimeTrainingModules = this.repository.maximumTimeTrainingModules(id);

		dashboard = new DeveloperDashboard();
		dashboard.setTotalNumberOfTrainingModulesWithUpdatedMoment(totalNumberOfTrainingModulesWithUpdatedMoment);
		dashboard.setTotalNumberOfTrainingSessionsWithLink(totalNumberOfTrainingSessionsWithLink);
		dashboard.setAverageTimeTrainingModules(averageTimeTrainingModules);
		dashboard.setDeviationTimeTrainingModules(deviationTimeTrainingModules);
		dashboard.setMinimumTimeTrainingModules(minimumTimeTrainingModules);
		dashboard.setMaximumTimeTrainingModules(maximumTimeTrainingModules);

		super.getBuffer().addData(dashboard);
	}

	@Override
	public void unbind(final DeveloperDashboard object) {
		Dataset dataset;

		dataset = super.unbind(object, //
			"totalNumberOfTrainingModulesWithUpdatedMoment", //
			"totalNumberOfTrainingSessionsWithLink", //
			"averageTimeTrainingModules", // 
			"deviationTimeTrainingModules", //
			"minimumTimeTrainingModules", //
			"maximumTimeTrainingModules");

		super.getResponse().addData(dataset);
	}
}
