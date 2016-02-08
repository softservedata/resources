jQuery(document).ready(function($) {
	
	$('#example').on('click', '#edit', function () {
		var data = oTable.row( $(this).parents('tr') ).data();
	    alert( data[0] +"s salary is:");
	    login = data[2];
	    window.location.href="edit-registrated-user/?login="+login;
	} );	
});



