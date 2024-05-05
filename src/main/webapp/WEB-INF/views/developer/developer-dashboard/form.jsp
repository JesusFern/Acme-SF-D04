<%--
- form.jsp
-
- Copyright (C) 2012-2023 Rafael Corchuelo.
-
- In keeping with the traditional purpose of furthering education and research, it is
- the policy of the copyright owner to permit non-commercial use and redistribution of
- this software. It has been tested carefully, but it is not guaranteed for any particular
- purposes.  The copyright owner does not offer any warranties or representations, nor do
- they accept any liabilities with respect to them.
--%>


<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<h2>
	<acme:message code="developer.developer-dashboard.form.title.general-indicators"/>
</h2>

<table class="table table-sm">

	<tr>
		<th scope="row">
			<acme:message code="developer.developer-dashboard.form.label.total-training-modules-with-updated-moment"/>
		</th>
		<td>
			<acme:print value="${totalNumberOfTrainingModulesWithUpdatedMoment}"/>
		</td>
	</tr>	


	<tr>
		<th scope="row">
			<acme:message code="developer.developer-dashboard.form.label.total-training-sessions-with-link"/>
		</th>
		<td>
			<acme:print value="${totalNumberOfTrainingSessionsWithLink}"/>
		</td>
	</tr>	

	<tr>
		<th scope="row">
			<acme:message code="developer.developer-dashboard.form.label.average-time-training-modules"/>
		</th>
		<td>
			<acme:print value="${averageTimeTrainingModules}"/>
		</td>
	</tr>	

	<tr>
		<th scope="row">
			<acme:message code="developer.developer-dashboard.form.label.deviation-time-training-modules"/>
		</th>
		<td>
			<acme:print value="${deviationTimeTrainingModules}"/>
		</td>
	</tr>	

	<tr>
		<th scope="row">
			<acme:message code="developer.developer-dashboard.form.label.minimum-time-training-modules"/>
		</th>
		<td>
			<acme:print value="${minimumTimeTrainingModules}"/>
		</td>
	</tr>	

	<tr>
		<th scope="row">
			<acme:message code="developer.developer-dashboard.form.label.maximum-time-training-modules"/>
		</th>
		<td>
			<acme:print value="${maximumTimeTrainingModules}"/>
		</td>
	</tr>	
</table>


<acme:return/>
