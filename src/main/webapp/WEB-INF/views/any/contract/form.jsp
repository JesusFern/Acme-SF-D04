<%--
- form.jsp
-
- Copyright (C) 2012-2024 Rafael Corchuelo.
-
- In keeping with the traditional purpose of furthering education and research, it is
- the policy of the copyright owner to permit non-commercial use and redistribution of
- this software. It has been tested carefully, but it is not guaranteed for authenticated particular
- purposes.  The copyright owner does not offer authenticated warranties or representations, nor do
- they accept authenticated liabilities with respect to them.
--%>

<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:form>
	<acme:input-textbox code="authenticated.contract.form.label.code" path="code" />
	<acme:input-textarea code="authenticated.contract.form.label.providerName" path="providerName"/>
	<acme:input-textarea code="authenticated.contract.form.label.customerName" path="customerName"/>
	<acme:input-textarea code="authenticated.contract.form.label.goals" path="goals"/>
	<acme:input-select code="authenticated.contract.form.label.project" path="project" choices="${projects}" />
	<acme:input-money code="authenticated.contract.form.label.budget" path="budget"/>
	<jstl:choose>
		<jstl:when test="${acme:anyOf(_command, 'show') }">
	<acme:input-moment code="any.contract.form.label.instantiationMoment" path="instantiationMoment" readonly="true"/>
			</jstl:when>
	</jstl:choose>
	<jstl:choose>
	<jstl:when test="${_command == 'show' && draftMode == false}">
			<acme:button code="any.progress-log.list.title" action="/any/progress-log/list-all?masterId=${id}"/>			
		</jstl:when>
	</jstl:choose>
		
    
</acme:form>