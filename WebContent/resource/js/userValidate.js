$(document).ready(function() {
	$("#editWindow").validate({
		rules : {
			firstName : {
				required : true
			},
			lastName : {
				required : true
			},
			email : {
				required : true,
				email : true
			},
			"address.postcode" : {
				required : true
			},
			"passport.seria" : {
				required : true
			},
			"passport.number" : {
				required : true
			},
		},
		messages : {
			firstName : {
				required : "Поле є обов'язковим для введення"
			},
			lastName : {
				required : "Поле є обов'язковим для введення"
			},
			email : {
				required : "Поле є обов'язковим для введення",
				email : "Це не емейл"
			},
			"address.postcode" : {
				required : "Поле є обов'язковим для введення"
			
			},
			"passport.seria" : {
				required : "Поле є обов'язковим для введення"
			},
			"passport.number" : {
				required : "Поле є обов'язковим для введення",
			}
		}
	});
});