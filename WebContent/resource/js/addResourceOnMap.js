var map;
var polygons = [];
var newPolygons = [];
var PS = null;

function getCookie(cname) {
    var name = cname + "=";
    var ca = document.cookie.split(';');
    for (var i = 0; i < ca.length; i++) {
        var c = ca[i];
        while (c.charAt(0) == ' ') c = c.substring(1);
        if (c.indexOf(name) == 0) return c.substring(name.length, c.length);
    }
    return "";
}

function initialize() {
// Reading the cookie of start position
    var lastMapSearchLat = getCookie("LastMapSearchLat");
    var lastMapSearchLng = getCookie("LastMapSearchLng");
    var startPoint;
    if ((lastMapSearchLat.length > 0) && (lastMapSearchLng.length > 0)) {
        var startLat = Number(lastMapSearchLat);
        var startLng = Number(lastMapSearchLng);
        startPoint = new google.maps.LatLng(startLat, startLng);
    }
    else {
        startPoint = new google.maps.LatLng(49.8326679, 23.942196);
    }

    var mapProp = {
        center: startPoint,
        zoom: 13,
        mapTypeId: google.maps.MapTypeId.ROADMAP
    };
    map = new google.maps.Map(document.getElementById("map_canvas"), mapProp);

    //Drawing of the snappable polygon. External plugin
    var polystyle = {
                fillColor: '#008000',
                fillOpacity: 0.4,
                strokeColor: "#006400",
                strokeWeight: 3,
                clickable: false,
                zIndex: 1
    };

    PS = PolySnapper({
        map: map,
        threshold: 20,
        key: 'shift',
        keyRequired: true,
        polygons: polygons,
        polystyle: polystyle,
        //hidePOI: true,
        onEnabled: function(){
            console.log("enabled")
        },
        onDisabled: function(){
            console.log("disabled")
        }
    });


    // Create the search box and link it to the UI element.
    var input = document.getElementById('gmaps-input');
    var button = document.getElementById('gmaps-show-res');
    var drawing = document.getElementById('cp-wrap');
    var searchBox = new google.maps.places.SearchBox(input);
    map.controls[google.maps.ControlPosition.TOP_LEFT].push(input);
    map.controls[google.maps.ControlPosition.TOP_LEFT].push(button);
    map.controls[google.maps.ControlPosition.TOP_LEFT].push(drawing);

    // Bias the SearchBox results towards current map's viewport.
    map.addListener('bounds_changed', function () {
        searchBox.setBounds(map.getBounds());
    });
    var markers = [];
    // Listen for the event fired when the user selects a prediction and retrieve
    // more details for that place.
    searchBox.addListener('places_changed', function () {
        var places = searchBox.getPlaces();

        if (places.length == 0) {
            return;
        }

        // Clear out the old markers.
        markers.forEach(function (marker) {
            marker.setMap(null);
        });
        markers = [];

        // For each place, get the icon, name and location.
        var bounds = new google.maps.LatLngBounds();
        places.forEach(function (place) {
            var icon = {
                url: place.icon,
                size: new google.maps.Size(71, 71),
                origin: new google.maps.Point(0, 0),
                anchor: new google.maps.Point(17, 34),
                scaledSize: new google.maps.Size(25, 25)
            };
// Create a marker for each place.
            markers.push(new google.maps.Marker({
                map: map,
                icon: icon,
                title: place.name,
                position: place.geometry.location
            }));

//Expires date for cookie
            var d = new Date();
            d.setTime(d.getTime() + 7 * 24 * 60 * 60 * 1000);
//Deleting of the old cookie
            document.cookie = "LastMapSearchLat=; expires=Thu, 01 Jan 1970 00:00:00 UTC";
            document.cookie = "LastMapSearchLng=; expires=Thu, 01 Jan 1970 00:00:00 UTC";
//Add new cookie
            document.cookie = "LastMapSearchLat=" + place.geometry.location.lat() + ";expires=" + d.toUTCString();
            document.cookie = "LastMapSearchLng=" + place.geometry.location.lng() + ";expires=" + d.toUTCString();

            if (place.geometry.viewport) {
                // Only geocodes have viewport.
                bounds.union(place.geometry.viewport);
            } else {
                bounds.extend(place.geometry.location);
            }
        });
        map.fitBounds(bounds);
    });

    //google.maps.event.addListener(drawingManager, 'overlaycomplete', function (event) {
    //    drawingManager.setDrawingMode(null);
    //    var polygon = event.overlay;
    //    var vertices = polygon.getPath();
    //    newPolygons.push(polygon);
    //
    //    for (var i = 0; i < vertices.getLength(); i++) {
    //        bounds.extend(vertices.getAt(i));
    //    }
    //
    //});

}

