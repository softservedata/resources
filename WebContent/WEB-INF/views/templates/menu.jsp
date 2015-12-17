<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<nav class="navbar navbar-default" id="menubar">
    <div class="container-fluid">

        <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
            <ul class="nav navbar-nav">
                <li><a href="/resources"><spring:message code="label.menu.resources" /></a></li>
                <li><a href="/registrator/resourcetypes/show-res-types"><spring:message code="label.menu.resourcesTypes" /></a></li>
                <li><a href="/administrator/users/get-all-users"><spring:message code="label.menu.users" /></a></li>
                <li><a href="/inquiries"><spring:message code="label.menu.inquiries" /></a></li>
            </ul>
        </div>

    </div>
</nav>