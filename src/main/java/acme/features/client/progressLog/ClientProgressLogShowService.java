
package acme.features.client.progressLog;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.client.views.SelectChoices;
import acme.entities.contracts.Contract;
import acme.entities.contracts.ProgressLog;
import acme.roles.Client;

@Service
public class ClientProgressLogShowService extends AbstractService<Client, ProgressLog> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private ClientProgressLogRepository cpr;

	// AbstractService interface ----------------------------------------------


	@Override
	public void authorise() {
		boolean status;
		int progressLogId;
		Contract contract;

		progressLogId = super.getRequest().getData("id", int.class);
		contract = this.cpr.findOneContractByProgressLogId(progressLogId);
		status = contract != null && (!contract.isDraftMode() || super.getRequest().getPrincipal().hasRole(contract.getClient()));

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		ProgressLog object;
		int id;

		id = super.getRequest().getData("id", int.class);
		object = this.cpr.findOneProgressLogById(id);

		super.getBuffer().addData(object);
	}
	@Override
	public void unbind(final ProgressLog object) {
		assert object != null;

		assert object != null;

		SelectChoices choicesC;
		Dataset dataset;

		Collection<Contract> contracts;
		contracts = this.cpr.findManyContract();
		choicesC = SelectChoices.from(contracts, "code", object.getContract());
		dataset = super.unbind(object, "recordId", "percentageCompleteness", "comment", "registrationMoment", "responsiblePerson");
		dataset.put("contract", choicesC.getSelected().getKey());
		dataset.put("contracts", choicesC);

		super.getResponse().addData(dataset);
	}

}