function getResources() {
    var resType = $("#resourcesTypeSelect").val();
    var maxLat = map.getBounds().getNorthEast().lat();
    var minLat = map.getBounds().getSouthWest().lat();
    var maxLng = map.getBounds().getNorthEast().lng();
    var minLng = map.getBounds().getSouthWest().lng();

    if (polygons.length > 0) {
        for (var i = 0; i < polygons.length; i++) {
            polygons[i].setMap(null);
        }
        polygons = [];
    }

    $("#dark_bg").show();
    $.ajax({
        data: {
            "minLat": minLat,
            "maxLat": maxLat,
            "minLng": minLng,
            "maxLng": maxLng,
            "resType": resType
        },
        type: "POST",
        async: false,
        url: baseUrl.toString() + "/registrator/resource/getResourcesByAreaLimits",
        timeout: 20000,
        contentType: "application/x-www-form-urlencoded;charset=UTF-8",
        dataType: 'json',
        success: function (data) {
            for (var i = 0; i < data.length; i++) {

                var polygonPath = [];
                var points = data[i].points;
                for (var j = 0; j < points.length; j++) {
                    polygonPath.push(new google.maps.LatLng(points[j].latitude, points[j].longitude));
                }

                var polygon = new google.maps.Polygon({
                    path: polygonPath, // Координаты
                    strokeColor: "#FF0000", // Цвет обводки
                    strokeOpacity: 0.8, // Прозрачность обводки
                    strokeWeight: 2, // Ширина обводки
                    fillColor: "#0000FF", // Цвет заливки
                    fillOpacity: 0.3, // Прозрачность заливки
                    snapable: true,
                    map: map
                });
                polygons.push(polygon);
            }
            //console.log("Find " + polygons.length + " polygons");
            //$("#dark_bg").hide();
        },
        error: function () {
            $("#dark_bg").hide();
            bootbox.alert(jQuery.i18n.prop('msg.error'));
        }
    });
    //return dfd.done();
}


//Function which checks does the new polygon intersect other polygons
//At the moment we check only do the point of one polygon inside of the other one.

function intersectionCheck(polygon){
    //console.log("Intersection check started!");

    $("#dark_bg").show();

    var intersection = false;
    var point;
    var i, j, k;
    var isWithinPolygon;
    var vertices;
    var sameVertice = false;
    var verticesPoly;
    var resType = $("#resourcesTypeSelect").val();
    var bounds = new google.maps.LatLngBounds();

    if (resType == "") {
        $("#resourcesTypeSelect").focus();
        bootbox.alert(jQuery.i18n.prop('msg.selectType'));
        $("#dark_bg").hide();
        return true;
    }

    vertices = polygon.getPath();
    for (i = 0; i < vertices.getLength(); i++) {
        bounds.extend(vertices.getAt(i));
    }

    map.fitBounds(bounds);

    getResources();   

    //console.log("Polygons found: "+polygons.length);

    for (i = 0; i < vertices.getLength(); i++) {
        point = vertices.getAt(i);
        for (j = 0; j < polygons.length; j++) {
            isWithinPolygon = google.maps.geometry.poly.containsLocation(point, polygons[j]);
            if (isWithinPolygon) {
                //console.log("Possible intersection at point: "+ i);
                sameVertice = false;
                verticesPoly = polygons[j].getPath();
                for (k = 0; k < verticesPoly.getLength(); k++) {
                    //console.log("Point: "+point+" compare: "+ verticesPoly.getAt(k));
                    if ((point.lat() == verticesPoly.getAt(k).lat()) &&
                        (point.lng() == verticesPoly.getAt(k).lng()) &&
                        (!sameVertice)) {
                        //console.log("Same point!");
                        sameVertice = true;
                    }
                }
                if (!sameVertice) {
                    console.log("Intersection found");
                    intersection = true;
                    polygons[j].setOptions({fillColor: "#FF0000"});
                }

            }
        }
    }

    for (i = 0; i < polygons.length; i++) {
        verticesPoly = polygons[i].getPath();
        for (j = 0; j < verticesPoly.getLength(); j++) {
            point = verticesPoly.getAt(j);
            isWithinPolygon = google.maps.geometry.poly.containsLocation(point, polygon);
            if (isWithinPolygon) {
                sameVertice = false;
                for (k = 0; k < vertices.getLength(); k++) {
                    if ((point.lat() == vertices.getAt(k).lat()) &&
                        (point.lng() == vertices.getAt(k).lng()) &&
                        (!sameVertice)) {
                        sameVertice = true;
                    }
                }
                if (!sameVertice) {
                    intersection = true;
                    polygons[i].setOptions({fillColor: "#FF0000"});
                }
            }

        }
    }

    if (!intersection) {
        newPolygons.push(polygon);
        polygon.setEditable(false);
    }
    else {
        bootbox.alert(jQuery.i18n.prop('msg.resoursesIntersect'));
    }
    $("#dark_bg").hide();
    return intersection;
}

