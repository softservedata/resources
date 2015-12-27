<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<c:set var="req" value="${pageContext.request}" />
<c:set var="url">${req.requestURL}</c:set>
<c:set var="base"
	value="${fn:substring(url, 0, fn:length(url) - fn:length(req.requestURI))}${req.contextPath}/" />

<spring:url
	value="http://ajax.googleapis.com/ajax/libs/jquery/1.3/jquery.min.js"
	var="jqueryJs" />
<spring:url value="/resource/js/addArea.js" var="addAreaJs" />
<script src="${jqueryJs}"></script>
<script src="${addAreaJs}"></script>

<div>

	<h1>
		<spring:message code="label.resource.add" />
	</h1>
	<form:form method="POST" action="addresource"
		modelAttribute="newresource">
		<table id="datatable" class="table display">
			<tr>
				<td><spring:message code="label.resource.description" />:</td>
				<td><form:input path="description" value="${description}" /></td>
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
					<td>${parameter.description},${parameter.unitName}:</td>
					<td><input name="resourceDiscrete[${param_i.index}].values"
						required="required" value="${0.0}" /> <input type="hidden"
						name="resourceDiscrete[${param_i.index}].discreteParameterDescription"
						value="${parameter.description}" /></td>
				</tr>
			</c:forEach>
			<c:forEach items="${resType.linearParameters}" var="parameter"
				varStatus="param_i">
				<tr>
					<td>${parameter.description},${parameter.unitName}:</td>
					<td><input
						name="resourceLinear[${param_i.index}].segments[0].begin"
						required="required" value="${0.0}" /></td>
					<td><input
						name="resourceLinear[${param_i.index}].segments[0].end"
						required="required" value="${0.0}" /> <input type="hidden"
						name="resourceLinear[${param_i.index}].linearParameterDescription"
						value="${parameter.description}" /></td>
				</tr>
			</c:forEach>
			<tr>
				<td><spring:message code="label.resource.identifier" />:</td>
				<td><form:input path="identifier" required="required"
						value="${identifier}" /> <form:errors path="identifier" /></td>
			</tr>
			<tr>
				<td><spring:message code="label.resource.reasonInclusion" />:</td>
				<td colspan="3"><textarea name="reasonInclusion"
						style="width: 500px; height: 200px;">${reasonInclusion} </textarea></td>
			</tr>
			<tr>
				<td><spring:message code="label.resource.date" />:</td>
				<td><input type="date" name="date" required="required"
					value="${date}" /></td>
			</tr>
			<tr>
				<td><spring:message code="label.resource.registrator" />(<spring:message
						code="label.resource.tome" />):</td>
				<td><form:select path="tomeIdentifier" required="required">
						<option value=""><spring:message
								code="label.resource.registrator.select" />:
						</option>
						<c:forEach items="${tomes}" var="tome">
							<option value="${tome.identifier}">${tome.registrator.lastName}
								${tome.registrator.middleName} ${tome.registrator.firstName}
								(номер тому: ${tome.identifier})</option>
						</c:forEach>
					</form:select></td>
			</tr>
			<tr>
				<td><spring:message code="label.resource.coordinates" />:</td>
			</tr>
		</table>

        <%--Container for Google map--%>
        <p><i>Щоб виділити на мапі область, оберіть інструмент "Намалювати фігуру" в верхній частині мапи.</i></p>
        <div id="map_canvas" class="container" style="height: 500px; padding: 20px 0px;"></div>

		<table id="datatable">
			<tr>
				<th style="width: 300px"><spring:message
						code="label.resource.orderPoint" /></th>
				<th align="right" style="width: 550px"><spring:message
						code="label.resource.latitude" /></th>
				<th align="center" style="width: 300px"><spring:message
						code="label.resource.longitude" /></th>
			</tr>
		</table>
		<div id="input1" class="clonedInput" style="float: left">
			<input id="myparam0" style="width: 100px"
				name="resourceArea.poligons[0].points[0].orderNumber" type="text"
				value="${1}" disabled />
            <input id="myparam1"
				name="resourceArea.poligons[0].points[0].latitudeDegrees"
				value="${0}" />
            <input id="myparam2"
				name="resourceArea.poligons[0].points[0].latitudeMinutes"
				value="${0}" />
            <input id="myparam3"
				name="resourceArea.poligons[0].points[0].latitudeSeconds"
				value="${0.0}" />
            <input id="myparam4"
				name="resourceArea.poligons[0].points[0].longitudeDegrees"
				value="${0}" />
            <input id="myparam5"
				name="resourceArea.poligons[0].points[0].longitudeMinutes"
				value="${0}" />
            <input id="myparam6"
				name="resourceArea.poligons[0].points[0].longitudeSeconds"
				value="${0.0}" />
		</div>
		<div id="mybuttontype">
			<input type="button" id="btnAddArea" value="+"
				class="btn btn-primary" /> <input type="button" id="btnDelArea"
				value="-" class="btn btn-primary" />
		</div>
		<br />
		<div class="button">
			<input type="submit" value=<spring:message code="label.save"/> class="btn  btn-success"/>
			<button type="reset" class="btn btn-default">
				<spring:message code="label.clearall" />
			</button>
		</div>
	</form:form>

    <%--Scripts for Google Map--%>
    <script src="http://maps.googleapis.com/maps/api/js?sensor=false&libraries=drawing"></script>
    <script type="text/javascript" src="${base}resource/js/addResourceOnMap.js"></script>
</div>


