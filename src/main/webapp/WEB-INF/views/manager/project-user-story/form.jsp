<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:form> 

<acme:input-select code="manager.project-user-story.form.label.userStory" path="userStory"  choices="${userStories}" readonly="${_command != 'create'}"/>

<jstl:choose>
		<jstl:when test="${acme:anyOf(_command, 'show|delete') && draftMode == true}">
			<acme:submit code="manager.user-story.form.button.delete" action="/manager/project-user-story/delete"/>
		</jstl:when>

		<jstl:when test="${_command == 'create'}">
			<acme:submit code="manager.project-user-story.form.button.create" action="/manager/project-user-story/create?masterId=${masterId}"/>
		</jstl:when>		
	</jstl:choose>

</acme:form>