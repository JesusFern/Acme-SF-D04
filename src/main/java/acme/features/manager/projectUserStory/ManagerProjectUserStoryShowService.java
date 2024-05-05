
package acme.features.manager.projectUserStory;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.accounts.Principal;
import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.client.views.SelectChoices;
import acme.entities.projects.ProjectUserStory;
import acme.entities.projects.UserStory;
import acme.roles.Manager;

@Service
public class ManagerProjectUserStoryShowService extends AbstractService<Manager, ProjectUserStory> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private ManagerProjectUserStoryRepository repository;

	// AbstractService interface ----------------------------------------------


	@Override
	public void authorise() {
		boolean status;
		int puId;
		ProjectUserStory pu;

		puId = super.getRequest().getData("id", int.class);
		pu = this.repository.findOneProjectUserStoryById(puId);
		status = pu != null && (!pu.getProject().isDraftMode() || super.getRequest().getPrincipal().hasRole(pu.getProject().getManager()));

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		ProjectUserStory object;
		int id;

		id = super.getRequest().getData("id", int.class);
		object = this.repository.findOneProjectUserStoryById(id);

		super.getBuffer().addData(object);
	}

	@Override
	public void unbind(final ProjectUserStory object) {
		assert object != null;

		Dataset dataset;
		Collection<UserStory> userStories;
		SelectChoices choicesU;
		Principal principal;

		principal = super.getRequest().getPrincipal();
		userStories = this.repository.findManyUserStoriesByManagerId(principal.getActiveRoleId());
		choicesU = SelectChoices.from(userStories, "title", object.getUserStory());

		dataset = super.unbind(object, "optional");

		dataset.put("userStories", choicesU);
		dataset.put("draftMode", object.getProject().isDraftMode());

		super.getResponse().addData(dataset);
	}

}
