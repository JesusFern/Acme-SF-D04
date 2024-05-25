/*
 * AuditorCodeAuditCreateService.java
 *
 * Copyright (C) 2012-2024 Rafael Corchuelo.
 *
 * In keeping with the traditional purpose of furthering education and research, it is
 * the policy of the copyright owner to permit non-commercial use and redistribution of
 * this software. It has been tested carefully, but it is not guaranteed for any particular
 * purposes. The copyright owner does not offer any warranties or representations, nor do
 * they accept any liabilities with respect to them.
 */

package acme.features.auditor.auditRecord;

import java.time.temporal.ChronoUnit;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.models.Dataset;
import acme.client.helpers.MomentHelper;
import acme.client.services.AbstractService;
import acme.client.views.SelectChoices;
import acme.entities.auditRecords.AuditRecord;
import acme.entities.auditRecords.Mark;
import acme.entities.codeAudits.CodeAudit;
import acme.roles.Auditor;

@Service
public class AuditorAuditRecordCreateService extends AbstractService<Auditor, AuditRecord> {

	// Internal state ---------------------------------------------------------
	@Autowired
	private AuditorAuditRecordRepository repository;

	// AbstractService interface ----------------------------------------------


	@Override
	public void authorise() {

		boolean status;
		int codeAuditId;
		CodeAudit codeAudit;

		codeAuditId = super.getRequest().getData("masterId", int.class);
		codeAudit = this.repository.findOneCodeAuditById(codeAuditId);
		status = codeAudit != null && (!codeAudit.isDraftMode() || super.getRequest().getPrincipal().hasRole(codeAudit.getAuditor()));

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		AuditRecord object;

		int masterId;
		CodeAudit codeAudit;
		masterId = super.getRequest().getData("masterId", int.class);
		codeAudit = this.repository.findOneCodeAuditById(masterId);

		object = new AuditRecord();
		object.setCode("");
		object.setCodeAudit(codeAudit);
		super.getBuffer().addData(object);
	}

	@Override
	public void bind(final AuditRecord object) {
		assert object != null;

		super.bind(object, "code", "periodStart", "periodEnd", "mark", "link");

	}

	@Override
	public void validate(final AuditRecord object) {
		assert object != null;
		if (!super.getBuffer().getErrors().hasErrors("code")) {
			AuditRecord existing;

			existing = this.repository.findOneAuditRecordByCode(object.getCode());
			super.state(existing == null, "code", "auditor.audit-record.form.error.duplicated");
		}
		if (!super.getBuffer().getErrors().hasErrors("periodStart")) {
			Date minimumEnd;

			minimumEnd = java.sql.Date.valueOf("1999-12-31");
			minimumEnd = MomentHelper.deltaFromMoment(minimumEnd, 23, ChronoUnit.HOURS);
			minimumEnd = MomentHelper.deltaFromMoment(minimumEnd, 59, ChronoUnit.MINUTES);
			super.state(MomentHelper.isAfter(object.getPeriodStart(), minimumEnd), "periodStart", "auditor.audit-record.form.error.bad-date");
		}
		if (!super.getBuffer().getErrors().hasErrors("periodStart")) {
			Date minimumEnd;

			minimumEnd = java.sql.Date.valueOf("2022-07-29");
			minimumEnd = MomentHelper.deltaFromMoment(minimumEnd, 23, ChronoUnit.HOURS);
			super.state(MomentHelper.isBefore(object.getPeriodStart(), minimumEnd), "periodStart", "auditor.audit-record.form.error.bad-date");
		}

		if (!super.getBuffer().getErrors().hasErrors("periodEnd")) {
			Date minimumEnd;
			minimumEnd = MomentHelper.deltaFromMoment(object.getPeriodStart(), 59, ChronoUnit.MINUTES);
			super.state(MomentHelper.isAfter(object.getPeriodEnd(), minimumEnd), "periodEnd", "auditor.audit-record.form.error.too-close");
		}
		if (!super.getBuffer().getErrors().hasErrors("periodEnd")) {
			Date minimumEnd;

			minimumEnd = java.sql.Date.valueOf("2022-07-30");
			super.state(MomentHelper.isBefore(object.getPeriodEnd(), minimumEnd), "periodEnd", "auditor.audit-record.form.error.bad-date");
		}
	}

	@Override
	public void perform(final AuditRecord object) {
		assert object != null;
		this.repository.save(object);
	}

	@Override
	public void unbind(final AuditRecord object) {
		assert object != null;
		SelectChoices choices;
		Dataset dataset;

		choices = SelectChoices.from(Mark.class, object.getMark());

		dataset = super.unbind(object, "code", "periodStart", "periodEnd", "mark", "link");
		dataset.put("marks", choices);
		dataset.put("masterId", super.getRequest().getData("masterId", int.class));

		super.getResponse().addData(dataset);
	}

}
