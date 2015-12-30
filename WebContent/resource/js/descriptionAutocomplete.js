jQuery(document).ready(function($) {
	
	$('#w-input-search').autocomplete({
		serviceUrl: 'decs',
		paramName: "descTag",
//		delimiter: ",",
//		transformResult: function(response) {
//		    	
//		return {      	
//		  //must convert json to javascript object before process
//		  suggestions: $.map($.parseJSON(response), function(item) {
//		            	
//		      return { value: item.tagName, data: item.id };
//		   })
//		            
//		 };
//		        
//            }
		    
	});
	
} );