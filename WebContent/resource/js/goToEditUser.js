function goToURL(url){
	location.href = url;
}

$('#example tbody').on( 'click', '#action', function () {
			alert( "alert" );
    } );


$(document).ready(function () {
	$('#example tbody').on( 'click', '#action', function () {
			alert( "alert" );
//        var data = table.row( $(this).parents('tr') ).data();
//        alert( data[0] +"'s salary is: "+ data[ 5 ] );
 //       url='';
 //       goToURL(url);
    } );
});