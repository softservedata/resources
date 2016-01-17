<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<script src="<c:url value='/resource/js/addParameterValues.js'/>"></script>

<!-- Showing all discrete parameters for resource type -->
<c:if test="${not empty discreteParameters}">
	<c:forEach items="${discreteParameters}" var="parameter"
		varStatus="param_i">
		<div id="discreteInput_${param_i.index}_1"
			class="form-group DiscreteInput_${param_i.index}">
			<label class="control-label col-sm-3">${parameter.description},${parameter.unitName}:</label>
			<input type="hidden"
				name="resourceDiscrete[${param_i.index}].discreteParameterDescription"
				value="${parameter.description}">
			<div class="col-sm-3 ">
				<input class="form-control"
					name="resourceDiscrete[${param_i.index}].valueDiscretes[0].value"
					required>
			</div>
			<div class="col-sm-3 ">
				<input class="form-control"
					name="resourceDiscrete[${param_i.index}].valueDiscretes[0].comment"
					placeholder="коментар">
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
		<div id="linearInput_${param_i.index}_1"
			class="form-group LinearInput_${param_i.index}">
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