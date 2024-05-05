
package acme.features.client.progressLog;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.models.Dataset;
import acme.client.helpers.MomentHelper;
import acme.client.services.AbstractService;
import acme.entities.contracts.Contract;
import acme.entities.contracts.ProgressLog;
import acme.roles.Client;

@Service
public class ClientProgressLogCreateService extends AbstractService<Client, ProgressLog> {

	//Internal state---------------------------------------------------------
	@Autowired
	private ClientProgressLogRepository cpr;


	//AbstractService interface----------------------------------------------
	@Override
	public void authorise() {

		super.getResponse().setAuthorised(true);
	}

	@Override
	public void load() {
		ProgressLog object;

		Date moment;
		int masterId;
		Contract contract;
		masterId = super.getRequest().getData("masterId", int.class);
		contract = this.cpr.findOneContractById(masterId);
		moment = MomentHelper.getCurrentMoment();

		object = new ProgressLog();
		object.setRecordId("");
		object.setRegistrationMoment(moment);
		object.setContract(contract);
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
		if (!super.getBuffer().getErrors().hasErrors("recordId")) {
			ProgressLog existing;

			existing = this.cpr.findOneProgressLogByRecordId(object.getRecordId());
			super.state(existing == null, "recordId", "client.progress-log.form.error.duplicated");
		}
	}

	@Override
	public void perform(final ProgressLog object) {
		assert object != null;

		this.cpr.save(object);

	}
	@Override
	public void unbind(final ProgressLog object) {
		assert object != null;
		Dataset dataset;

		dataset = super.unbind(object, "recordId", "percentageCompleteness", "comment", "registrationMoment", "responsiblePerson");
		dataset.put("masterId", super.getRequest().getData("masterId", int.class));

		super.getResponse().addData(dataset);
	}

}
