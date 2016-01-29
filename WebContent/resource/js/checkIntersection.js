/**
 * author Ann
 */
//Ann    
 //   intersection = checkIntersectionAllPoligons(polygon, polygons);

//var polygons = [];
var N = 10;		 //ділимо сторону на N частин.
var tolerance = 0.000000001;

function checkIntersectionAllPoligons(polygon, polygons){
	
	// перетворюємо тип
	/*polygonPath = [];
	for (var j = 0; j < polygon.length; j++) {
        polygonPath.push(new google.maps.LatLng(polygon[j][0], polygon[j][1]));
    }
	var polygonNew = new google.maps.Polygon({
        path: polygonPath, // Координаты
        strokeColor: "#FF0000", // Цвет обводки
        strokeOpacity: 0.8, // Прозрачность обводки
        strokeWeight: 2, // Ширина обводки
        fillColor: "#0000FF", // Цвет заливки
        fillOpacity: 0.3, // Прозрачность заливки
        snapable: true,
        map: map
    }); */
	
	for (var i = 0; i<polygons.length; i++)	{
		//можна буде частину відкинути
		return checkEdgeOfFirstOnSecond(polygon, poligons[i]) || checkEdgeOfFirstOnSecond(poligons[i], poligon);
	}   
}

//перевіряє чи межа першого полігону перетинає другий
function checkEdgeOfFirstOnSecond(polygon1, poligon2){
	var intersection = false;
	var vertices = polygon.getPath();
	var pointBegin;
	var pointEnd;
	for (var j = 0; j < vertices.getLength()-2; j++){
		pointBegin = vertices.getAt(j);
		pointEnd = vertices.getAt(j+1);
		intersection = checkInterseptionLinePoligon(pointBegin, pointEnd, poligon2);
		if (intersection){
			alert("intersection " + intersection);
			return intersection;
		}
	}
	pointBegin = vertices.vertices.getLength()-1;
	pointEnd = vertices.getAt(0);	
	return checkInterseptionLinePoligon(pointBegin, pointEnd, poligon2);;
}

//перевіряє чи відрізок (pointBegin, pointEnd) перетинає полігон
function checkInterseptionLinePoligon(pointBegin, pointEnd, poligon2){
	var pointOnVertices;
	var isInsidePolygon;
	var isOnEdge;
	for (var k=0; k<N; k++) {
		// new google.maps.LatLng(  )
		pointOnVertices = pointBegin*(N-k)/N + pointEnd*k/N;
		//is inside the polygon2
		isInsidePolygon = google.maps.geometry.poly.containsLocation(pointOnVertices, polygon2);
		console.log("isInsidePolygon " + isInsidePolygon);
		// if yes check if it is on edge
		if (isInsidePolygon) {
			isOnEdge = google.maps.geometry.poly.isLocationOnEdge(pointOnVertices, polygon2, tolerance);
			console.log("isOnEdge " + isOnEdge);
			if (!isOnEdge) {
				polygon2.setOptions({fillColor: "#FF0000"});
				return (!isOnEdge);
			}
		}		
	}	
}
