<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c' %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<c:set var="req" value="${pageContext.request}"/>
<c:set var="url">${req.requestURL}</c:set>
<c:set var="base" value="${fn:substring(url, 0, fn:length(url) - fn:length(req.requestURI))}${req.contextPath}/"/>

<link rel="stylesheet" type="text/css" href="${base}resource/css/cssload.css">
<script type="text/javascript" src="${base}/resource/js/showAllResources.js"></script>

<div>
    <c:if test="${not empty resourceTypes}">
        <div style="padding-bottom: 15px;">
            <label class="">Оберіть підклас об'єкту: </label>
            <select id="resourcesTypeSelect" class="form-control" style="width:auto; display: inline">
                <c:forEach items="${resourceTypes}" var="resourceType">
                    <option value="${resourceType.typeId}">${resourceType.typeName}</option>
                </c:forEach>
            </select>
        </div>
        <div id="searchParameters" class="container"></div>
        <br>
        <br>
        <div id="table" class="container"></div>

        <%--AJAX Loader on the dark display--%>
        <div id="dark_bg">
            <div class="windows8">
                <div class="wBall" id="wBall_1">
                    <div class="wInnerBall"></div>
                </div>
                <div class="wBall" id="wBall_2">
                    <div class="wInnerBall"></div>
                </div>
                <div class="wBall" id="wBall_3">
                    <div class="wInnerBall"></div>
                </div>
                <div class="wBall" id="wBall_4">
                    <div class="wInnerBall"></div>
                </div>
                <div class="wBall" id="wBall_5">
                    <div class="wInnerBall"></div>
                </div>
            </div>
        </div>
    </c:if>

</div>