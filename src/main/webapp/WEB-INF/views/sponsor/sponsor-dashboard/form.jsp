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

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<h1>
	<acme:message code="sponsor.dashboard.form.title.general-indicators"/>
</h1>


<h2>
	<acme:message code="sponsor.dashboard.form.title.general-indicators"/>
</h2>

 <table class="table table-sm">

	<tr>
		<th scope="row">
			<acme:message code="sponsor.dashboard.form.label.total-invoices-with-tax-less-or-equals-21"/>
		</th>
		<td>
			<acme:print value="${totalNumberOfInvoicesWithTaxLessOrEquals21}"/>
		</td>
	</tr>	


	<tr>
		<th scope="row">
			<acme:message code="sponsor.dashboard.form.label.total-sponsorships-link"/>
		</th>
		<td>
			<acme:print value="${totalNumberOfSponsorshipsLink}"/>
		</td>
	</tr>	

	<tr>
		<th scope="row">
			<acme:message code="sponsor.dashboard.form.label.average-sponsorships-amount"/>
		</th>
		<td>
			<c:forEach items="${averageSponsorshipsAmount}" var="entry">
                ${entry.value} ${entry.key} <br/>
            </c:forEach>
		</td>
	</tr>	

	<tr>
		<th scope="row">
			<acme:message code="sponsor.dashboard.form.label.deviation-sponsorships-amount"/>
		</th>
		<td>
			<c:forEach items="${deviationSponsorshipsAmount}" var="entry">
                ${entry.value} ${entry.key} <br/>
            </c:forEach>
		</td>
	</tr>	

	<tr>
		<th scope="row">
			<acme:message code="sponsor.dashboard.form.label.minimum-sponsorships-amount"/>
		</th>
		<td>
			<c:forEach items="${minimumSponsorshipsAmount}" var="entry">
                ${entry.value} ${entry.key} <br/>
            </c:forEach>
		</td>
	</tr>	

	<tr>
		<th scope="row">
			<acme:message code="sponsor.dashboard.form.label.maximum-sponsorships-amount"/>
		</th>
		<td>
			<c:forEach items="${maximumSponsorshipsAmount}" var="entry">
                ${entry.value} ${entry.key} <br/>
            </c:forEach>
		</td>
	</tr>	
	<tr>
		<th scope="row">
			<acme:message code="sponsor.dashboard.form.label.average-invoices-quantity"/>
		</th>
		<td>
			<c:forEach items="${averageInvoicesQuantity}" var="entry">
                ${entry.value} ${entry.key} <br/>
            </c:forEach>
		</td>
	</tr>	

	<tr>
		<th scope="row">
			<acme:message code="sponsor.dashboard.form.label.deviation-invoices-quantity"/>
		</th>
		<td>
			<c:forEach items="${deviationInvoicesQuantity}" var="entry">
                ${entry.value} ${entry.key} <br/>
            </c:forEach>
		</td>
	</tr>	

	<tr>
		<th scope="row">
			<acme:message code="sponsor.dashboard.form.label.minimum-invoices-quantity"/>
		</th>
		<td>
			<c:forEach items="${minimumInvoicesQuantity}" var="entry">
                ${entry.value} ${entry.key} <br/>
            </c:forEach>
		</td>
	</tr>	

	<tr>
		<th scope="row">
			<acme:message code="sponsor.dashboard.form.label.maximum-invoices-quantity"/>
		</th>
		<td>
			<c:forEach items="${maximumInvoicesQuantity}" var="entry">
                ${entry.value} ${entry.key} <br/>
            </c:forEach>
		</td>
	</tr>	
</table>

<acme:return/>
