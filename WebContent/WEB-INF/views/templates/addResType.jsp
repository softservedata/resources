<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<c:set var="req" value="${pageContext.request}" />
<c:set var="url">${req.requestURL}</c:set>
<c:set var="base" value="${fn:substring(url, 0, fn:length(url) - fn:length(req.requestURI))}${req.contextPath}/" />

 <spring:url
	value="http://ajax.googleapis.com/ajax/libs/jquery/1.3/jquery.min.js"
	var="jqueryJs" />
<spring:url value="/resource/js/add.js" var="addJs" />
<script src="${jqueryJs}"></script>
<script src="${addJs}"></script> 


	<h1>
		<spring:message code="label.restype.add" />
	</h1>


	<%-- <form:form method="POST" action="addrestype"
		modelAttribute="newrestype">
		<table>
			<tr>
				<td><spring:message code="label.resource.description" />:</td>
				<td><form:input path="description" /></td>
			</tr>
			<tr>
				<td><spring:message code="label.resource.type" />:</td>
				<td><select name="resourceTypeEntity">
						<option value=""><spring:message
								code="label.resource.selectType" /></option>
						<c:forEach items="${listOfResourceType}" var="restype">
							<option value="${restype.typeName}">${restype.typeName}</option>
						</c:forEach>
				</select></td>
			</tr>
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
				<td><select name="tomeIdentifier">
						<option value=""><spring:message
								code="label.resource.registrator.select" />:
						</option>
						<c:forEach items="${tomes}" var="tome">
							<option value="${tome.identifier}">${tome.registrator.lastName}
								${tome.registrator.middleName}${tome.registrator.firstName}
								(номер тому: ${tome.identifier})</option>
						</c:forEach>
				</select></td>
			</tr>
		</table>
		<input type="submit" value="Submit" />
	</form:form>  --%>
	
	<form:form method="POST" action="addrestype" modelAttribute="newrestype">
	
		<div class="caption-res-type">
			Назва: <form:input path="typeName" />

		</div>


<%-- <select name="resourceTypeEntity">
						<option value=""><spring:message
								code="label.resource.selectType" /></option>
						<c:forEach items="${listOfResourceType}" var="restype">
							<option value="${restype.typeName}">${restype.typeName}</option>
						</c:forEach>
				</select> --%>





		<div id="input1" class="clonedInput">
			 Опис параметру:  <form:input path="parameters[0].description" type="text" name="name1" id="name1" />
			Oдиниці вимірювання:  <form:input path="parameters[0].unitName"  type="text" name="name1" id="name1" /> 
			 
			
			<select name="parameters[0].parametersType">
				<option value="" >Виберіть тип параметру</option>
				<option value="linearParameters">Лінійний параметр</option>
				<option value="discreteParameters">Дискретний параметр</option>
			</select>

		</div>

		<%-- <div id="input1" class="clonedInput">
			 Опис параметру:  <form:input path="parameters[1].description" type="text" name="name1" id="name1" />
			Oдиниці вимірювання:  <form:input path="parameters[1].unitName"  type="text" name="name1" id="name1" /> 
			 
			
			<select name="parameters[1].parametersType">
				<option value="" >Виберіть тип параметру</option>
				<option value="linearParameters">Лінійний параметр</option>
				<option value="discreteParameters">Дискретний параметр</option>
			</select>

		</div> --%>













 

 	<div id="mybuttontype">
			<input type="button" id="btnAdd" value="+" /> <input type="button"
				id="btnDel" value="-" />
		</div> 



<br/>
		<div class="button">
			<input type="submit" value="Додати" class="btn btn-success">
		</div> 

</form:form>
	
