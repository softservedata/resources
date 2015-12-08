<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="en">
<head>
<title>Реєстратор</title>
</head>
<body>
			<h1>Реєстр ресурсів України</h1>
			<a  href="#" role="button">Перейти на нову версію</a>
			<form method="POST" action="<c:url value="/j_spring_security_check" />">
<table>
    <tr>
        <td align="right">Логін</td>
        <td><input type="text" name="username" /></td>
    </tr>
    <tr>
        <td align="right">Пароль</td>
        <td><input type="password" name="password" /></td>
    </tr>
    <tr>
        <td align="right">Запамятати мене</td>
        <td><input type="checkbox"  name="_spring_security_remember_me" /></td>
    </tr>
</table>
</form>	
</body>
</html>