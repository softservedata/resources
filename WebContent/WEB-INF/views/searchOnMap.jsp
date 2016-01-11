<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c' %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<c:set var="req" value="${pageContext.request}"/>
<c:set var="url">${req.requestURL}</c:set>
<c:set var="base" value="${fn:substring(url, 0, fn:length(url) - fn:length(req.requestURI))}${req.contextPath}/"/>

<link rel="stylesheet" type="text/css" href="${base}resource/css/cssload.css">

<div class="container" style="margin-bottom: 25px; line-height: 28px; vertical-align: top;">
    <h4>Пошук за координатою</h4>

    <p>
        Введіть координату, або поставте маркер на мапі:
    </p>

    <div class="col-sm-5" style="padding-bottom: 5px;">
        <label class="col-sm-3" style="padding-top: 6px; margin: 0;">Широта: </label>
        <input type="text" class="latitudeDegrees form-control" placeholder="Градуси"
               style="width: 90px; display: inline-block;"> °
        <input type="text" class="latitudeMinutes form-control" placeholder="Мінути"
               style="width: 75px;display: inline-block;"> '
        <input type="text" class="latitudeSeconds form-control" placeholder="Секунди"
               style="width: 125px;display: inline-block;"> "
    </div>
    <div class="col-sm-5" style="padding-bottom: 5px;">
        <label class="col-sm-3" style="padding-top: 6px; margin: 0;">Довгота: </label>
        <input type="text" class="longitudeDegrees form-control" placeholder="Градуси"
               style="width: 90px; display: inline-block;"> °
        <input type="text" class="longitudeMinutes form-control" placeholder="Мінути"
               style="width: 75px;display: inline-block;"> '
        <input type="text" class="longitudeSeconds form-control" placeholder="Секунди"
               style="width: 125px;display: inline-block;"> "
    </div>
    <div class="col-sm-2">
        <button id="searchOnMapButton" class="btn btn-success">Пошук</button>
    </div>
</div>
<div id="map_canvas" class="container" style="height: 700px;"></div>

<%--Scripts for Google Map--%>
<p>
    <input id="gmaps-input" class="controls gmap-input"
           style="width: 300px;" type="text"
           placeholder="Пошук на мапі">
</p>

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

<script
        src="http://maps.googleapis.com/maps/api/js?libraries=drawing,places,geometry&sensor=false"></script>
<script type="text/javascript"
        src="${base}resource/js/searchOnMap.js"></script>