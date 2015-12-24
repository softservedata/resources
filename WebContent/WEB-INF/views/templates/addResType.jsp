<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<c:set var="req" value="${pageContext.request}" />
<c:set var="url">${req.requestURL}</c:set>
<c:set var="base"
	value="${fn:substring(url, 0, fn:length(url) - fn:length(req.requestURI))}${req.contextPath}/" />

<spring:url
	value="http://ajax.googleapis.com/ajax/libs/jquery/1.3/jquery.min.js"
	var="jqueryJs" />
<spring:url value="/resource/js/add.js" var="addJs" />
<script src="${jqueryJs}"></script>
<script src="${addJs}"></script>

	<button type="button" id="clickmeshow" class="btn btn-primary btn-sm"><spring:message code="label.restype.addparameter"/></button>
	<button type="button" id="clickmehide" class="btn btn-primary btn-sm"><spring:message code="label.restype.hideparameter"/></button>
<h3>
	<spring:message code="label.restype.add" />
</h3>

<form:form method="POST" action="addrestype" modelAttribute="newrestype">

	<div class="caption-res-type">
	<spring:message code="label.restype.title" />: 
		<form:input path="typeName" placeholder="Введіть назву тут"
			required="required" /> 		
	</div>
	<br/>
	<div id="input1" class="clonedInput" style="display:none" >
		<spring:message code="label.restype.desc"/>: 
		<input id="myparam0" name="parameters[0].description"
			type="text" value="" /> 
		<spring:message code="label.restype.unitOfMeasurement"/>: 
		<input id="myparam1" name="parameters[0].unitName" type="text" value="" /> 
			<select
			id="myparam2" name="parameters[0].parametersType"
			class="selectpicker">
			<option value=""><spring:message code="label.restype.option"/></option>
			<option value="linearParameters"><spring:message code="label.restype.linpar"/></option>
			<option value="discreteParameters"><spring:message code="label.restype.dispar"/></option>
		</select>
	</div><br/>
	<div id="mybuttontype">
		<input type="button" id="btnAdd" value="+" class="btn btn-primary" />
		<input type="button" id="btnDel" value="-" class="btn btn-primary" />
	</div>
	<br />
	<div class="button">
		<input type="submit" value=<spring:message code="label.save"/> class="btn btn-success">
		<button type="reset" class="btn btn-default"><spring:message code="label.clearall"/></button>
	</div>
</form:form>