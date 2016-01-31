<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>

<script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>

<nav class="navbar navbar-default" id="menubaradmin">
    <div class="container-fluid">

        <div class="collapse navbar-collapse">
            <ul class="nav navbar-nav">
                <li><a href="<c:url value='/'/>" class="glyphicon glyphicon-home"></a></li>
                 <li class="dropdown"><a href="#" class="not-active"><spring:message code="label.user.pagename" /></a>
                    <ul class="dropdown_menu">
                         <li><a href="<c:url value='/administrator/users/get-all-users'/>">
                             <spring:message code="label.registrated.pagename" /></a></li>
                          <li><a href="<c:url value='/administrator/users/get-all-inactive-users'/>">
                              <spring:message code="label.inactive.pagename" /></a></li>
                    </ul>
                </li>
                <li><a href="<c:url value='/administrator/users/settings'/>">
                    <spring:message code="label.admin.settings"/></a></li>
                <li><a href="<c:url value='/administrator/users/search'/>" class="glyphicon glyphicon-search"></a></li>
            </ul>
        </div>
    </div>
</nav>