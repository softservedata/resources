<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<script src="./libs/jquery/1.10.1/jquery.min.js"></script>
<title>Resource Manipulation</title>
</head>
<body>

<table id="userBase" class="data" border="1" width="100%" cellpadding="2" cellspacing="4">
    <tr>
        <th>Login</th>
        <th>First Name</th>
        <th>Last Name</th>
        <th>Birthday</th>
        <th>Role</th>
        <th>Actions</th>
    </tr>
    <c:forEach items="${userList}" var="user">
        <tr>
            <td>${user.login}</td>
            <td>${user.firstname}</td>
            <td>${user.lastname}</td>
            <td>${user.birthday}</td>
            <td><c:if test="${user.roleid==1}">Admin</c:if>
                <c:if test="${user.roleid==2}">User</c:if></td>
            <td><a href="edit/${user.login}">Edit   </a>
                <a href="${pageContext.request.contextPath}/delete/${user.login}.json">Delete</a>
        </tr>
    </c:forEach>
</table>
</body>
</html>