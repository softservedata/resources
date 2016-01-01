$(function() {
	$(Document).on("click","#valid",
					function(event) {
		alert("checking");
})
.ajax({
	url : $(event.target).attr("href"),

	error : function() {
		alert('checking');
	}

});
		
		
		
});
