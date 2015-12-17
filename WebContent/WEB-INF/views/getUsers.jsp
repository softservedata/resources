<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="en">
<head>
<title>Реєстратор</title>

<spring:url
	value="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"
	var="jqueryJs" />
<link rel="stylesheet" type="text/css" href="resource/css/normalize.css">
<link rel="stylesheet" type="text/css" href="resource/css/1.css">

</head>

<body>
	<div class="container">
		<table id="users" border="1">
			<tr>
				<th>First name</th>
				<th>Last name</th>
				<th>Email</th>
				<th>Middle name</th>
				<th>Role</th>
				<th>Status</th>
				<th>Address</th>
				<th>Change Status</th>
			</tr>

			<c:if test="${not empty userList}">
				<c:forEach items="${userList}" var="user">
					<tr>
						<td>${user.firstName}</td>
						<td>${user.lastName}</td>
						<td>${user.email}</td>
						<td>${user.middleName}</td>
						<td>${user.role}</td>
						<td>${user.status}</td>
						<td><c:forEach items="${user.address}" var="add">
								<div>${add.city}</div>
							</c:forEach></td>
						<td><select>
								<option value="ADMIN">ADMIN</option>
								<option value="USER">REGISTRATOR</option>
								<option value="REGISTRATOR">USER</option>
						</select></td>
					</tr>
				</c:forEach>
			</c:if>
		</table>
	</div>
</body>
</html>