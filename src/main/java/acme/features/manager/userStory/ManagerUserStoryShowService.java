
package acme.features.manager.userStory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.client.views.SelectChoices;
import acme.entities.projects.Priority;
import acme.entities.projects.UserStory;
import acme.roles.Manager;

@Service
public class ManagerUserStoryShowService extends AbstractService<Manager, UserStory> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private ManagerUserStoryRepository repository;

	// AbstractService interface ----------------------------------------------


	@Override
	public void authorise() {
		boolean status;
		int userStoryId;
		UserStory us;

		userStoryId = super.getRequest().getData("id", int.class);
		us = this.repository.findOneUserStoryById(userStoryId);
		status = super.getRequest().getPrincipal().hasRole(us.getManager());

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		UserStory object;
		int id;

		id = super.getRequest().getData("id", int.class);
		object = this.repository.findOneUserStoryById(id);

		super.getBuffer().addData(object);
	}

	@Override
	public void unbind(final UserStory object) {
		assert object != null;

		Dataset dataset;

		SelectChoices choices;

		choices = SelectChoices.from(Priority.class, object.getPriority());

		dataset = super.unbind(object, "title", "description", "acceptanceCriteria", "estimatedCost", "priority", "link", "draftMode");
		dataset.put("priorities", choices);

		super.getResponse().addData(dataset);
	}

}
