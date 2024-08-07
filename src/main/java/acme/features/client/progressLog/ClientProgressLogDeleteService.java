
package acme.features.client.progressLog;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.services.AbstractService;
import acme.entities.contracts.ProgressLog;
import acme.roles.Client;

@Service
public class ClientProgressLogDeleteService extends AbstractService<Client, ProgressLog> {

	//Internal state---------------------------------------------------------
	@Autowired
	private ClientProgressLogRepository cpr;


	//AbstractService interface----------------------------------------------
	@Override
	public void authorise() {
		boolean status;
		int masterId;
		ProgressLog progressLog;
		Client client;

		masterId = super.getRequest().getData("id", int.class);
		progressLog = this.cpr.findOneProgressLogById(masterId);
		client = progressLog == null ? null : progressLog.getContract().getClient();
		status = progressLog != null && progressLog.isDraftMode() && super.getRequest().getPrincipal().hasRole(client);

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
	public void bind(final ProgressLog object) {
		assert object != null;

		super.bind(object, "recordId", "percentageCompleteness", "comment", "registrationMoment", "responsiblePerson");

	}

	@Override
	public void validate(final ProgressLog object) {
		assert object != null;
	}

	@Override
	public void perform(final ProgressLog object) {
		assert object != null;

		this.cpr.delete(object);

	}

	@Override
	public void unbind(final ProgressLog object) {
		/*
		 * assert object != null;
		 * 
		 * Dataset dataset;
		 * 
		 * dataset = super.unbind(object, "recordId", "percentageCompleteness", "comment", "registrationMoment", "responsiblePerson");
		 * 
		 * super.getResponse().addData(dataset);
		 * dataset.put("draftMode", object.getContract().isDraftMode());
		 */

	}
}
