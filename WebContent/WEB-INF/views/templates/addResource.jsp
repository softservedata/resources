<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<div>

	<h1>
		<spring:message code="label.resource.add" />
	</h1>
	<form:form method="POST" action="addresource"
		modelAttribute="newresource">
		<table>
			<tr>
				<td><spring:message code="label.resource.description" />:</td>
				<td><form:input path="description"/></td>
			</tr>
			<tr>
				<td><spring:message code="label.resource.type" />:</td>
				<td><form:input path="resourceType.typeName"
						value="${resType.typeName}" readonly="true" /></td>
			</tr>
			<!-- Showing all discrete parameters for resource type -->
			<c:forEach items="${resType.discreteParameters}" var="parameter"
				varStatus="param_i">
				<tr>
					<td>${parameter.description},${parameter.unitName}</td>
					<td><form:input
							path="resourceDiscrete[${param_i.index}].values" /></td>
					<td><form:hidden
							path="resourceDiscrete[${param_i.index}].discreteParameterDescription"
							value="${parameter.description}" /></td>
				</tr>
			</c:forEach>
			<c:forEach items="${resType.linearParameters}" var="parameter"
				varStatus="param_i">
				<tr>
					<td>${parameter.description},${parameter.unitName}</td>
					<td><form:input
							path="resourceLinear[${param_i.index}].segments[0].begin" /></td>
					<td><form:input
							path="resourceLinear[${param_i.index}].segments[0].end" /></td>
					<td><form:hidden
							path="resourceLinear[${param_i.index}].linearParameterDescription"
							value="${parameter.description}" /></td>
				</tr>
			</c:forEach>

			<tr>
				<td><spring:message code="label.resource.identifier" />:</td>
				<td><form:input path="identifier" /></td>
			</tr>
			<tr>
				<td><spring:message code="label.resource.reasonInclusion" />:</td>
				<td><form:input path="reasonInclusion" /></td>
			</tr>
			<tr>
				<td><spring:message code="label.resource.date" />:</td>
				<td><input type="date" name="inputDate" /></td>
			</tr>
			<tr>
				<td><spring:message code="label.resource.registrator" />(<spring:message
						code="label.resource.tome" />):</td>
				<td><form:select path="tomeIdentifier">
						<option value=""><spring:message
								code="label.resource.registrator.select" />:
						</option>
						<c:forEach items="${tomes}" var="tome">
							<option value="${tome.identifier}">${tome.registrator.lastName}
								${tome.registrator.middleName}${tome.registrator.firstName}
								(номер тому: ${tome.identifier})</option>
						</c:forEach>
					</form:select></td>
			</tr>
			<tr>
				<td><spring:message code="label.resource.coordinates" />:</td>
			</tr>
		</table>
		<table>
			<tr>
				<th><spring:message code="label.resource.orderPoint" /></th>
				<th colspan="3"><spring:message code="label.resource.latitude" /></th>
				<th colspan="3"><spring:message code="label.resource.longitude" /></th>
			</tr>

			<!-- Area should consist of three point at least -->
			<!-- TODO dynamic filling of area points/can be more than three -->
			<c:forEach var="number" begin="0" end="2">
				<tr>
					<td><form:input
							path="resourceArea.poligons[0].points[${number}].orderNumber"
							value="${number+1}" /></td>
					<td><form:input
							path="resourceArea.poligons[0].points[${number}].latitudeDegrees" /></td>
					<td><form:input
							path="resourceArea.poligons[0].points[${number}].latitudeMinutes" /></td>
					<td><form:input
							path="resourceArea.poligons[0].points[${number}].latitudeSeconds" /></td>
					<td><form:input
							path="resourceArea.poligons[0].points[${number}].longitudeDegrees" /></td>
					<td><form:input
							path="resourceArea.poligons[0].points[${number}].longitudeMinutes" /></td>
					<td><form:input
							path="resourceArea.poligons[0].points[${number}].longitudeSeconds" /></td>
				</tr>
			</c:forEach>
		</table>
		<input type="submit" value="Додати" class="btn btn-success" />
	</form:form>
</div>



