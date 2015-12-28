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
        //markerOptions: {
        //    icon: new google.maps.MarkerImage('http://www.example.com/icon.png')
        //},
        //circleOptions: {
        //    fillColor: '#ffffff',
        //    fillOpacity: 1,
        //    strokeWeight: 5,
        //    clickable: false,
        //    zIndex: 1,
        //    editable: true
        //}
    });

    drawingManager.setMap(map);

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

