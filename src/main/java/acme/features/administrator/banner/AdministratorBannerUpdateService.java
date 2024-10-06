
package acme.features.administrator.banner;

import java.time.temporal.ChronoUnit;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.accounts.Administrator;
import acme.client.data.models.Dataset;
import acme.client.helpers.MomentHelper;
import acme.client.services.AbstractService;
import acme.entities.banners.Banner;

@Service
public class AdministratorBannerUpdateService extends AbstractService<Administrator, Banner> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private AdministratorBannerRepository repository;

	// AbstractService interface ----------------------------------------------


	@Override
	public void authorise() {
		boolean status;
		Banner banner;
		int masterId;
		masterId = super.getRequest().getData("id", int.class);

		banner = this.repository.findOneBannerById(masterId);
		status = banner != null;
		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		Banner object;
		int id;

		id = super.getRequest().getData("id", int.class);
		object = this.repository.findOneBannerById(id);

		super.getBuffer().addData(object);
	}

	@Override
	public void bind(final Banner object) {
		assert object != null;

		super.bind(object, "instantiationMoment", "startDisplayPeriod", "endDisplayPeriod", "pictureLink", "slogan", "targetWebDocumentLink");

	}

	@Override
	public void validate(final Banner object) {
		assert object != null;
		Date lastDate = MomentHelper.parse("2200-12-31 23:59", "yyyy-MM-dd HH:mm");

		if (!super.getBuffer().getErrors().hasErrors("startDisplayPeriod"))
			super.state(MomentHelper.isBeforeOrEqual(object.getStartDisplayPeriod(), MomentHelper.deltaFromMoment(lastDate, -7, ChronoUnit.DAYS)), "startDisplayPeriod", "administrator.banner.form.error.start-period");

		if (!super.getBuffer().getErrors().hasErrors("startDisplayPeriod"))
			super.state(MomentHelper.isAfterOrEqual(object.getStartDisplayPeriod(), object.getInstantiationMoment()), "startDisplayPeriod", "administrator.banner.form.error.after-instantiation-moment");

		if (!super.getBuffer().getErrors().hasErrors("endDisplayPeriod"))
			super.state(MomentHelper.isBeforeOrEqual(object.getEndDisplayPeriod(), lastDate), "endDisplayPeriod", "administrator.banner.form.error.before-last-date");

		if (!super.getBuffer().getErrors().hasErrors("endDisplayPeriod"))
			super.state(MomentHelper.isAfterOrEqual(object.getEndDisplayPeriod(), MomentHelper.deltaFromMoment(object.getInstantiationMoment(), 7, ChronoUnit.DAYS)), "endDisplayPeriod", "administrator.banner.form.error.end-period");

		if (!super.getBuffer().getErrors().hasErrors("startDisplayPeriod") && !super.getBuffer().getErrors().hasErrors("endDisplayPeriod")) {

			Date period;
			period = MomentHelper.deltaFromMoment(object.getStartDisplayPeriod(), 7, ChronoUnit.DAYS);

			super.state(MomentHelper.isAfterOrEqual(object.getEndDisplayPeriod(), period), "endDisplayPeriod", "administrator.banner.form.error.short-period");
		}
	}

	@Override
	public void perform(final Banner object) {
		assert object != null;

		this.repository.save(object);
	}

	@Override
	public void unbind(final Banner object) {
		assert object != null;

		Dataset dataset;

		dataset = super.unbind(object, "instantiationMoment", "startDisplayPeriod", "endDisplayPeriod", "pictureLink", "slogan", "targetWebDocumentLink");
		super.getResponse().addData(dataset);
	}
}
