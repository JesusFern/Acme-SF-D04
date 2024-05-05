
package acme.features.authenticated.codeAudit;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.accounts.Authenticated;
import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.entities.codeAudits.CodeAudit;

@Service
public class AuthenticatedCodeAuditListAllService extends AbstractService<Authenticated, CodeAudit> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private AuthenticatedCodeAuditRepository repository;

	// AbstractService interface ----------------------------------------------


	@Override
	public void authorise() {
		super.getResponse().setAuthorised(true);
	}

	@Override
	public void load() {
		Collection<CodeAudit> objects;

		objects = this.repository.findManyCodeAuditsByAvailability();

		super.getBuffer().addData(objects);
	}

	@Override
	public void unbind(final CodeAudit object) {
		assert object != null;

		Dataset dataset;

		dataset = super.unbind(object, "code", "correctiveActions");

		super.getResponse().addData(dataset);
	}
}
