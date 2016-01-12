$(document).on('change', '#userStatusId', function(event) {
	var json = {
		"status" : $(this).val(),
		"login" : $("#login").text()
	};

	$.ajax({
		type : "POST",
		url : "get-all-inactive-users",
		dataType : "text",
		data : JSON.stringify(json),
		contentType : 'application/json; charset=utf-8',

		success : function() {
			var tr = $(event.target).closest("tr");
			tr.css("background-color", "#000000");
			tr.fadeIn(1000).fadeOut(200, function() {
				tr.remove();
			})
		}

	});

	event.preventDefault();

});
