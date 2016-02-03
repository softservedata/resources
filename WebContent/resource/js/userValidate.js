$(document).ready(function() {
	$("#editWindow").validate({
		rules : {
			firstName : {
				required : true
			},
			lastName : {
				required : true
			},
			middleName : {
				required : true
			},
			password : {
				required : true,
				minlength : 5,
				maxlength : 32
			},
			email : {
				required : true,
				email : true
			},
			"address.region" : {
				required : true
			},
			"address.city" : {
				required : true
			},
//			"address.district" : {
//				required : true
//			},
			"address.street" : {
				required : true
			},
			"address.building" : {
				required : true
			},
			"address.flat" : {
				required : true
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
			"passport.published_by_data" : {
				required : true
			}

		},
		messages : {
			firstName : {
				required : "Поле є обов'язковим для введення"
			},
			lastName : {
				required : "Поле є обов'язковим для введення"
			},
			middleName : {
				required : "Поле є обов'язковим для введення"
			},
			password : {
				required : "Поле є обов'язковим для введення",
				minlength : "Пароль повинен містити від 5 до 32 символів",
				maxLength : "Пароль повинен містити від 5 до 32 символів"
			},
			email : {
				required : "Поле є обов'язковим для введення",
				email : "Це не емейл"
			},
			"address.region" : {
				required : "Поле є обов'язковим для введення"
			},
			"address.city" : {
				required : "Поле є обов'язковим для введення"
			},
//			"address.district" : {
//				required : "Поле є обов'язковим для введення"
//			},
			"address.street" : {
				required : "Поле є обов'язковим для введення"
			},
			"address.building" : {
				required : "Поле є обов'язковим для введення"
			},
			"address.flat" : {
				required : "Поле є обов'язковим для введення"
			},
			"address.postcode" : {
				required : "Поле є обов'язковим для введення"
			},
			"passport.seria" : {
				required : "Поле є обов'язковим для введення"
			},
			"passport.number" : {
				required : "Поле є обов'язковим для введення"
			},
			"passport.published_by_data" : {
				required : "Поле є обов'язковим для введення"
			}

		}
	})
})