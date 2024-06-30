
package acme.features.authenticated.contract;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.accounts.Authenticated;
import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.client.views.SelectChoices;
import acme.entities.contracts.Contract;
import acme.entities.projects.Project;

@Service
public class AuthenticatedContractShowService extends AbstractService<Authenticated, Contract> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private AuthenticatedContractRepository acr;

	// AbstractService interface ----------------------------------------------


	@Override
	public void authorise() {
		super.getResponse().setAuthorised(true);
	}

	@Override
	public void load() {
		Contract object;
		int id;

		id = super.getRequest().getData("id", int.class);
		object = this.acr.findOneContractById(id);

		super.getBuffer().addData(object);
	}

	@Override
	public void unbind(final Contract object) {
		assert object != null;

		SelectChoices choicesP;
		Dataset dataset;

		Collection<Project> projects;

		projects = this.acr.findManyProjectsByAvailability();
		choicesP = SelectChoices.from(projects, "code", object.getProject());
		dataset = super.unbind(object, "code", "instantiationMoment", "providerName", "customerName", "goals", "budget", "draftMode");
		dataset.put("project", choicesP.getSelected().getKey());
		dataset.put("projects", choicesP);

		super.getResponse().addData(dataset);
	}
}
