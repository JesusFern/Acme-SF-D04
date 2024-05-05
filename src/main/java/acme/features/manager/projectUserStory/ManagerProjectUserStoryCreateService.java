
package acme.features.manager.projectUserStory;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.accounts.Principal;
import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.client.views.SelectChoices;
import acme.entities.projects.Project;
import acme.entities.projects.ProjectUserStory;
import acme.entities.projects.UserStory;
import acme.roles.Manager;

@Service
public class ManagerProjectUserStoryCreateService extends AbstractService<Manager, ProjectUserStory> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private ManagerProjectUserStoryRepository repository;

	// AbstractService interface ----------------------------------------------


	@Override
	public void authorise() {
		super.getResponse().setAuthorised(true);
	}

	@Override
	public void load() {
		ProjectUserStory object;
		int masterId;
		Project project;

		masterId = super.getRequest().getData("masterId", int.class);
		project = this.repository.findOneProjectById(masterId);

		object = new ProjectUserStory();
		object.setProject(project);
		object.setUserStory(null);

		super.getBuffer().addData(object);
	}

	@Override
	public void bind(final ProjectUserStory object) {
		assert object != null;
		int userStoryId;
		UserStory userStory;

		super.bind(object, "optional");

		userStoryId = super.getRequest().getData("userStory", int.class);
		userStory = this.repository.findOneUserStoryById(userStoryId);
		object.setUserStory(userStory);

	}

	@Override
	public void validate(final ProjectUserStory object) {
		assert object != null;

		if (!super.getBuffer().getErrors().hasErrors()) {
			super.state(object.getProject().getManager().equals(object.getUserStory().getManager()), "project", "manager.project-user-story.form.error.not-same-manager");

			ProjectUserStory pu;
			pu = this.repository.findOneProjectUserStoriesByProjectAndUserStoryId(object.getProject().getId(), object.getUserStory().getId());
			super.state(pu == null, "*", "manager.project-user-story.form.error.existing-p-u-s");
		}
	}

	@Override
	public void perform(final ProjectUserStory object) {
		assert object != null;

		this.repository.save(object);
	}

	@Override
	public void unbind(final ProjectUserStory object) {
		assert object != null;

		Collection<UserStory> userStories;

		SelectChoices choicesU;
		Dataset dataset;

		dataset = super.unbind(object, "optional");

		Principal principal;

		principal = super.getRequest().getPrincipal();

		userStories = this.repository.findManyUserStoriesByManagerId(principal.getActiveRoleId());
		choicesU = SelectChoices.from(userStories, "title", object.getUserStory());

		dataset.put("userStories", choicesU);
		dataset.put("masterId", super.getRequest().getData("masterId", int.class));

		super.getResponse().addData(dataset);
	}

}
