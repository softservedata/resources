<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>



<spring:url value="/resource/js/changeStatus.js" var="statusJs" />

<script src="${statusJs}"></script>

<div style="text-align: center;">
	<h4>
		<spring:message code="label.registrated.pagename"/>
	</h4>
</div>

<table id="datatable" class="table display">
	<thead>
		<tr>
			<th><spring:message code="label.user.secondname"/></th>
			<th><spring:message code="label.user.firstname"/></th>
			<th><spring:message code="label.user.middlename"/></th>
			<th><spring:message code="label.user.email"/></th>
			<th><spring:message code="label.user.address"/></th>
			<th><spring:message code="label.user.passport"/></th>
			<th><spring:message code="label.restype.actions"/></th>
			<!-- <th>Змінити Роль</th>
			<th>Змінти Статус</th> -->
		</tr>
	</thead>
	<c:if test="${not empty userDtoList}">
		<c:forEach items="${userDtoList}" var="user">
			<tr>
				<td>${user.lastName}</td>
				<td>${user.firstName}</td>
				<td>${user.middleName}</td>
				<td>${user.email}</td>
				<td>${user.address.city},${user.address.street}, ${user.address.building}, ${user.address.flat}</td>
				<td>${user.passport.seria}, ${user.passport.number}, ${user.passport.published_by_data}</td>
				<td><a href=# class="btn btn-primary" role="button"><spring:message code="label.restype.edit"/></a></td>
				<%-- <td><a
					href="${base}addressWindow/?login=${user.login}"
					rel="0">Адреса</a></td>
				<td><a
					href="${base}passportWindow/?login=${user.login}"
					rel="0">Паспорт</a></td>
				<td><select id="roleId" name="roleName">
						<c:forEach items="${roleList}" var="role">
							<c:choose>
								<c:when test="${user.role.type == role.type}">
									<option selected value="${role.type}">${role.type}</option>
								</c:when>
								<c:otherwise>
									<option value="${role.type}">${role.type}</option>
								</c:otherwise>
							</c:choose>
						</c:forEach>
				</select></td>
				<td><select id="userStatusId" name="userStatusName">
						<c:forEach items="${userStatusList}" var="userStatus">
							<c:choose>
								<c:when test="${user.status == userStatus}">
									<option selected value="${userStatus}">${userStatus}</option>
								</c:when>
								<c:otherwise>
									<option value="${userStatus}">${userStatus}</option>
								</c:otherwise>
							</c:choose>
						</c:forEach>
				</select></td> --%>
			</tr>
		</c:forEach>
	</c:if>
</table>
