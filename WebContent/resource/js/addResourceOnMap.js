var map;

function initialize() {
    var mapProp = {
        center: new google.maps.LatLng(49.8326679,23.942196),
        zoom: 13,
        mapTypeId: google.maps.MapTypeId.ROADMAP
    };
    map = new google.maps.Map(document.getElementById("map_canvas"), mapProp);

    var drawingManager = new google.maps.drawing.DrawingManager({
        drawingControl: true,
        drawingControlOptions: {
            position: google.maps.ControlPosition.TOP_CENTER,
            drawingModes: [google.maps.drawing.OverlayType.POLYGON]
        },
        //drawingMode: google.maps.drawing.OverlayType.POLYGON,
        polygonOptions: {
            fillColor: '#008000',
            fillOpacity: 0.4,
            strokeColor: "#006400",
            strokeWeight: 3,
            clickable: false,
            zIndex: 1,
            editable: true
        }
    });

    drawingManager.setMap(map);

    // Create the search box and link it to the UI element.
    var input = document.getElementById('gmaps-input');
    var searchBox = new google.maps.places.SearchBox(input);
    map.controls[google.maps.ControlPosition.TOP_LEFT].push(input);

    // Bias the SearchBox results towards current map's viewport.
    map.addListener('bounds_changed', function() {
        searchBox.setBounds(map.getBounds());
    });
    var markers = [];
    // Listen for the event fired when the user selects a prediction and retrieve
    // more details for that place.
    searchBox.addListener('places_changed', function() {
        var places = searchBox.getPlaces();

        if (places.length == 0) {
            return;
        }

        // Clear out the old markers.
        markers.forEach(function(marker) {
            marker.setMap(null);
        });
        markers = [];

        // For each place, get the icon, name and location.
        var bounds = new google.maps.LatLngBounds();
        places.forEach(function(place) {
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

            if (place.geometry.viewport) {
                // Only geocodes have viewport.
                bounds.union(place.geometry.viewport);
            } else {
                bounds.extend(place.geometry.location);
            }
        });
        map.fitBounds(bounds);
    });


    var poligonNumber = 0;

    google.maps.event.addListener(drawingManager, 'overlaycomplete', function(event) {
        var pointsArray = event.overlay.getPath().getArray();
        for(var i=0;i<pointsArray.length;i++) {
            var delimiter = String(pointsArray[i]).indexOf(",");
            var end = String(pointsArray[i]).length;
            var latitude = Number(String(pointsArray[i]).slice(1,delimiter));
            var longitude = Number(String(pointsArray[i]).slice(delimiter+1, end-1));

            var latitudeDegrees = Math.floor(latitude);
            var latitudeMinutes = Math.floor((latitude - latitudeDegrees)*60);
            var latitudeSeconds = ((latitude-latitudeDegrees)*60 - latitudeMinutes)*60;
            var longitudeDegrees = Math.floor(longitude);
            var longitudeMinutes = Math.floor((longitude - longitudeDegrees)*60);
            var longitudeSeconds = ((longitude-longitudeDegrees)*60 - longitudeMinutes)*60;

            addNewPoint(poligonNumber,
                latitudeDegrees, latitudeMinutes, latitudeSeconds,
                longitudeDegrees, longitudeMinutes,longitudeSeconds);
        }

        alert("Периметр виділеної області: " + (google.maps.geometry.spherical.computeLength(event.overlay.getPath())).toFixed(1) + " м\n" +
            "Площа виділеної області: " + (google.maps.geometry.spherical.computeArea(event.overlay.getPath())/10000).toFixed(5) + " га");

        poligonNumber++;
    });
}

google.maps.event.addDomListener(window, 'load', initialize);