$("#gmaps-show-res").click(function () {

    var resType = $("#resourcesTypeSelect").val();
    if (resType == "") {
        $("#resourcesTypeSelect").focus();
        bootbox.alert(jQuery.i18n.prop('msg.selectType'));
        return false;
    }
    $("#dark_bg").show();
    getResources();
    $("#dark_bg").hide();
    //console.log("Polygons: " + polygons.length)
});

//Add coordinates from map and verify them
$("#addPointsFromMap").click(function () {

    if (newPolygons.length > 0) {
        $("#dark_bg").show();
        var area = new Number();
        var perimeter = new Number();

        //If user entered the correct polygon and it doesn't intersect with existing polygons
        //we add points coordinates to inputs and deny to edit entered polygon.
            for (var i = 0; i < newPolygons.length; i++) {

                var pointsArray = newPolygons[i].getPath().getArray();
                for (var j = 0; j < pointsArray.length; j++) {
                    var delimiter = String(pointsArray[j]).indexOf(",");
                    var end = String(pointsArray[j]).length;
                    var latitude = Number(String(pointsArray[j]).slice(1, delimiter));
                    var longitude = Number(String(pointsArray[j]).slice(delimiter + 1, end - 1));

                    var latitudeDegrees = Math.floor(latitude);
                    var latitudeMinutes = Math.floor((latitude - latitudeDegrees) * 60);
                    var latitudeSeconds = ((latitude - latitudeDegrees) * 60 - latitudeMinutes) * 60;
                    var longitudeDegrees = Math.floor(longitude);
                    var longitudeMinutes = Math.floor((longitude - longitudeDegrees) * 60);
                    var longitudeSeconds = ((longitude - longitudeDegrees) * 60 - longitudeMinutes) * 60;

                    addNewPoint(i,
                        latitudeDegrees, latitudeMinutes, latitudeSeconds,
                        longitudeDegrees, longitudeMinutes, longitudeSeconds);
                }
                newPolygons[i].setOptions({fillColor: "#003400"});

                //Calculation of area and perimeter of all new polygons.
                area += Number(google.maps.geometry.spherical.computeArea(newPolygons[i].getPath()));
                perimeter += Number(google.maps.geometry.spherical.computeLength(newPolygons[i].getPath()));

                //Coordinates added, if we want we can delete the polygon from the array.
                //newPolygons.splice(i,1);
            }

            //Adding area and perimeter values to input fields
            $("input").each(function () {
                if ($(this).val() == "площа") {
                    $(this).siblings("div").children("input:first").val((area / 10000).toFixed(5));
                }
                if ($(this).val() == "периметер") {
                    $(this).siblings("div").children("input:first").val((perimeter).toFixed(1));
                }
            });
        //We make the link "Add polygon" inactive
        $(".toggle a").addClass("inactiveLink");
        $("#dark_bg").hide();
    }
    else {
        bootbox.alert(jQuery.i18n.prop('msg.enterPolygon'));
    }
});

google.maps.event.addDomListener(window, 'load', initialize);

//importScripts(baseUrl.toString()+"/resources/js/ukraineCoord.js");

$("#cp-wrap").on("click", "a", function(){

    var inactive = $(this).hasClass("inactiveLink");

    if(!inactive) {

        var action = $(this).data("action");

        if (action == 'new') {
            PS.enable();
            $(this).closest(".toggle").removeClass("active");
            $(this).closest(".toggle").siblings("span").addClass("active");
        }
        else if (action == 'save') {
            if(PS.polygon().getPath().length > 2) {
                var intersection = intersectionCheck(PS.polygon());
                if (!intersection) {
                    PS.save();
                    $(this).closest(".toggle").removeClass("active");
                    $(this).closest(".toggle").siblings("span").addClass("active");
                }
            }
            else {
                bootbox.alert(jQuery.i18n.prop('msg.enterPolygon'));
            }
        }
        else {
            PS.disable();
            $(this).closest(".toggle").removeClass("active");
            $(this).closest(".toggle").siblings("span").addClass("active");
        }
    }
    else {
        bootbox.alert("Точки вже додано. Додавання нових полігонів заборонено.");
    }

});

$(document).on("click", "#mapManual", function(){
    var spoiler = $(this).siblings(".spoiler");
    var icon = $(this).find(".glyphicon");
    if (!($(this).hasClass("active"))) {
        spoiler.slideDown(200);
        $(this).addClass("active");
        icon.each(function(){
            if($(this).hasClass("hidden")) {
                $(this).removeClass("hidden");
            }
            else {
                $(this).addClass("hidden");
            }
        });
    }
    else {
        spoiler.slideUp(200);
        $(this).removeClass("active");
        icon.each(function(){
            if($(this).hasClass("hidden")) {
                $(this).removeClass("hidden");
            }
            else {
                $(this).addClass("hidden");
            }
        });
    }
});

