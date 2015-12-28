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

<div class="container">
	<h2>
		<spring:message code="label.resource.add" />
	</h2>
	<form:form method="POST" action="addresource"
		modelAttribute="newresource" class="form-horizontal">
		<div class="form-group">
			<label class="control-label col-sm-3"><spring:message
					code="label.resource.description" />:</label>
			<div class="col-sm-3">
				<input class="form-control" name="description"
					value="${description}">
			</div>
		</div>
		<div class="form-group">
			<label class="control-label col-sm-3"><spring:message
					code="label.resource.type" />:</label>
			<div class="col-sm-3">
				<select id="resourcesTypeSelect" name="resourceType"
					class="form-control" required>
					<option value=""><spring:message
							code="label.resource.selectType" />:
					</option>
					<c:forEach items="${listOfResourceType}" var="restype">
						<option value="${restype.typeName}">${restype.typeName}</option>
					</c:forEach>
				</select>
			</div>
		</div>
		<div id="typeParameters"></div>
		<div class="form-group">
			<label class="control-label col-sm-3"><spring:message
					code="label.resource.identifier" />:</label>
			<div class="col-sm-3">
				<input class="form-control" name="identifier" required
					value="${identifier}">
				<form:errors name="identifier" />
			</div>
		</div>
		<div class="form-group">
			<label class="control-label col-sm-3"><spring:message
					code="label.resource.reasonInclusion" />:</label>
			<div class="col-sm-3">
				<textarea class="form-control" rows="5" name="reasonInclusion"
					required></textarea>
			</div>
		</div>
		<div class="form-group">
			<label class="control-label col-sm-3"><spring:message
					code="label.resource.date" />:</label>
			<div class="col-sm-3">
				<input class="form-control" type="date" name="date" required
					value="${date}">
			</div>
		</div>
		<div class="form-group ">
			<label class="control-label col-sm-3"><spring:message
					code="label.resource.registrator" />(<spring:message
					code="label.resource.tome" />):</label>
			<div class="col-sm-3">
				<select name="tomeIdentifier" class="form-control" required>
					<option value=""><spring:message
							code="label.resource.registrator.select" />:
					</option>
					<c:forEach items="${tomes}" var="tome">
						<option value="${tome.identifier}">${tome.registrator.lastName}
							${tome.registrator.middleName} ${tome.registrator.firstName}
							(номер тому: ${tome.identifier})</option>
					</c:forEach>
				</select>
			</div>
		</div>
		<legend>
			<spring:message code="label.resource.coordinates" />
		</legend>

		<%--Container for Google map--%>
		<p>
			<i>Щоб виділити на мапі область, оберіть інструмент "Намалювати
				фігуру" в верхній частині мапи.</i>
		</p>

		<div id="map_canvas" class="container"
			style="height: 500px; padding: 20px 0px;"></div>

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
			<input type="submit" value=<spring:message code="label.save"/> class="btn  btn-success formsubmit"/>
			<button type="reset" class="btn btn-default">
				<spring:message code="label.clearall" />
			</button>
		</div>
	</form:form>

<%-- 		<div class="form-group">
			<label class="control-label col-sm-1"><spring:message
					code="label.resource.orderPoint" /></label> <label
				class="control-label col-sm-3"><spring:message
					code="label.resource.latitude" /></label> <label
				class="control-label col-sm-3"><spring:message
					code="label.resource.longitude" /></label>
		</div>
		<div id="areaInput1" class="form-group clonedAreaInput">
			<div class="col-sm-1" style="width: 130px; height: 34px">
				<input id="pointNumber" class="form-control"
					name="resourceArea.poligons[0].points[0].orderNumber" value="${1}"
					readonly>
			</div>
			<div class="col-sm-1" style="width: 130px; height: 34px">
				<input class="form-control"
					name="resourceArea.poligons[0].points[0].latitudeDegrees" required>
			</div>
			<div class="col-sm-1" style="width: 130px; height: 34px">
				<input class="form-control"
					name="resourceArea.poligons[0].points[0].latitudeMinutes" required>
			</div>
			<div class="col-sm-1" style="width: 130px; height: 34px">
				<input class="form-control"
					name="resourceArea.poligons[0].points[0].latitudeSeconds" required>
			</div>
			<div class="col-sm-1" style="width: 130px; height: 34px">
				<input class="form-control"
					name="resourceArea.poligons[0].points[0].longitudeDegrees" required>
			</div>
			<div class="col-sm-1" style="width: 130px; height: 34px">
				<input class="form-control"
					name="resourceArea.poligons[0].points[0].longitudeMinutes" required>
			</div>
			<div class="col-sm-1" style="width: 130px; height: 34px">
				<input class="form-control"
					name="resourceArea.poligons[0].points[0].longitudeSeconds" required>
			</div>
		</div>

		<div id="mybuttontype">
			<input type="button" id="btnAddAreaPoint" value="+"
				class="btn btn-primary" /> <input type="button"
				id="btnDelAreaPoint" value="-" class="btn btn-primary deleteButton" />
		</div>
		<br />
		<div class="button">
			<input type="submit" value=<spring:message code="label.save"/>
				class="btn btn-success">
			<button type="reset" class="btn btn-default" id="hahaha">
				<spring:message code="label.clearall" />
			</button>
		</div>
	</form:form> --%>

	<%--Scripts for Google Map--%>
    <p>
        <input id="gmaps-input" class="controls form-control" style="width: 300px; margin: 9px 0px;" type="text" placeholder="Пошук на мапі">
    </p>
	<script
		src="http://maps.googleapis.com/maps/api/js?sensor=false&libraries=drawing,places"></script>
	<script type="text/javascript"
		src="${base}resource/js/addResourceOnMap.js"></script>
</div>


