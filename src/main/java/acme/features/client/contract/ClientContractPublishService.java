
package acme.features.client.contract;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.datatypes.Money;
import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.client.views.SelectChoices;
import acme.entities.contracts.Contract;
import acme.entities.projects.Project;
import acme.roles.Client;

@Service
public class ClientContractPublishService extends AbstractService<Client, Contract> {

	// Internal state ---------------------------------------------------------
	@Autowired
	private ClientContractRepository ccr;

	// AbstractService interface ----------------------------------------------


	@Override
	public void authorise() {
		boolean status;
		int contractId;
		Contract contract;
		Client client;

		contractId = super.getRequest().getData("id", int.class);
		contract = this.ccr.findOneContractById(contractId);
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
			super.state(existing == null || existing.equals(object), "code", "client.contract.form.error.duplicated");
		}
		if (!super.getBuffer().getErrors().hasErrors("budget")) {
			super.state(object.getBudget().getAmount() >= 0, "budget", "client.contract.form.error.negative-budget");
			super.state(object.getBudget().getAmount() <= 1000000, "budget", "client.contract.form.error.much-budget");
			String currency = object.getBudget().getCurrency();
			boolean isValidCurrency = currency.equals("EUR") || currency.equals("GBP") || currency.equals("USD");
			super.state(isValidCurrency, "budget", "client.contract.form.error.invalid-currency");
		}
		{
			double totalBudgets = 0.0;
			Project project = object.getProject();
			if (project != null && object.getBudget() != null) {
				Money budget = new Money();
				Collection<Contract> contracts = this.ccr.findManyContractsAvailableByProjectId(project.getId());
				for (Contract c : contracts) {
					budget = this.ConvertToEuros(c.getBudget());
					totalBudgets += budget.getAmount();
				}

				totalBudgets += this.ConvertToEuros(object.getBudget()).getAmount();
				double projectCost = object.getProject().getCost();
				Money projectCostEUR = this.hoursToEuros(projectCost);

				super.state(totalBudgets <= projectCostEUR.getAmount(), "*", "client.contract.form.error.exceeded-project-cost");
			}
		}
	}
	public Money hoursToEuros(double projectCost) {
		projectCost = projectCost * 150;
		Money projectCostEUR = new Money();
		projectCostEUR.setAmount(projectCost);
		projectCostEUR.setCurrency("EUR");
		return projectCostEUR;
	}

	public Money ConvertToEuros(final Money dinero) {
		if (dinero.getCurrency().equals("GBP")) {
			dinero.setAmount(dinero.getAmount() * 1.18);
			dinero.setCurrency("EUR");
			return dinero;
		}

		else if (dinero.getCurrency().equals("USD")) {
			dinero.setAmount(dinero.getAmount() * 0.94);
			dinero.setCurrency("EUR");
			return dinero;
		}

		else
			return dinero;

	}
	@Override
	public void perform(final Contract object) {
		assert object != null;

		object.setDraftMode(false);
		this.ccr.save(object);
	}

	@Override
	public void unbind(final Contract object) {
		assert object != null;
		Dataset dataset;
		SelectChoices choicesP;
		Collection<Project> projects;

		projects = this.ccr.findManyProjects();
		choicesP = SelectChoices.from(projects, "code", object.getProject());

		dataset = super.unbind(object, "code", "instantiationMoment", "providerName", "customerName", "goals", "budget", "draftMode");
		dataset.put("project", choicesP.getSelected().getKey());
		dataset.put("projects", choicesP);

		super.getResponse().addData(dataset);
	}

}
