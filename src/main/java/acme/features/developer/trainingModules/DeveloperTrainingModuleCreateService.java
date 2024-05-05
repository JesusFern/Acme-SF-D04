
package acme.features.developer.trainingModules;

import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.models.Dataset;
import acme.client.helpers.MomentHelper;
import acme.client.services.AbstractService;
import acme.client.views.SelectChoices;
import acme.entities.projects.Project;
import acme.entities.trainingModule.DifficultyLevel;
import acme.entities.trainingModule.TrainingModule;
import acme.roles.Developer;

@Service
public class DeveloperTrainingModuleCreateService extends AbstractService<Developer, TrainingModule> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private DeveloperTrainingModuleRepository repository;

	// AbstractService interface ----------------------------------------------


	@Override
	public void authorise() {
		super.getResponse().setAuthorised(true);
	}

	@Override
	public void load() {
		TrainingModule object;
		Developer developer;

		developer = this.repository.findOneDeveloperById(super.getRequest().getPrincipal().getActiveRoleId());

		object = new TrainingModule();
		object.setDraftMode(true);
		object.setDeveloper(developer);

		super.getBuffer().addData(object);
	}

	@Override
	public void bind(final TrainingModule object) {
		assert object != null;

		int projectId;
		Project project;

		projectId = super.getRequest().getData("project", int.class);
		project = this.repository.findOneProjectById(projectId);

		super.bind(object, "code", "creationMoment", "details", "difficultyLevel", "updateMoment", "link", "time", "project");

		Date currentMoment = MomentHelper.getCurrentMoment();
		Date creationMoment = new Date(currentMoment.getTime() - 600000);

		object.setCreationMoment(creationMoment);
		object.setProject(project);

	}

	@Override
	public void validate(final TrainingModule object) {
		assert object != null;

		boolean sameCode;

		if (!super.getBuffer().getErrors().hasErrors("code")) {
			sameCode = this.repository.findAllTrainingModules().stream().anyMatch(t -> t.getCode().equals(object.getCode()));

			super.state(!sameCode, "code", "developer.training-module.form.error.same-code");

		}
	}
	@Override
	public void perform(final TrainingModule object) {
		assert object != null;
		this.repository.save(object);
	}

	@Override
	public void unbind(final TrainingModule object) {
		assert object != null;

		Collection<Project> projects;
		SelectChoices levelChoices;
		SelectChoices projectChoices;
		Dataset dataset;

		projects = this.repository.findManyProjectsByAvailability();
		projectChoices = SelectChoices.from(projects, "code", object.getProject());
		levelChoices = SelectChoices.from(DifficultyLevel.class, object.getDifficultyLevel());

		dataset = super.unbind(object, "code", "creationMoment", "details", "difficultyLevel", "updateMoment", "link", "time", "project", "draftMode");
		dataset.put("project", projectChoices.getSelected().getKey());
		dataset.put("projects", projectChoices);
		dataset.put("difficultyLevels", levelChoices);

		super.getResponse().addData(dataset);
	}
}
