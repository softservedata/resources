var map;
var markers = [];

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

    var drawingManager = new google.maps.drawing.DrawingManager({
        drawingControl: true,
        drawingControlOptions: {
            position: google.maps.ControlPosition.TOP_CENTER,
            drawingModes: [google.maps.drawing.OverlayType.MARKER]
        }
    });

    drawingManager.setMap(map);

    // Create the search box and link it to the UI element.
    var input = document.getElementById('gmaps-input');
    var searchBox = new google.maps.places.SearchBox(input);
    map.controls[google.maps.ControlPosition.TOP_LEFT].push(input);

    // Bias the SearchBox results towards current map's viewport.
    map.addListener('bounds_changed', function () {
        searchBox.setBounds(map.getBounds());
    });

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

        // Clear out the old polygons.
        polygons.forEach(function(poligon){
            poligon.setMap(null);
        });
        polygons = [];

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


    google.maps.event.addListener(drawingManager, 'markercomplete', function(marker) {

        // Clear out the old markers.
        markers.forEach(function (marker) {
            marker.setMap(null);
        });
        markers = [];

        markers.push(marker);

        var latitude = marker.getPosition().lat();
        var longitude = marker.getPosition().lng();

        var latitudeDegrees = Math.floor(latitude);
        var latitudeMinutes = Math.floor((latitude - latitudeDegrees)*60);
        var latitudeSeconds = (((latitude-latitudeDegrees)*60 - latitudeMinutes)*60).toFixed(5);
        var longitudeDegrees = Math.floor(longitude);
        var longitudeMinutes = Math.floor((longitude - longitudeDegrees)*60);
        var longitudeSeconds = (((longitude-longitudeDegrees)*60 - longitudeMinutes)*60).toFixed(5);

        $(".latitudeDegrees").val(latitudeDegrees);
        $(".latitudeMinutes").val(latitudeMinutes);
        $(".latitudeSeconds").val(latitudeSeconds);
        $(".longitudeDegrees").val(longitudeDegrees);
        $(".longitudeMinutes").val(longitudeMinutes);
        $(".longitudeSeconds").val(longitudeSeconds);

        searchOnMap(marker.getPosition(), marker);
    });
}

$("#searchOnMapButton").click( function() {
    var latitudeDegrees = Number($(".latitudeDegrees").val());
    var latitudeMinutes = Number($(".latitudeMinutes").val());
    var latitudeSeconds = Number($(".latitudeSeconds").val());

    var longitudeDegrees = Number($(".longitudeDegrees").val());
    var longitudeMinutes = Number($(".longitudeMinutes").val());
    var longitudeSeconds = Number($(".longitudeSeconds").val());

    var searchLat = latitudeDegrees + latitudeMinutes/60 + latitudeSeconds/3600;
    var searchLng = longitudeDegrees + longitudeMinutes/60 + longitudeSeconds/3600;

    var myLatLng = new google.maps.LatLng(searchLat, searchLng);

    // Clear out the old markers.
    markers.forEach(function (marker) {
        marker.setMap(null);
    });
    markers = [];

    var marker = new google.maps.Marker({
        position: myLatLng,
        map: map
    });

    markers.push(marker);

    searchOnMap(myLatLng, marker);
});

var polygons = [];
function searchOnMap(latLng, marker) {

    map.setCenter(latLng);
    map.setZoom(13);

    var lat = latLng.lat();
    var lng = latLng.lng();

    if (polygons.length > 0) {
        for (var i = 0; i < polygons.length; i++) {
            polygons[i].setMap(null);
        }
        polygons = [];
    }

    $("#dark_bg").show();
    $.ajax({
        data: {
            "lat": lat,
            "lng": lng
        },
        type: "POST",
        url: baseUrl.toString() + "/registrator/resource/getResourcesByPoint",
        timeout: 20000,
        contentType: "application/x-www-form-urlencoded;charset=UTF-8",
        dataType: 'json',
        success: function (data) {
            //Function add additional 0 in the beginning of string to the @max length
            function pad (str, max) {
                str = str.toString();
                return str.length < max ? pad("0" + str, max) : str;
            }

            function changeFillColor(color) {
                color = color.substr(1);
                var number = parseInt(color,16);
                number +=100000;
                color = number.toString(16);
                color = pad(color,6);
                console.log("New color: #"+color);
                return "#"+color;
            }

            var infoWindowContent = "<table id='infowindow_table'><tr><th>Опис</th><th>Підклас</th><th></th></tr>";
            var contentString ="";

            var color = "#000000";
            var resTypes = [];

            var bounds = new google.maps.LatLngBounds();

            for (var i = 0; i < data.length; i++) {

                var polygonPath = [];
                var points = data[i].points;
                for (var j = 0; j < points.length; j++) {
                    var myLatLng = new google.maps.LatLng(points[j].latitude, points[j].longitude);
                    polygonPath.push(myLatLng);
                    bounds.extend(myLatLng);
                }

                //Changing fill color depending on resource type
                if ($.inArray(data[i].resourceType, resTypes) == (-1)) {
                    resTypes.push(data[i].resourceType);
                    color = changeFillColor(color);
                }

                var polygon = new google.maps.Polygon({
                    path: polygonPath, // Координаты
                    strokeColor: "#FF0000", // Цвет обводки
                    strokeOpacity: 0.8, // Прозрачность обводки
                    strokeWeight: 2, // Ширина обводки
                    fillColor: color, // Цвет заливки
                    fillOpacity: 0.4 // Прозрачность заливки
                });
                polygons.push(polygon);

                var isWithinPolygon = google.maps.geometry.poly.containsLocation(latLng, polygon);
                if(isWithinPolygon) {
                    contentString += "<tr>"+
                        "<td>"+ data[i].resourceDescription +"</td>"+
                        "<td>"+ data[i].resourceType +"</td>"+
                        "<td><a href='"+baseUrl.toString() + "/registrator/resource/get/"+data[i].identifier+"'><i>Детальніше</i></a> </td>"+
                        "</tr>";
                    polygon.setMap(map);
                }
            }



            if (contentString.length >0) {
                infoWindowContent +=contentString+"</table>";
                //Zoom map to show all resources, which displayed
                map.fitBounds(bounds);
            }
            else {
                infoWindowContent = "<div>В цій точці ще нема зареєстрованих ресурсів</div>"
            }

            var infowindow = new google.maps.InfoWindow({
                content: infoWindowContent
            });

            infowindow.open(map, marker);

            $("#dark_bg").hide();
        },
        error: function() {
            $("#dark_bg").hide();
            alert("При запиті до серверу виникла помилка, спробуйте ще раз через кілька хвилин.");
        }
    });
}
google.maps.event.addDomListener(window, 'load', initialize);
