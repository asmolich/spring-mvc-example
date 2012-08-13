<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<div id="main_container" class="colmask leftmenu">
	<div class="colleft">
		<div class="col1">
			<form action="<c:url value='/venue/297e2b093852bbbf013852bdf0e90000/comment/add'/>"
				enctype="application/json" method="post">
				<table>
					<tr>
						<td>text</td>
						<td><input type="text" name="text" /></td>
					</tr>
					<tr>
						<td>userId</td>
						<td><input type="text" name="userId" /></td>
					</tr>
				</table>
				<input type="submit" value="submit" />
			</form>
		</div>
		<div class="col2">
			<form action="<c:url value='/venue/add'/>" enctype="application/json"
				method="post">
				<table>
					<tr>
						<td>name</td>
						<td><input type="text" name="name" /></td>
					</tr>
					<tr>
						<td>address</td>
						<td><input type="text" name="address" /></td>
					</tr>
					<tr>
						<td>city</td>
						<td><input type="text" name="city" /></td>
					</tr>
					<tr>
						<td>state</td>
						<td><input type="text" name="state" /></td>
					</tr>
					<tr>
						<td>country</td>
						<td><input type="text" name="country" /></td>
					</tr>
					<tr>
						<td>postalCode</td>
						<td><input type="text" name="postalCode" /></td>
					</tr>
					<tr>
						<td>photoUrl</td>
						<td><input type="text" name="photoUrl" /></td>
					</tr>
					<tr>
						<td>latitude</td>
						<td><input type="text" name="latitude" /></td>
					</tr>
					<tr>
						<td>longitude</td>
						<td><input type="text" name="longitude" /></td>
					</tr>
					<tr>
						<td>ssid</td>
						<td><input type="text" name="ssid" /></td>
					</tr>
					<tr>
						<td>password</td>
						<td><input type="text" name="password" /></td>
					</tr>
					<tr>
						<td>categoryId</td>
						<td><input type="text" name="categoryId" /></td>
					</tr>
				</table>
				<input type="submit" value="submit" />
			</form>
			<c:if test="${!empty id}">${id}</c:if>
		</div>
	</div>
</div>
