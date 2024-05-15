
package acme.features.auditor.codeAudit;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.client.views.SelectChoices;
import acme.entities.auditRecords.AuditRecord;
import acme.entities.auditRecords.Mark;
import acme.entities.codeAudits.CodeAudit;
import acme.entities.codeAudits.Type;
import acme.entities.projects.Project;
import acme.roles.Auditor;

@Service
public class AuditorCodeAuditShowService extends AbstractService<Auditor, CodeAudit> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private AuditorCodeAuditRepository repository;

	// AbstractService interface ----------------------------------------------


	@Override
	public void authorise() {
		boolean status;
		int codeAuditId;
		CodeAudit codeAudit;

		codeAuditId = super.getRequest().getData("id", int.class);
		codeAudit = this.repository.findOneCodeAuditById(codeAuditId);
		status = codeAudit != null && (!codeAudit.isDraftMode() || super.getRequest().getPrincipal().hasRole(codeAudit.getAuditor()));

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		CodeAudit objects;
		int id;

		id = super.getRequest().getData("id", int.class);
		objects = this.repository.findOneCodeAuditById(id);

		super.getBuffer().addData(objects);
	}

	@Override
	public void unbind(final CodeAudit object) {
		assert object != null;
		SelectChoices choices;
		SelectChoices choicesP;
		SelectChoices choicesM;
		Dataset dataset;
		Collection<AuditRecord> auditRecords;
		int id;
		id = super.getRequest().getData("id", int.class);

		auditRecords = this.repository.findManyAuditRecordByCodeAuditId(id);
		Collection<Project> projects;

		choices = SelectChoices.from(Type.class, object.getType());
		choicesM = SelectChoices.from(Mark.class, object.getMark(this.repository.findManyAuditRecordByCodeAuditId(id)));
		projects = this.repository.findManyProjectsByAvailability();
		choicesP = SelectChoices.from(projects, "code", object.getProject());

		dataset = super.unbind(object, "code", "execution", "type", "correctiveActions", "link", "draftMode");
		dataset.put("types", choices);
		dataset.put("project", choicesP.getSelected().getKey());
		dataset.put("projects", choicesP);
		dataset.put("draftMode", object.isDraftMode());
		dataset.put("mark", choicesM.getSelected().getKey());
		dataset.put("marks", choicesM);
		dataset.put("MarkShow", auditRecords.isEmpty());

		super.getResponse().addData(dataset);
	}

}
