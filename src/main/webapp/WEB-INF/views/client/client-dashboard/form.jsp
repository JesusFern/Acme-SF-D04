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
	<acme:message code="client.client-dashboard.form.title.general-indicators"/>
</h2>

<table class="table table-sm">

	<tr>
		<th scope="row">
			<acme:message code="client.client-dashboard.form.label.total-progress-log-with-completeness-below-25"/>
		</th>
		<td>
			<acme:print value="${totalNumberOfProgressLogsCompletenessBelow25}"/>
		</td>
	</tr>	


	<tr>
		<th scope="row">
			<acme:message code="client.client-dashboard.form.label.total-progress-log-with-completeness-between-25-50"/>
		</th>
		<td>
			<acme:print value="${totalNumberOfProgressLogsCompletenessBetween25to50}"/>
		</td>
	</tr>	
	
	<tr>
		<th scope="row">
			<acme:message code="client.client-dashboard.form.label.total-progress-log-with-completeness-between-50-75"/>
		</th>
		<td>
			<acme:print value="${totalNumberOfProgressLogsCompletenessBetween50to75}"/>
		</td>
	</tr>	
	
	<tr>
		<th scope="row">
			<acme:message code="client.client-dashboard.form.label.total-progress-log-with-completeness-above-75"/>
		</th>
		<td>
			<acme:print value="${totalNumberOfProgressLogsCompletenessAbove75}"/>
		</td>
	</tr>	

	<tr>
		<th scope="row">
			<acme:message code="client.client-dashboard.form.label.average-budget-contracts"/>
		</th>
		<td>
			<acme:print value="${averageContractsBudget}"/>
		</td>
	</tr>	

	<tr>
		<th scope="row">
			<acme:message code="client.client-dashboard.form.label.deviation-budget-contracts"/>
		</th>
		<td>
			<acme:print value="${deviationContractsBudget}"/>
		</td>
	</tr>	

	<tr>
		<th scope="row">
			<acme:message code="client.client-dashboard.form.label.minimum-budget-contracts"/>
		</th>
		<td>
			<acme:print value="${minimumContractsBudget}"/>
		</td>
	</tr>	

	<tr>
		<th scope="row">
			<acme:message code="client.client-dashboard.form.label.maximum-budget-contracts"/>
		</th>
		<td>
			<acme:print value="${maximumContractsBudget}"/>
		</td>
	</tr>	
</table>


<acme:return/>
