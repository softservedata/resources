<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<c:set var="req" value="${pageContext.request}" />
<c:set var="url">${req.requestURL}</c:set>
<c:set var="base"
	value="${fn:substring(url, 0, fn:length(url) - fn:length(req.requestURI))}${req.contextPath}/" />



<div style="text-align: center;">
	<h4>
		<spring:message code="label.registrated.pagename" />
	</h4>
</div>

<table id="datatable" class="table display">
	<thead>
		<tr>
			<th><spring:message code="label.user.secondname" /></th>
			<th><spring:message code="label.user.firstname" /></th>
			<th><spring:message code="label.user.middlename" /></th>
			<th><spring:message code="label.user.email" /></th>
			<th><spring:message code="label.user.address" /></th>
			<th><spring:message code="label.user.passport" /></th>
			<th><spring:message code="label.restype.actions" /></th>
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
				<td>${user.address.city},${user.address.street},
					${user.address.building}, ${user.address.flat}</td>
				<td>${user.passport.seria},${user.passport.number},
					${user.passport.published_by_data}</td>
				<td><a
					href="${base}administrator/users/edit-registrated-user/?login=${user.login}"
					class="btn btn-primary" role="button"><spring:message
							code="label.restype.edit" /></a></td>
			</tr>
		</c:forEach>
	</c:if>
</table>
