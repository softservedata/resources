jQuery(document).ready(function($) {
	
	$('#example').on('click', '#mybutton', function () {
		var data = oTable.row( $(this).parents('tr') ).data();
	    alert( data[0] +"s salary is:");
	} );
	
	
});


