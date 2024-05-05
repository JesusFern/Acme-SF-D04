
package acme.features.authenticated.codeAudit;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.accounts.Authenticated;
import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.client.views.SelectChoices;
import acme.entities.codeAudits.CodeAudit;
import acme.entities.codeAudits.Type;
import acme.entities.projects.Project;
import acme.features.auditor.codeAudit.AuditorCodeAuditRepository;

@Service
public class AuthenticatedCodeAuditShowService extends AbstractService<Authenticated, CodeAudit> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private AuditorCodeAuditRepository repository;

	// AbstractService interface ----------------------------------------------


	@Override
	public void authorise() {
		super.getResponse().setAuthorised(true);
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
		Dataset dataset;

		int auditorId;
		Collection<Project> projects;

		auditorId = object.getAuditor().getId();

		choices = SelectChoices.from(Type.class, object.getType());
		projects = this.repository.findManyProjectsByAvailability();
		choicesP = SelectChoices.from(projects, "code", object.getProject());

		dataset = super.unbind(object, "code", "execution", "type", "correctiveActions", "link", "draftMode");
		dataset.put("types", choices);
		dataset.put("project", choicesP.getSelected().getKey());
		dataset.put("projects", choicesP);
		dataset.put("draftMode", object.isDraftMode());

		super.getResponse().addData(dataset);
	}

}
