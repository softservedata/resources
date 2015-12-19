<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<c:set var="req" value="${pageContext.request}" />
<c:set var="url">${req.requestURL}</c:set>
<c:set var="base" value="${fn:substring(url, 0, fn:length(url) - fn:length(req.requestURI))}${req.contextPath}/" />

<nav class="navbar navbar-default" id="menubar">
    <div class="container-fluid">

        <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
            <ul class="nav navbar-nav">
                <li><a href="${base}" class="glyphicon glyphicon-home"></a></li>
                <li><a href="${base}resources"><spring:message code="label.menu.resources" /></a></li>
                <li><a href="${base}registrator/resourcetypes/show-res-types"><spring:message code="label.menu.resourcesTypes" /></a></li>
                <li><a href="${base}administrator/users/get-all-users"><spring:message code="label.menu.users" /></a></li>
                <li><a href="${base}inquiries"><spring:message code="label.menu.inquiries" /></a></li>
                <li><a href="${base}registrator/resource/addresource"><spring:message code="label.menu.addnewresource"/></a>
            </ul>
        </div>

    </div>
</nav>