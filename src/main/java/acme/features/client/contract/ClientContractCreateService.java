
package acme.features.client.contract;

import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.models.Dataset;
import acme.client.helpers.MomentHelper;
import acme.client.services.AbstractService;
import acme.client.views.SelectChoices;
import acme.entities.contracts.Contract;
import acme.entities.projects.Project;
import acme.roles.Client;

@Service
public class ClientContractCreateService extends AbstractService<Client, Contract> {

	// Internal state ---------------------------------------------------------
	@Autowired
	private ClientContractRepository ccr;

	// AbstractService interface ----------------------------------------------


	@Override
	public void authorise() {
		super.getResponse().setAuthorised(true);
	}

	@Override
	public void load() {
		Contract object;
		Client client;
		Date moment;
		client = this.ccr.findOneClientById(super.getRequest().getPrincipal().getActiveRoleId());
		moment = MomentHelper.getCurrentMoment();
		object = new Contract();
		object.setDraftMode(true);
		object.setInstantiationMoment(moment);
		object.setClient(client);

		super.getBuffer().addData(object);
	}

	@Override
	public void bind(final Contract object) {
		assert object != null;

		Project project;
		int projectId;
		projectId = super.getRequest().getData("project", int.class);
		project = this.ccr.findOneProjectById(projectId);

		super.bind(object, "code", "providerName", "customerName", "goals", "budget");
		object.setProject(project);

	}

	@Override
	public void validate(final Contract object) {
		assert object != null;
		if (!super.getBuffer().getErrors().hasErrors("code")) {
			Contract existing;

			existing = this.ccr.findOneContractByCode(object.getCode());
			super.state(existing == null, "code", "client.contract.form.error.duplicated");
		}
		if (!super.getBuffer().getErrors().hasErrors("budget")) {
			super.state(object.getBudget().getAmount() >= 0, "budget", "client.contract.form.error.negative-budget");

			super.state(object.getBudget().getAmount() <= 1000000, "budget", "client.contract.form.error.much-budget");
		}
	}
	@Override
	public void perform(final Contract object) {
		assert object != null;
		this.ccr.save(object);
	}

	@Override
	public void unbind(final Contract object) {
		assert object != null;

		SelectChoices choicesP;
		Dataset dataset;
		Collection<Project> projects;
		projects = this.ccr.findManyProjectsByAvailability();
		choicesP = SelectChoices.from(projects, "code", object.getProject());
		dataset = super.unbind(object, "code", "instantiationMoment", "providerName", "customerName", "goals", "budget", "draftMode");
		dataset.put("project", choicesP.getSelected().getKey());
		dataset.put("projects", choicesP);

		super.getResponse().addData(dataset);
	}

}
