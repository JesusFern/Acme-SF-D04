
package acme.features.sponsor.sponsorship;

import java.time.temporal.ChronoUnit;
import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.models.Dataset;
import acme.client.helpers.MomentHelper;
import acme.client.services.AbstractService;
import acme.client.views.SelectChoices;
import acme.entities.projects.Project;
import acme.entities.sponsorships.Sponsorship;
import acme.entities.sponsorships.Type;
import acme.roles.Sponsor;

@Service
public class SponsorSponsorshipCreateService extends AbstractService<Sponsor, Sponsorship> {
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
		Sponsorship object;
		Sponsor sponsor;
		Date moment;

		sponsor = this.ssr.findOneSponsorById(super.getRequest().getPrincipal().getActiveRoleId());
		moment = MomentHelper.getCurrentMoment();

		object = new Sponsorship();
		object.setCode("");
		object.setMoment(moment);
		object.setType(Type.Financial);
		object.setEmail("");
		object.setLink("");
		object.setDraftMode(true);
		object.setSponsor(sponsor);
		object.setProject(null);
		super.getBuffer().addData(object);
	}

	@Override
	public void bind(final Sponsorship object) {
		assert object != null;

		int projectId;
		Project project;

		projectId = super.getRequest().getData("project", int.class);
		project = this.ssr.findOneProjectById(projectId);

		super.bind(object, "code", "startSponsor", "endSponsor", "amount", "type", "email", "link");
		object.setProject(project);
	}

	@Override
	public void validate(final Sponsorship object) {
		assert object != null;

		if (!super.getBuffer().getErrors().hasErrors("code")) {
			Sponsorship existing;

			existing = this.ssr.findOneSponsorshipByCode(object.getCode());
			super.state(existing == null, "code", "sponsor.sponsorship.form.error.duplicated");
		}

		if (!super.getBuffer().getErrors().hasErrors("startSponsor")) {
			Date minimumStart;

			minimumStart = java.sql.Date.valueOf("1999-12-31");
			minimumStart = MomentHelper.deltaFromMoment(minimumStart, 23, ChronoUnit.HOURS);
			minimumStart = MomentHelper.deltaFromMoment(minimumStart, 59, ChronoUnit.MINUTES);
			super.state(MomentHelper.isAfter(object.getStartSponsor(), minimumStart), "startSponsor", "sponsor.sponsorship.form.error.wrong-date");
		}

		if (!super.getBuffer().getErrors().hasErrors("startSponsor"))
			super.state(MomentHelper.isAfter(object.getStartSponsor(), object.getMoment()), "startSponsor", "sponsor.sponsorship.form.error.wrong-date");

		if (!super.getBuffer().getErrors().hasErrors("startSponsor")) {
			Date minimumEnd;

			minimumEnd = java.sql.Date.valueOf("2200-11-30");
			minimumEnd = MomentHelper.deltaFromMoment(minimumEnd, 1, ChronoUnit.MONTHS);
			super.state(MomentHelper.isBefore(object.getStartSponsor(), minimumEnd), "startSponsor", "sponsor.sponsorship.form.error.wrong-date");
		}

		if (!super.getBuffer().getErrors().hasErrors("endSponsor")) {
			Date minimumEnd;

			minimumEnd = MomentHelper.deltaFromMoment(object.getStartSponsor(), 1, ChronoUnit.MONTHS);
			super.state(MomentHelper.isAfter(object.getEndSponsor(), minimumEnd), "endSponsor", "sponsor.sponsorship.form.error.too-close");
		}

		if (!super.getBuffer().getErrors().hasErrors("endSponsor")) {
			Date minimumEnd;

			minimumEnd = java.sql.Date.valueOf("2201-01-01");
			super.state(MomentHelper.isBefore(object.getEndSponsor(), minimumEnd), "endSponsor", "sponsor.sponsorship.form.error.wrong-date");
		}

		if (!super.getBuffer().getErrors().hasErrors("amount")) {
			super.state(object.getAmount().getAmount() > 0, "amount", "sponsor.sponsorship.form.error.negative-amount");
			super.state(object.getAmount().getAmount() <= 1000000, "amount", "sponsor.sponsorship.form.error.too-high-amount");
		}
	}

	@Override
	public void perform(final Sponsorship object) {
		assert object != null;

		this.ssr.save(object);
	}

	@Override
	public void unbind(final Sponsorship object) {
		assert object != null;

		SelectChoices choices;
		SelectChoices choicesP;
		Dataset dataset;

		Collection<Project> projects;

		choices = SelectChoices.from(Type.class, object.getType());
		projects = this.ssr.findManyProjectsByAvailability();
		choicesP = SelectChoices.from(projects, "code", object.getProject());
		dataset = super.unbind(object, "code", "moment", "startSponsor", "endSponsor", "amount", "email", "link", "draftMode");
		dataset.put("types", choices);
		dataset.put("project", choicesP.getSelected().getKey());
		dataset.put("projects", choicesP);

		super.getResponse().addData(dataset);
	}
}
