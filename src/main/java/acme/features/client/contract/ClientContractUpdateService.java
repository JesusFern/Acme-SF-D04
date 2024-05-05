
package acme.features.client.contract;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.client.views.SelectChoices;
import acme.entities.contracts.Contract;
import acme.entities.projects.Project;
import acme.roles.Client;

@Service
public class ClientContractUpdateService extends AbstractService<Client, Contract> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private ClientContractRepository ccr;

	// AbstractService interface ----------------------------------------------


	@Override
	public void authorise() {
		boolean status;
		int masterId;
		Contract contract;
		Client client;

		masterId = super.getRequest().getData("id", int.class);
		contract = this.ccr.findOneContractById(masterId);
		client = contract == null ? null : contract.getClient();
		status = contract != null && contract.isDraftMode() && super.getRequest().getPrincipal().hasRole(client);

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		Contract object;
		int id;

		id = super.getRequest().getData("id", int.class);
		object = this.ccr.findOneContractById(id);

		super.getBuffer().addData(object);
	}

	@Override
	public void bind(final Contract object) {
		assert object != null;
		int projectId;
		Project project;

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
			super.state(existing == null || existing.equals(object), "code", "client.contract.form.error.duplicated");
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
