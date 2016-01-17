<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<% session.setAttribute("selectedTomeIdentifier", "ivan"); %>

<div style="text-align: center;">
    <h4>
        <!-- <spring:message code="label.inquiry.output.pagename" /> -->
        Запит на внесення ресурсу
    </h4>
</div>
<c:url value='/registrator/resource/addresource' var="theAction"/>
<form:form method="GET" action="${theAction}">
    <div>
        <spring:message code="label.resource.registrator"/>
        (<spring:message code="label.resource.tome"/>):
        <br/>
        <select id="tomeIdentifier" name="selectedTomeIdentifier" onchange="this.form.submit();">
            <option value="selectedTomeIdentifier"><spring:message
                    code="label.resource.registrator.select"/>:
            </option>
            <c:forEach items="${tomes}" var="tome">
                <option value="${tome.tomeIdentifier}">

                        ${tome.registratorLastName} ${tome.registratorFirstName}
                        ${tome.registratorMiddleName} (номер тому: ${tome.tomeIdentifier})
                </option>
            </c:forEach>
        </select>
    </div>


</form:form>

<!--
<a href="http://localhost:8080/resources/registrator/resource/addresource"
class="btn btn-success" role="button"> Підтвердити </a>

<a href="http://localhost:8080/resources/registrator/resource/addresource">Заповнити ресурс</a>
-->


<!--
<div>
<spring:message code="label.resource.registrator"/>
(<spring:message code="label.resource.tome"/>):
</div>
<br/>
<div>

<c:forEach items="${tomes}" var="tome">
    <a href="http://localhost:8080/resources/registrator/resource/addresource/${tome.tomeIdentifier}">
    ${tome.registratorLastName} ${tome.registratorFirstName}
    ${tome.registratorMiddleName} (номер тому: ${tome.tomeIdentifier})</a>
    <br/>
</c:forEach>

</div>
-->
			
		