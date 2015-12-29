<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<spring:url
	value="http://ajax.googleapis.com/ajax/libs/jquery/1.3/jquery.min.js"
	var="jqueryJs" />
<spring:url value="/resource/js/addArea.js" var="addAreaJs" />
<script src="${jqueryJs}"></script>
<script src="${addAreaJs}"></script>

<%-- <spring:url
	value="http://ajax.googleapis.com/ajax/libs/jquery/1.3/jquery.min.js"
	var="jqueryJs" />
<spring:url value="/resource/js/addResourceValue.js" var="addResourceValueJs" />
<script src="${jqueryJs}"></script>
<script src="${addResourceValueJs}"></script> --%>

<!-- Showing all discrete parameters for resource type -->
<c:if test="${not empty discreteParameters}">
	<c:forEach items="${discreteParameters}" var="parameter"
		varStatus="param_i">
		<div id="discreteInput_${param_i.index}_1" class="form-group DiscreteInput_${param_i.index}">
			<label class="control-label col-sm-3">${parameter.description},${parameter.unitName}:</label>
			<input type="hidden"
				name="resourceDiscrete[${param_i.index}].discreteParameterDescription"
				value="${parameter.description}">
			<div class="col-sm-3 ">
				<input class="form-control"
					name="resourceDiscrete[${param_i.index}].values[0]" required>

			</div>
		</div>
		<div>
			<button type="button" id="btnAddDiscreteValue_${param_i.index}"
				class="btn btn-primary">+</button>
			<button type="button" id="btnDelDiscreteValue_${param_i.index}"
				class="btn btn-primary deleteButton">-</button>
		</div>
	</c:forEach>
</c:if>


<!-- Showing all discrete parameters for resource type -->
<c:if test="${not empty linearParameters}">
	<c:forEach items="${linearParameters}" var="parameter"
		varStatus="param_i">
		<div id="linearInput_${param_i.index}_1" class="form-group LinearInput_${param_i.index}">
			<label class="control-label col-sm-3">${parameter.description},${parameter.unitName}:</label>
			<input type="hidden"
				name="resourceLinear[${param_i.index}].linearParameterDescription"
				value="${parameter.description}">
			<div class="col-sm-3 ">
				<input class="form-control"
					name="resourceLinear[${param_i.index}].segments[0].begin" required>
			</div>
			<div class="col-sm-3 ">
				<input class="form-control"
					name="resourceLinear[${param_i.index}].segments[0].end" required>

			</div>
		</div>
		<div>
			<button type="button" id="btnAddLinearValue_${param_i.index}"
				class="btn btn-primary">+</button>
			<button type="button" id="btnDelLinearValue_${param_i.index}"
				class="btn btn-primary deleteButton">-</button>
		</div>
	</c:forEach>
</c:if>