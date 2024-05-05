
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
		super.getResponse().setAuthorised(true);
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
		Date instantiationMoment;

		instantiationMoment = MomentHelper.getCurrentMoment();
		super.bind(object, "startDisplayPeriod", "endDisplayPeriod", "pictureLink", "slogan", "targetWebDocumentLink");
		object.setInstantiationMoment(instantiationMoment);

	}

	@Override
	public void validate(final Banner object) {
		assert object != null;

		Date firstDate = MomentHelper.parse("2000-01-01 00:00", "yyyy-MM-dd HH:mm");
		Date lastDate = MomentHelper.parse("2100-12-31 23:59", "yyyy-MM-dd HH:mm");

		if (!super.getBuffer().getErrors().hasErrors("startDisplayPeriod"))
			super.state(MomentHelper.isAfterOrEqual(object.getStartDisplayPeriod(), firstDate), "startDisplayPeriod", "administrator.banner.form.error.first-date");

		if (!super.getBuffer().getErrors().hasErrors("startDisplayPeriod"))
			super.state(MomentHelper.isBeforeOrEqual(object.getStartDisplayPeriod(), MomentHelper.deltaFromMoment(lastDate, -7, ChronoUnit.DAYS)), "startDisplayPeriod", "administrator.banner.form.error.start-period");

		if (!super.getBuffer().getErrors().hasErrors("startDisplayPeriod"))
			super.state(MomentHelper.isAfterOrEqual(object.getStartDisplayPeriod(), object.getInstantiationMoment()), "startDisplayPeriod", "administrator.banner.form.error.after-instantiation-moment");

		if (!super.getBuffer().getErrors().hasErrors("endDisplayPeriod"))
			super.state(MomentHelper.isAfterOrEqual(object.getEndDisplayPeriod(), firstDate), "endDisplayPeriod", "administrator.banner.form.error.after-first-date");

		if (!super.getBuffer().getErrors().hasErrors("endDisplayPeriod"))
			super.state(MomentHelper.isBeforeOrEqual(object.getEndDisplayPeriod(), lastDate), "endDisplayPeriod", "administrator.banner.form.error.before-last-date");

		if (!super.getBuffer().getErrors().hasErrors("endDisplayPeriod"))
			super.state(MomentHelper.isAfterOrEqual(object.getEndDisplayPeriod(), MomentHelper.deltaFromMoment(firstDate, 7, ChronoUnit.DAYS)), "endDisplayPeriod", "administrator.banner.form.error.end-period");

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

		dataset = super.unbind(object, "startDisplayPeriod", "endDisplayPeriod", "pictureLink", "slogan", "targetWebDocumentLink");
		dataset.put("instantiationMoment", object.getInstantiationMoment());
		super.getResponse().addData(dataset);
	}
}
