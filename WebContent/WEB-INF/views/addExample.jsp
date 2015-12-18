<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
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
	<div class="languages">
		<a href="?lang=ua">UA</a>| <a href="?lang=en">ENG</a>| <a
			href="?lang=ru">RUS</a>
	</div>
	<h1>Додати новий тип ресурсу</h1>
	<form method="POST"
		action="http://localhost:8080/resources/registrator/resourcetypes/add"
		name="newRTDTO">
		<div class="caption-res-type">
			Назва: <input type="text" name="typeName"
				placeholder="Введіть назву тут" required>

		</div>


		<div id="input1" class="clonedInput">
			Опис параметру: <input type="text" name="name1" id="name1" />
			Oдиниці вимірювання: <input type="text" name="name1" id="name1" /> <select
				required>
				<option value="" selected>Виберіть тип параметру</option>
				<option value="лінійний">Лінійний параметр</option>
				<option value="дискретний">Дискретний параметр</option>
			</select>

		</div>


		<div id="mybuttontype">
			<input type="button" id="btnAdd" value="+" /> <input type="button"
				id="btnDel" value="-" />
		</div>



		<div class="button">
			<input type="submit" value="Додати">
		</div>
	</form>


</body>
</html>