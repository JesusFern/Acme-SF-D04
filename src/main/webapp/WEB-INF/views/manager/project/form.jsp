<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:form> 
	<acme:input-textbox code="manager.project.form.label.code" path="code"/>
	<acme:input-textbox code="manager.project.form.label.title" path="title"/>
	<acme:input-textbox code="manager.project.form.label.abstractString" path="abstractString"/>
	<acme:input-textbox code="manager.project.form.label.indication" path="indication" placeholder="manager.project.form.placeholder.indication"/>	
	<acme:input-double code="manager.project.form.label.cost" path="cost" placeholder="manager.project.form.placeholder.cost"/>
	<acme:input-url code="manager.project.form.label.link" path="link"/>
	
	<jstl:choose>
	
	<jstl:when test="${acme:anyOf(_command, 'show|update|delete|publish') && draftMode==true }">
			<acme:submit code="manager.project.form.button.delete" action="/manager/project/delete"/>
			<acme:submit code="manager.project.form.button.update" action="/manager/project/update"/>
			<acme:submit code="manager.project.form.button.publish" action="/manager/project/publish"/>
			<acme:button code="manager.project.form.button.userStories" action="/manager/user-story/list-project?masterId=${id}"/>		
			<acme:button code="manager.project.form.button.projectUserStories" action="/manager/project-user-story/list-project?masterId=${id}"/>
		</jstl:when>
	<jstl:when test="${acme:anyOf(_command, 'show|update|delete|publish') && draftMode==false}">
			<acme:button code="manager.project.form.button.userStories" action="/manager/user-story/list-project?masterId=${id}"/>
		</jstl:when>
	<jstl:when test="${_command == 'create'}">
			<acme:submit code="manager.project.form.button.create" action="/manager/project/create"/>
		</jstl:when>	
	
	</jstl:choose>
		
</acme:form>