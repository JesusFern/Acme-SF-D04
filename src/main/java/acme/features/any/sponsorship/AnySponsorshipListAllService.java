
package acme.features.any.sponsorship;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.accounts.Any;
import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.entities.sponsorships.Sponsorship;
import acme.features.sponsor.sponsorship.SponsorSponsorshipRepository;

@Service
public class AnySponsorshipListAllService extends AbstractService<Any, Sponsorship> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private SponsorSponsorshipRepository ssr;

	// AbstractService interface ----------------------------------------------


	@Override
	public void authorise() {
		super.getResponse().setAuthorised(true);
	}

	@Override
	public void load() {
		Collection<Sponsorship> objects;

		objects = this.ssr.findManySponsorshipsByAvailability();

		super.getBuffer().addData(objects);
	}

	@Override
	public void unbind(final Sponsorship object) {
		assert object != null;

		Dataset dataset;

		dataset = super.unbind(object, "code", "amount", "type", "amount");

		super.getResponse().addData(dataset);
	}

}
