
package acme.features.authenticated.claim;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.accounts.Authenticated;
import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.entities.claims.Claim;

@Service
public class AuthenticatedClaimShowService extends AbstractService<Authenticated, Claim> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private AuthenticatedClaimRepository repository;

	// AbstractService interface ----------------------------------------------


	@Override
	public void authorise() {
		super.getResponse().setAuthorised(true);
	}

	@Override
	public void load() {
		Claim objects;
		int id;

		id = super.getRequest().getData("id", int.class);
		objects = this.repository.findOneClaimById(id);

		super.getBuffer().addData(objects);
	}

	@Override
	public void unbind(final Claim object) {
		assert object != null;
		Dataset dataset;

		dataset = super.unbind(object, "code", "instantiationMoment", "heading", "description", "department", "email", "link");

		super.getResponse().addData(dataset);
	}

}
