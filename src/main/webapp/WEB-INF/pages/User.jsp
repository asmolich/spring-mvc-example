<%@ page language="java" contentType="text/html; charset=utf8"
	pageEncoding="utf8"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf8">
<title><spring:message code="label.title" /></title>
</head>
<body>

	<a href="<c:url value="/logout" />"> <spring:message
			code="label.logout" />
	</a>

	<h2>
		<spring:message code="label.title" />
	</h2>

	<form:form method="post" action="add" commandName="User">

		<table>
			<tr>
				<td><form:label path="id">
						<spring:message code="label.lastname" />
					</form:label></td>
				<td><form:input path="id" /></td>
			</tr>
			<tr>
				<td><form:label path="firstName">
						<spring:message code="label.firstname" />
					</form:label></td>
				<td><form:input path="firstName" /></td>
			</tr>
			<tr>
				<td><form:label path="lastName">
						<spring:message code="label.lastname" />
					</form:label></td>
				<td><form:input path="lastName" /></td>
			</tr>
		</table>
	</form:form>

	<h3>
		<spring:message code="label.users" />
	</h3>
	<c:if test="${!empty userList}">
		<table class="data">
			<tr>
				<th><spring:message code="label.user" /></th>
				<th>&nbsp;</th>
			</tr>
			<c:forEach items="${userList}" var="user">
				<tr>
					<td>${user.id} : ${user.lastname}, ${user.firstname}</td>
					<td><a href="delete/${user.id}"><spring:message
								code="label.delete" /></a></td>
				</tr>
			</c:forEach>
		</table>
	</c:if>

</body>
</html>