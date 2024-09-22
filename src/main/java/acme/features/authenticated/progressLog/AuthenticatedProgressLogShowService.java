
package acme.features.authenticated.progressLog;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.accounts.Authenticated;
import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.client.views.SelectChoices;
import acme.entities.contracts.Contract;
import acme.entities.contracts.ProgressLog;

@Service
public class AuthenticatedProgressLogShowService extends AbstractService<Authenticated, ProgressLog> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private AuthenticatedProgressLogRepository apr;

	// AbstractService interface ----------------------------------------------


	@Override
	public void authorise() {
		boolean status;
		int progressLogId;
		Contract contract;

		progressLogId = super.getRequest().getData("id", int.class);
		contract = this.apr.findOneContractByProgressLogId(progressLogId);
		status = contract != null && (!contract.isDraftMode() || super.getRequest().getPrincipal().hasRole(contract.getClient()));

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		ProgressLog object;
		int id;

		id = super.getRequest().getData("id", int.class);
		object = this.apr.findOneProgressLogById(id);

		super.getBuffer().addData(object);
	}
	@Override
	public void unbind(final ProgressLog object) {
		assert object != null;

		SelectChoices choicesC;
		Dataset dataset;

		Collection<Contract> contracts;
		contracts = this.apr.findManyContract();
		choicesC = SelectChoices.from(contracts, "code", object.getContract());
		dataset = super.unbind(object, "recordId", "percentageCompleteness", "comment", "registrationMoment", "responsiblePerson");
		dataset.put("contract", choicesC.getSelected().getKey());
		dataset.put("contracts", choicesC);

		super.getResponse().addData(dataset);
	}

}
