<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<div id="main_container" class="colmask leftmenu">
	<div class="colleft">
		<div class="col1">
			<div id="appstore">
				<img alt="appstore"
					src='<c:url value="/resources/images/appstore.png" />' />
			</div>
			<div id="social">
				<a target="_blank" href='<spring:message code="link.facebook" />'>
					<img alt="facebook"
					src='<c:url value="/resources/images/fb.png" />' />
				</a> <a target="_blank" href='<spring:message code="link.twitter" />'>
					<img alt="twitter"
					src='<c:url value="/resources/images/twi.png" />' />
				</a> <a target="_blank" href='<spring:message code="link.gplus" />'>
					<img alt="google plus"
					src='<c:url value="/resources/images/gplus.png" />' />
				</a>
			</div>
		</div>
		<div class="col2">
			<div id="logo">
				<img title="LocaWifi" alt="LocaWifi"
					src='<c:url value="/resources/images/logo.png" />' />
			</div>
			<div id="text_main">
				<h2>
					<spring:message code="text.main_title" />
				</h2>
				<p>
					<spring:message code="text.main" />
				</p>
				<a id="blog" href='http://locamotiontravel.blogspot.com/'><spring:message
						code="label.blog" /></a>
			</div>
		</div>
	</div>
</div>
<div id="screenshot">
	<img title="LocaWifi" alt="LocaWifi"
		src='<c:url value="/resources/images/iphone.png" />' />
</div>
<div id="bottom_line" class="colmask leftmenu">
	<div class="colleft">
		<div class="col1">
			<div id="langs">
				<c:if test="${!empty langList}">
					<ul>
						<c:forEach items="${langList}" var="lang" varStatus="status">
							<c:if test="${status.index == 3}">
					</ul>
					<ul>
				</c:if>
				<li><a href="<c:url value="/${lang}/"/>"> <img
						alt="${lang}" src="<c:url value="/resources/images/${lang}.png"/>" />
				</a></li>
				</c:forEach>
				</ul>
				</c:if>
			</div>
		</div>
		<div class="col2">
			<div id="text_soon">
				<h2>
					<spring:message code="text.soon_title" />
				</h2>
				<p>
					<spring:message code="text.soon" />
				</p>
			</div>
		</div>
	</div>
</div>
<div id="feedback_button" class="box_rotate">
	<spring:message code="text.feedback" />
</div>
<div class="feedback" style="display: none;">
	<form:form method="post" commandName="feedback">
		<table>
			<tr>
				<td><form:label path="fromName">
						<spring:message code="label.name" />
					</form:label></td>
				<td><form:input path="fromName" /></td>
			</tr>
			<tr>
				<td><form:label path="fromEmail">
						<spring:message code="label.email" />
					</form:label></td>
				<td><form:input path="fromEmail" /></td>
			</tr>
			<tr>
				<td><form:label path="body">
						<spring:message code="label.emailbody" />
					</form:label></td>
				<td><form:textarea path="body" /></td>
			</tr>
			<tr>
				<td colspan="2"><input type="submit"
					value="<spring:message code="label.submit"/>" /></td>
			</tr>
		</table>
	</form:form>
</div>
<c:if test="${!empty feedbacks}">
	<div style="position: absolute; top: 0; left: 0;">
		<table>
			<tr><td>
				<spring:message code="label.name" /></td>
				<td><spring:message code="label.email" /></td>
				<td><spring:message code="label.emailbody" /></td>
			</tr>
			<c:forEach items="feedbacks" var="fb">
				<tr>
					<td><c:out value="${fb.fromName}" /></td>
					<td><c:out value="${fb.fromEmail}" /></td>
					<td><c:out value="${fb.body}" /></td>
				</tr>
			</c:forEach>
		</table>
	</div>
</c:if>
