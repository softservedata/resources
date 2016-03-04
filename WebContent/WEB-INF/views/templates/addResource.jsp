<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<c:set var="newresource" value="${newresource}" scope="session" />
<c:set var="firstPoint" value="${newresource.resourceArea.poligons[0].points[0].latitudeDegrees}"/>

<c:if test="${!empty newresource.date}">
	<fmt:formatDate value="${newresource.date}" pattern="dd.MM.yyyy"
		var="FormatedDate" />
</c:if>

<div class="container">
	<h2>
		<spring:message code="label.resource.add" />
	</h2>
	<form:form id="addResource" method="POST" action="addresource"
		modelAttribute="newresource" class="form-horizontal">

		<!-- select co-owner -->
		<div class="form-group">
			<label class="control-label col-md-3"><spring:message
					code="label.resource.co-owner.select" />:</label>
			<div class="col-md-3">
				<input class="form-control" id="owner_search" type="text">
			</div>
		</div>

		<!-- login of the selected co-owner -->
		<div class="form-group"  hidden="true">
			<label class="control-label col-md-3">login:</label>
			<div class="col-md-3">
				<input class="form-control" id="owner_login" type="text" 
				value="${ownerLogin}" name="ownerLogin">
			</div>
		</div>

		<!-- add resource description -->
		<div class="form-group">
			<label class="control-label col-md-3"><spring:message
					code="label.resource.description" />:</label>
			<div class="col-md-3">
				<input class="form-control" name="description" id="w-input-search"
					value="${newresource.description}">
				<div class="control-group error">
					<form:errors path="description" cssClass="error" style="color:red" />
				</div>
			</div>
		</div>

		<!-- select resource type -->
		<div class="form-group">
			<label class="control-label col-md-3"><spring:message
					code="label.resource.type" />:</label>
			<div class="col-md-3">
				<select id="resourcesTypeSelect" name="resourceType"
					class="form-control" required>
					<c:if test="${!empty newresource.resourceType}">
						<option value="${newresource.resourceType}">${newresource.resourceType}
						</option>
					</c:if>
					<c:if test="${empty newresource.resourceType}">
						<option value=""><spring:message
								code="label.resource.selectType" />:
						</option>
					</c:if>
					<c:forEach items="${listOfResourceType}" var="restype">
						<option value="${restype.typeName}">${restype.typeName}</option>
					</c:forEach>
				</select>
				<div class="control-group error">
					<form:errors path="resourceType" cssClass="error" style="color:red" />
				</div>
			</div>
		</div>

		<!-- input of selected type parameters  -->
		<div id="typeParameters"></div>

		<!-- registration number of resource-->
		<div class="form-group">
			<label class="control-label col-md-3"><spring:message
					code="label.resource.identifier" />:</label>
			<div class="col-md-3">
				<input class="form-control" name="identifier" id="identifier"
					value="${newresource.identifier}" readonly>
				<div class="control-group error">
					<form:errors path="identifier" cssClass="error" style="color:red" />
				</div>
			</div>
			<div class="col-md-3">
				<button id="editNumber" type="button" class="btn btn-primary">
					<spring:message code="label.resource.edit" />
				</button>
			</div>

		</div>

		<!-- reasonInclusion -->
		<div class="form-group">
			<label class="control-label col-md-3"><spring:message
					code="label.resource.reasonInclusion" />:</label>
			<div class="col-md-3">
				<textarea id="reasonInclusion" class="form-control" rows="5"
					name="reasonInclusion">${newresource.reasonInclusion}</textarea>
				<div class="control-group error">
					<form:errors path="reasonInclusion" cssClass="error"
						style="color:red" />
				</div>
			</div>
			<div class="checkbox">
				<div class="col-md-3">
					<label><input class="disable" id="pass" type="checkbox" disabled> <spring:message
							code="label.resource.pass" /></label><br /> <label><input class="disable"
						id="will" type="checkbox" disabled> <spring:message
							code="label.resource.willDocument" /></label><br /> <label><input class="disable"
						id="otherDocs" type="checkbox" disabled> <spring:message
							code="label.resource.otherDocuments" /></label><br /> <label><input class="disable"
						id="tytul" type="checkbox" disabled> <spring:message
							code="label.resource.propertyTitle" /></label><br /> <label><input
						id="delivery" type="checkbox"> <spring:message
							code="label.resource.assignment" /></label>
				</div>
			</div>

		</div>

		<!-- reasonInclusion -->
		<div class="form-group">
			<label class="control-label col-md-3"><spring:message
					code="label.resource.date" />:</label>
			<div class="col-md-3">
				<input class="form-control" type="text" id="datepicker" name="date"
					value="${FormatedDate}">
				<div class="control-group error">
					<form:errors path="date" cssClass="error" style="color:red" />
				</div>
			</div>
		</div>

		<!-- fill tome identifier -->
		<%--         <div class="form-group ">
            <label class="control-label col-md-3"><spring:message
                    code="label.resource.tome" />:</label>
            <div class="col-md-3">
                <input class="form-control" name="tomeIdentifier" required readonly
                    value="${tome.identifier}">
            </div>
        </div> --%>

		<!-- Coordinates -->
		<legend>
			<spring:message code="label.resource.coordinates" />
		</legend>

		<%--Container for Google map--%>
		<div class="col-md-12" style="margin-bottom: 10px;">
			<div class="col-md-12" id="mapManual">
				<span class="glyphicon glyphicon-triangle-right"></span> <span
					class="glyphicon glyphicon-triangle-bottom hidden"></span> <span><spring:message
						code="label.howToUseMap" /></span>
			</div>
			<div class="spoiler col-md-12">
				Щоб додати область натисніть кнопку "Додати полігон". Клікаючи на
				мапі лівою кнопкою мишки виділіть потрібну область.<br /> Якщо Ви
				хочете виділити область поруч з існуючою, таким чином, щоб вони мали
				спільну межу, виконайте наступні дії:<br /> 1. Знайдіть на мапі
				потрібну область і оберіть такий масштаб, щоб вся область поміщалася
				в межах мапи.<br /> 2. Натисніть кнопку показати ресурси. <br />
				3. На мапі відобразяться області ресурсів вже внесених в базу.<br />
				4. Коли ви підведете мишку до однієї з вершин показаних областей, на
				ній з'явиться маркер. Затиснувши кнопку шифт клікніть лівою кнопкою
				миші. Таким чином Ви оберете для нової області точку з тими ж
				координатами, що має відображена область.<br /> Якщо Ви зробили
				помилку - Ви можете видалити введену точку клікнувши на ній правою
				кнопкою миші.<br /> Після повного введення області натисніть кнопку
				"Зберегти". Після цього область вже буде недоступною для
				редагування.<br /> Якщо Вам необхідно додати ще одну область до
				ресурсу, повторіть кроки описані вище.<br /> Після закінчення
				введення областей, натисність кнопку "Додати координати з мапи".
				Після цього вже буде не можливо додати нову область.
			</div>
		</div>

		<div id="map_canvas" class="container"
			style="height: 500px; padding: 20px 0px;"></div>
		<div class="col-md-12">
			<button id="addPointsFromMap" class="btn btn-primary" type="button"
				style="margin-top: 10px;">
				<spring:message code="label.resource.coordinates.addFromMap" />
			</button>
			<button id="addPointsToMap" class="btn btn-primary" type="button"
				style="margin-top: 10px;">
				<spring:message code="label.resource.coordinates.addToMap" />
			</button>
			<%--<button id="show_UA" class="btn btn-primary" type="button"--%>
			<%--style="margin-top: 10px;">Show Ukraine</button>--%>
			<span id="infoBox" class="col-md-5"><spring:message
                    code="label.resource.infoBox" /></span>
		</div>

        <div class="col-md-12">
            <input type="checkbox" id="allUkraine"
                <c:if test="${firstPoint == 200}">checked</c:if>>
            <label for="allUkraine"><spring:message code="label.resource.allUkraine"/></label>
        </div>

        <div id="latLngAdd"
                <c:if test="${firstPoint == 200}">style="display: none;" </c:if>
                >
            <div class="form-group">
                <label class="control-label col-md-1"><spring:message
                        code="label.resource.orderPoint"/></label> <label
                    class="control-label col-md-3"><spring:message
                    code="label.resource.latitude"/></label> <label
                    class="control-label col-md-3"><spring:message
                    code="label.resource.longitude"/></label>
            </div>

            <c:if test="${firstPoint != 0}">
                <c:forEach items="${newresource.resourceArea.poligons}" var="polygon" varStatus="polygonStatus">
                    <c:set value="${polygonStatus.count -1}" var="polygonIndex"/>
                    <div id="polygon_${polygonIndex+1}">
                        <h4>
                            <spring:message code="label.resource.Polygon"/>
                            <span class="polygonIndex">${polygonIndex+1}</span>:
                        </h4>
                        <c:forEach items="${polygon.points}" var="point" varStatus="pointStatus">
                            <c:set value="${pointStatus.count -1}" var="pointIndex"/>
                        <div id="areaInput1" class="form-group clonedAreaInput">
                            <div class="col-md-1" style="width: 130px; height: 34px">
                                <input id="pointNumber" class="form-control"
                                       name="resourceArea.poligons[${polygonIndex}].points[${pointIndex}].orderNumber" value="${point.orderNumber}"
                                       readonly>
                            </div>
                            <div class="col-md-1" style="width: 130px; height: 34px">
                                <input id="myparam1" class="form-control"
                                       name="resourceArea.poligons[${polygonIndex}].points[${pointIndex}].latitudeDegrees"
                                       value="${point.latitudeDegrees}" required>

                                <div class="control-group error">
                                    <form:errors
                                            path="resourceArea.poligons[${polygonIndex}].points[${pointIndex}].latitudeDegrees"
                                            cssClass="error" style="color:red"/>
                                </div>
                            </div>
                            <div class="col-md-1" style="width: 130px; height: 34px">
                                <input id="myparam2" class="form-control"
                                       name="resourceArea.poligons[${polygonIndex}].points[${pointIndex}].latitudeMinutes"
                                       value="${point.latitudeMinutes}" required>

                                <div class="control-group error">
                                    <form:errors
                                            path="resourceArea.poligons[${polygonIndex}].points[${pointIndex}].latitudeMinutes"
                                            cssClass="error" style="color:red"/>
                                </div>
                            </div>
                            <div class="col-md-1" style="width: 130px; height: 34px">
                                <input id="myparam3" class="form-control"
                                       name="resourceArea.poligons[${polygonIndex}].points[${pointIndex}].latitudeSeconds"
                                       value="${point.latitudeSeconds}" required>

                                <div class="control-group error">
                                    <form:errors
                                            path="resourceArea.poligons[${polygonIndex}].points[${pointIndex}].latitudeSeconds"
                                            cssClass="error" style="color:red"/>
                                </div>
                            </div>
                            <div class="col-md-1" style="width: 130px; height: 34px">
                                <input id="myparam4" class="form-control"
                                       name="resourceArea.poligons[${polygonIndex}].points[${pointIndex}].longitudeDegrees"
                                       value="${point.longitudeDegrees}" required>

                                <div class="control-group error">
                                    <form:errors
                                            path="resourceArea.poligons[${polygonIndex}].points[${pointIndex}].longitudeDegrees"
                                            cssClass="error" style="color:red"/>
                                </div>
                            </div>
                            <div class="col-md-1" style="width: 130px; height: 34px">
                                <input id="myparam5" class="form-control"
                                       name="resourceArea.poligons[${polygonIndex}].points[${pointIndex}].longitudeMinutes"
                                       value="${point.longitudeMinutes}" required>

                                <div class="control-group error">
                                    <form:errors
                                            path="resourceArea.poligons[${polygonIndex}].points[${pointIndex}].longitudeMinutes"
                                            cssClass="error" style="color:red"/>
                                </div>
                            </div>
                            <div class="col-md-1" style="width: 130px; height: 34px">
                                <input id="myparam6" class="form-control"
                                       name="resourceArea.poligons[${polygonIndex}].points[${pointIndex}].longitudeSeconds"
                                       value="${point.longitudeSeconds}" required>

                                <div class="control-group error">
                                    <form:errors
                                            path="resourceArea.poligons[${polygonIndex}].points[${pointIndex}].longitudeSeconds"
                                            cssClass="error" style="color:red"/>
                                </div>
                            </div>
                            <div class="col-md-1">
                                <a class="delPoint glyphicon glyphicon-remove-sign"></a>
                            </div>
                        </div>
                    </c:forEach>
                    </div>
                </c:forEach>
            </c:if>

            <c:if test="${firstPoint == null}">
                <div id="polygon_1">
                    <h4>
                        <spring:message code="label.resource.Polygon"/>
                        <span class="polygonIndex">1</span>:
                    </h4>
                <div id="areaInput1" class="form-group clonedAreaInput">
                    <div class="col-md-1" style="width: 130px; height: 34px">
                        <input id="pointNumber" class="form-control"
                               name="resourceArea.poligons[0].points[0].orderNumber" value="${1}"
                               readonly>
                    </div>
                    <div class="col-md-1" style="width: 130px; height: 34px">
                        <input id="myparam1" class="form-control"
                               name="resourceArea.poligons[0].points[0].latitudeDegrees"
                               value="${0}" required>

                        <div class="control-group error">
                            <form:errors
                                    path="resourceArea.poligons[0].points[0].latitudeDegrees"
                                    cssClass="error" style="color:red"/>
                        </div>
                    </div>
                    <div class="col-md-1" style="width: 130px; height: 34px">
                        <input id="myparam2" class="form-control"
                               name="resourceArea.poligons[0].points[0].latitudeMinutes"
                               value="${0}" required>

                        <div class="control-group error">
                            <form:errors
                                    path="resourceArea.poligons[0].points[0].latitudeMinutes"
                                    cssClass="error" style="color:red"/>
                        </div>
                    </div>
                    <div class="col-md-1" style="width: 130px; height: 34px">
                        <input id="myparam3" class="form-control"
                               name="resourceArea.poligons[0].points[0].latitudeSeconds"
                               value="${0.0}" required>

                        <div class="control-group error">
                            <form:errors
                                    path="resourceArea.poligons[0].points[0].latitudeSeconds"
                                    cssClass="error" style="color:red"/>
                        </div>
                    </div>
                    <div class="col-md-1" style="width: 130px; height: 34px">
                        <input id="myparam4" class="form-control"
                               name="resourceArea.poligons[0].points[0].longitudeDegrees"
                               value="${0}" required>

                        <div class="control-group error">
                            <form:errors
                                    path="resourceArea.poligons[0].points[0].longitudeDegrees"
                                    cssClass="error" style="color:red"/>
                        </div>
                    </div>
                    <div class="col-md-1" style="width: 130px; height: 34px">
                        <input id="myparam5" class="form-control"
                               name="resourceArea.poligons[0].points[0].longitudeMinutes"
                               value="${0}" required>

                        <div class="control-group error">
                            <form:errors
                                    path="resourceArea.poligons[0].points[0].longitudeMinutes"
                                    cssClass="error" style="color:red"/>
                        </div>
                    </div>
                    <div class="col-md-1" style="width: 130px; height: 34px">
                        <input id="myparam6" class="form-control"
                               name="resourceArea.poligons[0].points[0].longitudeSeconds"
                               value="${0.0}" required>

                        <div class="control-group error">
                            <form:errors
                                    path="resourceArea.poligons[0].points[0].longitudeSeconds"
                                    cssClass="error" style="color:red"/>
                        </div>
                    </div>
                    <div class="col-md-1">
                        <a class="delPoint glyphicon glyphicon-remove-sign"></a>
                    </div>
                </div>
            </div>
            </c:if>

            <div id="mybuttontype">
                <button class="btn btn-primary" id="btnAddAreaPoint" type="button">
                    <spring:message code="label.resource.coordinates.addPoint" />
                </button>
                <button class="btn btn-primary" id="addPolygon" type="button">
                    <spring:message code="label.resource.coordinates.addPolygon" />
                </button>
                <%--<input type="button"--%>
                       <%--id="btnDelAreaPoint" value="-"--%>
                       <%--class="btn btn-primary deleteButton"/>--%>
                <button class="btn btn-danger" id="clearAllPoints" type="button">
                    <spring:message code="label.resource.coordinates.clearAll" />
                </button>
                <button class="btn btn-danger" id="delAllPolygons" type="button">
                    <spring:message code="label.resource.coordinates.delAllPolygons" />
                </button>
            </div>
            <br/>
        </div>
		<div class="button">
			<input id="submitForm" type="submit" class="btn btn-success"
				value=<spring:message code="label.save"/>>
			<button id="resetForm" type="reset" class="btn btn-default">
				<spring:message code="label.clearall" />
			</button>
		</div>
	</form:form>

	<%--Search on the map--%>
	<p>
		<input id="gmaps-input" class="controls gmap-input"
			style="width: 300px;" type="text"
			placeholder=<spring:message code="label.menu.searchOnMap"/>>
	</p>

	<%--Search existing resources on the map--%>
	<p>
		<a id="gmaps-show-res" class="controls gmap-button"><spring:message
				code="label.showResources" /></a>

		<%--Control buttons for polygon drawing--%>
	<div id='cp-wrap' style="width: 80px;">
		<span class="toggle"> <a data-action='save'
			class="controls gmap-button" style="position: relative;"><spring:message
					code="label.savePolygon" /></a> <a data-action='cancel'
			class="controls gmap-button" style="position: relative;"><spring:message
					code="label.cancelPolygon" /></a>
		</span> <span class="toggle active"> <a data-action='new'
			class="controls gmap-button"><spring:message
					code="label.addPolygon" /></a>
		</span>

	</div>

	<%--AJAX Loader on the dark display--%>
	<div id="dark_bg">
		<div class="windows8">
			<div class="wBall" id="wBall_1">
				<div class="wInnerBall"></div>
			</div>
			<div class="wBall" id="wBall_2">
				<div class="wInnerBall"></div>
			</div>
			<div class="wBall" id="wBall_3">
				<div class="wInnerBall"></div>
			</div>
			<div class="wBall" id="wBall_4">
				<div class="wInnerBall"></div>
			</div>
			<div class="wBall" id="wBall_5">
				<div class="wInnerBall"></div>
			</div>
		</div>
	</div>

	<%--Scripts for Google Map--%>
	<script
		src="http://maps.googleapis.com/maps/api/js?sensor=false&libraries=drawing,places"></script>
	<script type="text/javascript"
		src="<c:url value="/resource/js/lib/polysnapper.js"/>"></script>
	<script type="text/javascript"
		src="<c:url value='/resource/js/addResourceOnMap.js'/>"></script>
	<script type="text/javascript"
		src="<c:url value='/resource/js/checkIntersection.js'/>"></script>
	<script type="text/javascript"
		src="<c:url value='/resource/js/isInsideUkraine.js'/>"></script>	
</div>

<script
	src="<c:url value='/resource/js/lib/jquery-ui.datepscker.min.js'/>"></script>
<script
	src="<c:url value='/resource/js/lib/jquery.autocomplete.min.js'/>"></script>
<script src="<c:url value='/resource/js/descriptionAutocomplete.js'/>"></script>
<script src="<c:url value='/resource/js/ownerControl.js'/>"></script>
<script src="<c:url value='/resource/js/addArea.js'/>"></script>
<link rel="stylesheet" type="text/css"
	href="<c:url value='/resource/css/suggestion.css'/>">
<link rel="stylesheet" type="text/css"
	href="<c:url value='/resource/css/cssload.css'/>">
<link rel="stylesheet"
	href="https://code.jquery.com/ui/1.11.3/themes/smoothness/jquery-ui.css">

