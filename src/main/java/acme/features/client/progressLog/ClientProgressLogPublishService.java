
package acme.features.client.progressLog;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.entities.contracts.ProgressLog;
import acme.roles.Client;

@Service
public class ClientProgressLogPublishService extends AbstractService<Client, ProgressLog> {

	@Autowired
	private ClientProgressLogRepository repository;


	@Override
	public void authorise() {
		boolean status;
		int plId;
		ProgressLog progressLog;
		Client client;

		plId = super.getRequest().getData("id", int.class);
		progressLog = this.repository.findOneProgressLogById(plId);
		client = progressLog == null ? null : progressLog.getContract().getClient();
		status = progressLog != null && progressLog.isDraftMode() && super.getRequest().getPrincipal().hasRole(client);

		super.getResponse().setAuthorised(status);
	}
	@Override
	public void load() {
		ProgressLog object;
		int id;

		id = super.getRequest().getData("id", int.class);
		object = this.repository.findOneProgressLogById(id);

		super.getBuffer().addData(object);
	}
	@Override
	public void bind(final ProgressLog object) {
		assert object != null;

		super.bind(object, "recordId", "percentageCompleteness", "comment", "responsiblePerson");
	}

	@Override
	public void validate(final ProgressLog object) {
		assert object != null;
	}
	@Override
	public void perform(final ProgressLog object) {
		assert object != null;

		object.setDraftMode(false);
		this.repository.save(object);
	}

	@Override
	public void unbind(final ProgressLog object) {
		assert object != null;

		Dataset dataset;

		dataset = super.unbind(object, "recordId", "percentageCompleteness", "comment", "registrationMoment", "responsiblePerson", "draftMode");

		super.getResponse().addData(dataset);
	}

}
