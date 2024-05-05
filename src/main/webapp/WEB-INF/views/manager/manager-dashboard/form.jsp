<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<h2>
	<acme:message code="manager.dashboard.form.title.general-indicators"/>
</h2>

 <table class="table table-sm">

	<tr>
		<th scope="row">
			<acme:message code="manager.dashboard.form.label.totalNumberOfMustUserStories"/>
		</th>
		<td>
			<acme:print value="${totalNumberOfMustUserStories}"/>
		</td>
	</tr>	


	<tr>
		<th scope="row">
			<acme:message code="manager.dashboard.form.label.totalNumberOfShouldUserStories"/>
		</th>
		<td>
			<acme:print value="${totalNumberOfShouldUserStories}"/>
		</td>
	</tr>	

	<tr>
		<th scope="row">
			<acme:message code="manager.dashboard.form.label.totalNumberOfCouldUserStories"/>
		</th>
		<td>
			<acme:print value="${totalNumberOfCouldUserStories}"/>
		</td>
	</tr>	

	<tr>
		<th scope="row">
			<acme:message code="manager.dashboard.form.label.totalNumberOfWontUserStories"/>
		</th>
		<td>
			<acme:print value="${totalNumberOfWontUserStories}"/>
		</td>
	</tr>	

	<tr>
		<th scope="row">
			<acme:message code="manager.dashboard.form.label.averageEstimatedCostUserStories"/>
		</th>
		<td>
			<acme:print value="${averageEstimatedCostUserStories}"/>
		</td>
	</tr>	

	<tr>
		<th scope="row">
			<acme:message code="manager.dashboard.form.label.deviationEstimatedCostUserStories"/>
		</th>
		<td>
			<acme:print value="${deviationEstimatedCostUserStories}"/>
		</td>
	</tr>	
	<tr>
		<th scope="row">
			<acme:message code="manager.dashboard.form.label.minimumEstimatedCostUserStories"/>
		</th>
		<td>
			<acme:print value="${minimumEstimatedCostUserStories}"/>
		</td>
	</tr>	

	<tr>
		<th scope="row">
			<acme:message code="manager.dashboard.form.label.maximumEstimatedCostUserStories"/>
		</th>
		<td>
			<acme:print value="${maximumEstimatedCostUserStories}"/>
		</td>
	</tr>	

	<tr>
		<th scope="row">
			<acme:message code="manager.dashboard.form.label.averageProjectsCost"/>
		</th>
		<td>
			<acme:print value="${averageProjectsCost}"/>
		</td>
	</tr>	

	<tr>
		<th scope="row">
			<acme:message code="manager.dashboard.form.label.deviationProjectsCost"/>
		</th>
		<td>
			<acme:print value="${deviationProjectsCost}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="manager.dashboard.form.label.minimumProjectsCost"/>
		</th>
		<td>
			<acme:print value="${minimumProjectsCost}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="manager.dashboard.form.label.minimumProjectsCost"/>
		</th>
		<td>
			<acme:print value="${maximumProjectsCost}"/>
		</td>
	</tr>
</table>

<acme:return/>