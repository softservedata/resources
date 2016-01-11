jQuery(document).ready(function($) {
	
	$('#w-input-search').autocomplete({
		serviceUrl: 'decs',
		paramName: "descTag",	    
	});
	
} );