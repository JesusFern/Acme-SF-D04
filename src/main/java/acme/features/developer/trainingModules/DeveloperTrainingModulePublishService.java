
package acme.features.developer.trainingModules;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.services.AbstractService;
import acme.entities.projects.Project;
import acme.entities.trainingModule.TrainingModule;
import acme.entities.trainingModule.TrainingSession;
import acme.roles.Developer;

@Service
public class DeveloperTrainingModulePublishService extends AbstractService<Developer, TrainingModule> {

	// Internal state ---------------------------------------------------------
	@Autowired
	private DeveloperTrainingModuleRepository repository;

	// AbstractService interface ----------------------------------------------


	@Override
	public void authorise() {
		boolean status;

		int masterId;
		TrainingModule trainingModule;
		Developer developer;

		masterId = super.getRequest().getData("id", int.class);
		trainingModule = this.repository.findOneTrainingModuleById(masterId);
		developer = trainingModule == null ? null : trainingModule.getDeveloper();
		status = trainingModule != null && trainingModule.isDraftMode() && super.getRequest().getPrincipal().hasRole(developer);

		super.getResponse().setAuthorised(status);

	}

	@Override
	public void load() {
		TrainingModule object;
		int id;

		id = super.getRequest().getData("id", int.class);
		object = this.repository.findOneTrainingModuleById(id);

		super.getBuffer().addData(object);
	}

	@Override
	public void bind(final TrainingModule object) {
		assert object != null;
		Project project;
		int projectId;

		projectId = super.getRequest().getData("project", int.class);
		project = this.repository.findOneProjectById(projectId);
		super.bind(object, "code", "creationMoment", "details", "difficultyLevel", "updateMoment", "link", "time", "project");
		object.setProject(project);

	}
	@Override
	public void validate(final TrainingModule object) {
		assert object != null;
		Collection<TrainingSession> trainingSessions;
		boolean existsTrainingSessions;
		int id;

		id = super.getRequest().getData("id", int.class);
		trainingSessions = this.repository.findAllTrainingSessionsByTrainingModuleId(id);
		existsTrainingSessions = !trainingSessions.isEmpty();

		super.state(existsTrainingSessions, "*", "developer.training-module.form.error.existsTrainingSessions");
	}

	@Override
	public void perform(final TrainingModule object) {
		assert object != null;

		object.setDraftMode(false);
		this.repository.save(object);
	}

}
