
package acme.features.authenticated.auditRecord;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.accounts.Authenticated;
import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.client.views.SelectChoices;
import acme.entities.auditRecords.AuditRecord;
import acme.entities.auditRecords.Mark;

@Service
public class AuthenticatedAuditRecordShowService extends AbstractService<Authenticated, AuditRecord> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private AuthenticatedAuditRecordRepository repository;

	// AbstractService interface ----------------------------------------------


	@Override
	public void authorise() {
		super.getResponse().setAuthorised(true);
	}

	@Override
	public void load() {
		AuditRecord objects;
		int id;

		id = super.getRequest().getData("id", int.class);
		objects = this.repository.findOneAuditRecordById(id);

		super.getBuffer().addData(objects);
	}

	@Override
	public void unbind(final AuditRecord object) {
		assert object != null;
		SelectChoices choices;

		Dataset dataset;
		choices = SelectChoices.from(Mark.class, object.getMark());

		dataset = super.unbind(object, "code", "periodStart", "periodEnd", "mark", "link");
		dataset.put("masterId", object.getCodeAudit().getId());
		dataset.put("marks", choices);

		super.getResponse().addData(dataset);
	}

}
