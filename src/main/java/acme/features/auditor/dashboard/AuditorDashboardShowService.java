/*
 * AdministratorDashboardShowService.java
 *
 * Copyright (C) 2012-2024 Rafael Corchuelo.
 *
 * In keeping with the traditional purpose of furthering education and research, it is
 * the policy of the copyright owner to permit non-commercial use and redistribution of
 * this software. It has been tested carefully, but it is not guaranteed for any particular
 * purposes. The copyright owner does not offer any warranties or representations, nor do
 * they accept any liabilities with respect to them.
 */

package acme.features.auditor.dashboard;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.forms.AuditorDashboard;
import acme.roles.Auditor;

@Service
public class AuditorDashboardShowService extends AbstractService<Auditor, AuditorDashboard> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private AuditorDashboardRepository repository;

	// AbstractService interface ----------------------------------------------


	@Override
	public void authorise() {
		super.getResponse().setAuthorised(true);
	}

	@Override
	public void load() {
		AuditorDashboard dashboard;
		Integer totalNumberOfStaticCodeAudit;
		Integer totalNumberOfDynamicCodeAudit;

		Double averageNumberOfAuditRecords;
		Double deviationNumberOfAuditRecords;
		Double minimumNumberOfAuditRecords;
		Double maximumNumberOfAuditRecords;

		Double averageTimeOfPeriod;
		Double deviationTimeOfPeriod;
		Double minimumTimeOfPeriod;
		Double maximumTimeOfPeriod;

		int id;

		id = super.getRequest().getPrincipal().getActiveRoleId();

		totalNumberOfStaticCodeAudit = this.repository.totalNumberOfStaticCodeAudit(id);
		totalNumberOfDynamicCodeAudit = this.repository.totalNumberOfDynamicCodeAudit(id);
		averageNumberOfAuditRecords = this.repository.averageNumberOfAuditRecords(id);
		deviationNumberOfAuditRecords = this.repository.deviationNumberOfAuditRecords(id);
		minimumNumberOfAuditRecords = this.repository.minimumNumberOfAuditRecords(id);
		maximumNumberOfAuditRecords = this.repository.maximumNumberOfAuditRecords(id);
		averageTimeOfPeriod = this.repository.averageTimeOfPeriod(id);
		deviationTimeOfPeriod = this.repository.deviationTimeOfPeriod(id);
		minimumTimeOfPeriod = this.repository.minimumTimeOfPeriod(id);
		maximumTimeOfPeriod = this.repository.maximumTimeOfPeriod(id);

		dashboard = new AuditorDashboard();
		dashboard.setTotalNumberOfStaticCodeAudit(totalNumberOfStaticCodeAudit);
		dashboard.setTotalNumberOfDynamicCodeAudit(totalNumberOfDynamicCodeAudit);
		dashboard.setAverageNumberOfAuditRecords(averageNumberOfAuditRecords);
		dashboard.setDeviationNumberOfAuditRecords(deviationNumberOfAuditRecords);
		dashboard.setMinimumNumberOfAuditRecords(minimumNumberOfAuditRecords);
		dashboard.setMaximumNumberOfAuditRecords(maximumNumberOfAuditRecords);
		dashboard.setAverageTimeOfPeriod(averageTimeOfPeriod / 10000);
		dashboard.setDeviationTimeOfPeriod(deviationTimeOfPeriod / 10000);
		dashboard.setMinimumTimeOfPeriod(minimumTimeOfPeriod / 10000);
		dashboard.setMaximumTimeOfPeriod(maximumTimeOfPeriod / 10000);

		super.getBuffer().addData(dashboard);
	}

	@Override
	public void unbind(final AuditorDashboard object) {
		Dataset dataset;

		dataset = super.unbind(object, //
			"totalNumberOfStaticCodeAudit", "totalNumberOfDynamicCodeAudit", // 
			"averageNumberOfAuditRecords", "deviationNumberOfAuditRecords", //
			"minimumNumberOfAuditRecords", "maximumNumberOfAuditRecords", "averageTimeOfPeriod", "deviationTimeOfPeriod" //
			, "minimumTimeOfPeriod", "maximumTimeOfPeriod");

		super.getResponse().addData(dataset);
	}

}
