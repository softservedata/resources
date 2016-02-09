//$(document).on("click", ".ok", function() {
//	var currentRow = $(this).closest("tr");
//	
//	var login = currentRow.find("td").eq(2).text();
//	 window.location.href="/administrator/users/edit-registrated-user/?login="+login;
//	
////
////	var json = {
////		"login" : currentRow.find("td").eq(2).text()
////	};
//
////	$.ajax({
////		type : "GET",
////		url : "edit-registrated-user",
////		dataType : "text",
////		data : JSON.stringify(json),
////		contentType : 'application/json; charset=utf-8',
////		success : function(data){
////			alert(data);
////		},
////		error : function(data){
////			alert(data);
////		}
////	});
//});