<%@ page language="java" contentType="text/html; charset=utf8"
pageEncoding="utf8"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">
<head>
<title>Реєстратор</title>
<spring:url value="/resource/css/normalize.css" var="normalizeCss" />
<spring:url value="/resource/css/addRes.css" var="regCss" />
<spring:url
	value="http://ajax.googleapis.com/ajax/libs/jquery/1.3/jquery.min.js"
	var="jqueryJs" />
<spring:url value="/resource/js/add.js" var="addJs" />

<link href="${normalizeCss}" rel="stylesheet" />
<link href="${regCss}" rel="stylesheet" />
<script src="${jqueryJs}"></script>
<script src="${addJs}"></script>
</head>
<body>
	<form:form method="post" action="add" commandName="newRTDTO">
<%-- 	<form method="POST"
		action="http://localhost:8080/resources/registrator/resourcetypes/add"
		name="newRTDTO"> --%>
		<div class="caption-res-type">
			Назва: <form:input path="typeName" />

		</div>


		<div id="input1" class="clonedInput">
			Опис параметру:  <form:input path="description" type="text" name="name1" id="name1" />
			Oдиниці вимірювання:  <form:input path="unitName"  type="text" name="name1" id="name1" /> <select
				>
				<option value="" selected>Виберіть тип параметру</option>
				<option value="linearParameters.">Лінійний параметр</option>
				<option value="discreteParameters">Дискретний параметр</option>
			</select>

		</div>


		<div id="mybuttontype">
			<input type="button" id="btnAdd" value="+" /> <input type="button"
				id="btnDel" value="-" />
		</div>



		<div class="button">
			<input type="submit" value="Додати">
		</div>

</form:form>

</body>
</html>