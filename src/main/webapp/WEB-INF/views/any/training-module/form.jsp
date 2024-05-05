<%--
- form.jsp
-
- Copyright (C) 2012-2024 Rafael Corchuelo.
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

<acme:form>
	<acme:input-textbox code="any.training-module.form.label.code" path="code"/>
	<acme:input-textarea code="any.training-module.form.label.details" path="details"/>
	<acme:input-select code="any.training-module.form.label.difficultyLevel" path="difficultyLevel" choices="${difficultyLevels}" />
	<acme:input-url code="any.training-module.form.label.link" path="link"/>
	<acme:input-integer code="any.training-module.form.label.time" path="time"/>
	<acme:input-select code="any.training-module.form.label.project" path="project" choices="${projects}" />
	<acme:input-moment code="any.training-module.form.label.creationMoment" path="creationMoment" readonly="true"/>
	<acme:input-moment code="any.training-module.form.label.updateMoment" path="updateMoment" readonly="true"/>
</acme:form>