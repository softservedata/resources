<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- fav-icon -->
    <link rel="Shortcut Icon" href="<c:url value='/resource/img/maple_leaf.ico'/>" type="image/x-icon"/>
    <title><tiles:getAsString name="title"/></title>
    <!-- Bootstrap -->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css">
    <%--<link rel="stylesheet" href="https://cdn.datatables.net/1.10.10/css/jquery.dataTables.min.css">--%>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
    <script src="https://cdn.datatables.net/1.10.10/js/jquery.dataTables.min.js"></script>
    <%--<script src="<c:url value='/resource/js/lib/jquery-1.11.1.js'/>" type="text/javascript"></script>--%>
    <script src="<c:url value='/resource/js/lib/jquery-validate/jquery.validate.js'/>" type="text/javascript"></script>
    <%--<script src="<c:url value='/resource/js/scripts.js'/>"></script>--%>
    <%--<link rel="stylesheet" href="<c:url value='/resource/css/login.css'/>">--%>

    <!-- Our own css -->
    <link rel="stylesheet" href="<c:url value='/resource/css/system.css'/>">

<div class="container">
   	<div id="header">
   		<tiles:insertAttribute name="login_header" />
   	</div>
   	<div id="body">
   		<tiles:insertAttribute name="login_body" />
   	</div>
   	<div id="footer">
   		<tiles:insertAttribute name="login_footer" />
   	</div>
</div>
</html>