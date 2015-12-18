<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<title>Реєстратор</title>

</head>

<body>
	<table class="layout">
		<caption>Опис вибраного типу ресурсу:</caption>
		<thead>
			<tr>
				<th>ID</th>
				<th>Name</th>
				<th>Discrete parameters</th>
				<th>Linear parameters</th>

			</tr>
		</thead>

		<tbody>
			<c:out value="${onewResType.typeId}"/>
			<c:out value="${onewResType.typeName}"/>
			<c:forEach items="${onewResType.discreteParameters}" var="dis_param">

				<div class="block">
					<div>опис: ${dis_param.description}</div>
					<div>ом: ${dis_param.unitName}</div>
				</div>

			</c:forEach>

			<c:forEach items="${onewResType.linearParameters}" var="lin_param">
				<div class="block">
					<div>опис: ${lin_param.description}</div>
					<div>ОМ: ${lin_param.unitName}</div>
				</div>

			</c:forEach>

		</tbody>
	</table>


</body>
</html>