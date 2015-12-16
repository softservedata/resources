<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>ResourcesType</title>
</head>
<body>



<h1>This is list of resource types</h1>

<jsp:useBean id="restype" class="org.registrator.community.entity.ResourceType"/>
<jsp:setProperty property="typeName" name="restype"/>

You added new resource type: 
<jsp:getProperty property="typeName" name="restype"/>
<%-- <c:forEach items="${listOfResourceType}" var="rt"  >
<p><c:out value = "${rt.typeName}"/></p>
</c:forEach> --%>


<!-- <a href="edit-book.html?isbn=$(book.isbn)">edit</a>
<a href="delete-book.html?isbn=$(book.isbn)">delete</a>
 -->
<!-- <a href="create-book.html?isbn=$(book.isbn)">create</a> -->


<!-- <a href="book.htm">Add new resourceType</a> -->


</body>
</html>