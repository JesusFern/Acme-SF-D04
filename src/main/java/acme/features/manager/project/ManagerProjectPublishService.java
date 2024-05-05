
package acme.features.manager.project;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.entities.projects.Project;
import acme.entities.projects.UserStory;
import acme.roles.Manager;

@Service
public class ManagerProjectPublishService extends AbstractService<Manager, Project> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private ManagerProjectRepository repository;

	// AbstractService interface ----------------------------------------------


	@Override
	public void authorise() {
		boolean status;
		int projectId;
		Project project;
		Manager manager;

		projectId = super.getRequest().getData("id", int.class);
		project = this.repository.findOneProjectById(projectId);
		manager = project == null ? null : project.getManager();
		status = project != null && project.isDraftMode() && super.getRequest().getPrincipal().hasRole(manager);

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		Project object;
		int id;

		id = super.getRequest().getData("id", int.class);
		object = this.repository.findOneProjectById(id);

		super.getBuffer().addData(object);
	}

	@Override
	public void bind(final Project object) {
		assert object != null;

		super.bind(object, "code", "title", "abstractString", "indication", "cost", "link");
	}

	@Override
	public void validate(final Project object) {
		assert object != null;

		if (!super.getBuffer().getErrors().hasErrors("indication"))
			super.state(!object.isIndication(), "indication", "manager.project.form.error.indication");

		{
			Collection<UserStory> userStories;
			boolean allPublished;

			userStories = this.repository.findManyUserStoriesByProjectId(object.getId());
			allPublished = userStories.stream().allMatch(userStory -> !userStory.isDraftMode());
			super.state(allPublished, "*", "manager.project.form.error.userStories");
		}
		{
			Collection<UserStory> userStories;
			boolean existsUserStories;

			userStories = this.repository.findManyUserStoriesByProjectId(object.getId());
			existsUserStories = userStories.size() > 0;
			super.state(existsUserStories, "*", "manager.project.form.error.existsUserStories");
		}
	}

	@Override
	public void perform(final Project object) {
		assert object != null;

		object.setDraftMode(false);
		this.repository.save(object);
	}

	@Override
	public void unbind(final Project object) {
		assert object != null;

		Dataset dataset;

		dataset = super.unbind(object, "code", "title", "abstractString", "indication", "cost", "link", "draftMode");

		super.getResponse().addData(dataset);
	}

}
