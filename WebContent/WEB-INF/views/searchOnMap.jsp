<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c' %>

<link rel="stylesheet" type="text/css" href="<c:url value='/resource/css/cssload.css'/>">
<link rel="stylesheet" type="text/css" href="<c:url value='/resource/css/searchOnMap.css'/>">

<div class="container" style="margin-bottom: 25px; line-height: 28px; vertical-align: top;">
    <div class="col-sm-12 controls">
        <span id="searchByPointButton" class="btn btn-primary toggle-button col-sm-3"><h4>Пошук за координатою</h4></span>
        <span id="searchByAreaButton" class="btn btn-primary toggle-button col-sm-3"><h4>Пошук по області</h4></span>
    </div>
    <div id="searchByPointDiv" class="col-sm-12 searchDiv">
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
    <div id="searchByAreaDiv" class="col-sm-12 searchDiv">
        <p>
            Введіть координати протилежних вершин прямокутника, або оберіть область на мапі:
        </p>
        <div id="first_point" >
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
        </div>
        <div id="second_point">
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
        </div>
        <div class="col-sm-2">
            <button id="searchOnMapButton_area" class="btn btn-success">Пошук</button>
        </div>
    </div>
</div>
<div id="resTypeFilter" class="col-sm-12">

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
        src="<c:url value='/resource/js/searchOnMap.js'/>"></script>