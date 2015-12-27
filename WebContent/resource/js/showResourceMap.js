
    var map;
    var myLatlng;
    var centerlat = $("#map_canvas").attr("centerlat");
    var centerlng = $("#map_canvas").attr("centerlng");

    function initialize() {
        myLatlng = new google.maps.LatLng(centerlat, centerlng);
        var mapProp = {
            center: myLatlng,
            zoom: 9,
            mapTypeId: google.maps.MapTypeId.ROADMAP
        };
        map = new google.maps.Map(document.getElementById("map_canvas"), mapProp);

        var polygonCoords = [];

        $(".coordinates").each(function () {
            var latitudeDegrees = Number($(this).find(".latitudeDegrees").html());
            var latitudeMinutes = Number($(this).find(".latitudeMinutes").html());
            var latitudeSeconds = Number($(this).find(".latitudeSeconds").html());
            var longitudeDegrees = Number($(this).find(".longitudeDegrees").html());
            var longitudeMinutes = Number($(this).find(".longitudeMinutes").html());
            var longitudeSeconds = Number($(this).find(".longitudeSeconds").html());

            var lat = latitudeDegrees + latitudeMinutes / 60 + latitudeSeconds / 3600;
            var lng = longitudeDegrees + longitudeMinutes / 60 + longitudeSeconds / 3600;

            polygonCoords.push(new google.maps.LatLng(lat, lng));
        });
        polygonCoords.push(new google.maps.LatLng(centerlat, centerlng));

        var polygon = new google.maps.Polygon({
            path: polygonCoords, // Координаты
            strokeColor: "#FF0000", // Цвет обводки
            strokeOpacity: 0.8, // Прозрачность обводки
            strokeWeight: 2, // Ширина обводки
            fillColor: "#0000FF", // Цвет заливки
            fillOpacity: 0.4 // Прозрачность заливки
        });

        polygon.setMap(map);
    }

    google.maps.event.addDomListener(window, 'load', initialize);