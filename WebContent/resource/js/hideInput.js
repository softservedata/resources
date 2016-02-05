$(document).on("click", "#ok", function() {
	var json = {
		"login" : $("#loginNumber").val()
	};
	$.ajax({
		type : "post",
		url : "edit-registrated-user",
		dataType : "text",
		data : JSON.stringify(json),
		contentType : 'application/json; charset=utf-8',
	});
});