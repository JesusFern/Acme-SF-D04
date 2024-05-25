
package acme.features.developer.trainingSessions;

import java.time.temporal.ChronoUnit;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.models.Dataset;
import acme.client.helpers.MomentHelper;
import acme.client.services.AbstractService;
import acme.entities.trainingModule.TrainingModule;
import acme.entities.trainingModule.TrainingSession;
import acme.roles.Developer;

@Service
public class DeveloperTrainingSessionUpdateService extends AbstractService<Developer, TrainingSession> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected DeveloperTrainingSessionRepository repository;

	// AbstractService ---------------------------


	@Override
	public void authorise() {
		boolean status;
		int masterId;
		TrainingSession trainingSession;
		Developer developer;

		masterId = super.getRequest().getData("id", int.class);
		trainingSession = this.repository.findOneTrainingSessionById(masterId);
		developer = trainingSession == null ? null : trainingSession.getTrainingModule().getDeveloper();
		status = trainingSession != null && super.getRequest().getPrincipal().hasRole(developer);

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		TrainingSession object;
		int id;

		id = super.getRequest().getData("id", int.class);
		object = this.repository.findOneTrainingSessionById(id);

		super.getBuffer().addData(object);
	}

	@Override
	public void bind(final TrainingSession object) {
		assert object != null;

		super.bind(object, "code", "startPeriod", "endPeriod", "location", "instructor", "email", "link");
	}

	@Override
	public void validate(final TrainingSession object) {
		assert object != null;

		if (!super.getBuffer().getErrors().hasErrors("code")) {
			TrainingSession existing;

			existing = this.repository.findOneTrainingSessionByCode(object.getCode());
			super.state(existing == null || existing.equals(object), "code", "developer.tranining-session.form.error.duplicated");
		}

		if (!super.getBuffer().getErrors().hasErrors("startPeriod")) {
			TrainingModule module;
			int masterId;
			Date creation;

			masterId = super.getRequest().getData("masterId", int.class);
			module = this.repository.findOneTrainingModuleById(masterId);
			creation = MomentHelper.deltaFromMoment(module.getCreationMoment(), 6, ChronoUnit.DAYS);
			creation = MomentHelper.deltaFromMoment(creation, 23, ChronoUnit.HOURS);
			creation = MomentHelper.deltaFromMoment(creation, 59, ChronoUnit.MINUTES);
			super.state(MomentHelper.isAfter(object.getStartPeriod(), creation), "startPeriod", "developer.training-session.form.error.invalid-creation-moment");
		}
		if (!super.getBuffer().getErrors().hasErrors("startPeriod")) {
			Date maxStart;

			maxStart = java.sql.Date.valueOf("2200-12-25");
			super.state(MomentHelper.isBefore(object.getStartPeriod(), maxStart), "startPeriod", "developer.training-session.form.error.invalid-date");
		}

		if (!super.getBuffer().getErrors().hasErrors("endPeriod")) {
			Date minimumTime;

			minimumTime = MomentHelper.deltaFromMoment(object.getStartPeriod(), 6, ChronoUnit.DAYS);
			minimumTime = MomentHelper.deltaFromMoment(minimumTime, 23, ChronoUnit.HOURS);
			minimumTime = MomentHelper.deltaFromMoment(minimumTime, 59, ChronoUnit.MINUTES);
			super.state(MomentHelper.isAfter(object.getEndPeriod(), minimumTime), "endPeriod", "developer.training-session.form.error.minimum-time");
		}
		if (!super.getBuffer().getErrors().hasErrors("endPeriod")) {
			Date maxEnd;

			maxEnd = java.sql.Date.valueOf("2201-01-01");
			super.state(MomentHelper.isBefore(object.getEndPeriod(), maxEnd), "endPeriod", "developer.training-session.form.error.invalid-date");
		}
	}

	@Override
	public void perform(final TrainingSession object) {
		assert object != null;

		this.repository.save(object);
	}

	@Override
	public void unbind(final TrainingSession object) {
		assert object != null;
		Dataset dataset;

		dataset = super.unbind(object, "code", "startPeriod", "endPeriod", "location", "instructor", "email", "link");
		dataset.put("masterId", super.getRequest().getData("masterId", int.class));
		dataset.put("draftMode", object.getTrainingModule().isDraftMode());

		super.getResponse().addData(dataset);
	}
}
