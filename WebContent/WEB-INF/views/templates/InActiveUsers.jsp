<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<div style="text-align: center;">
	<h4>
		<spring:message code="label.inactive.pagename" />
	</h4>
</div>
<form:form id="editWinodow" modelAttribute="userDTO" method="post"
	action="get-all-inactive-users">
	<table id="datatable" class="table display">


		<thead>
			<tr>
				<th><spring:message code="label.user.secondname" /></th>
				<th><spring:message code="label.user.firstname" /></th>
				<th><spring:message code="label.user.middlename" /></th>
				<th><spring:message code="label.user.email" /></th>
				<th><spring:message code="label.user.address" /></th>
				<th><spring:message code="label.user.passport" /></th>
				<th>Змінити Роль</th>
				<th>Змінти Статус</th>
			</tr>
		</thead>
		<c:if test="${not empty unregistatedUsers}">
			<c:forEach items="${unregistatedUsers}" var="userDTO">
				<tr>
					<td>${userDTO.lastName}</td>
					<td>${userDTO.firstName}</td>
					<td>${userDTO.middleName}</td>
					<td>${userDTO.email}</td>
					<td>${userDTO.address.city},${user.address.street},
						${userDTO.address.building}, ${user.address.flat}</td>
					<td>${userDTO.passport.seria},${user.passport.number},
						${userDTO.passport.published_by_data}</td>
					<td><select id="roleId" name="role">
							<c:forEach items="${roleList}" var="role">
								<c:choose>
									<c:when test="${userDto.role == role.type}">
										<option selected value="${role.type}">${role.type}</option>
									</c:when>
									<c:otherwise>
										<option value="${role.type}">${role.type}</option>
									</c:otherwise>
								</c:choose>
							</c:forEach>
					</select></td>
					<td><select id="userStatusId" name="status">
							<c:forEach items="${userStatusList}" var="userStatus">
								<c:choose>
									<c:when test="${userDto.status == userStatus}">
										<option selected value="${userStatus}">${userStatus}</option>
									</c:when>
									<c:otherwise>
										<option value="${userStatus}">${userStatus}</option>
									</c:otherwise>
								</c:choose>
							</c:forEach>
					</select></td>
				</tr>
			</c:forEach>
		</c:if>

	</table>
	<input type="submit" value="Submit">
</form:form>

