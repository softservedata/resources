<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="sf"%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Edit User Profile</title>
<link rel="stylesheet" href="${base}resource/css/register.css">
<link rel="stylesheet" href="${base}resource/css/bootstrap.css">
<script type="text/javascript">
// AJAX request
$(document).ready(function() {
  $('#username').keyup(function (){
      var username = $(this).val();
        console.log(username);

        if(username.length >= 3){
        $.ajax({
            url: 'http://localhost:8080/registrator/check-username-is-available',
            method: 'GET',
            data: {login: username},
            dataType: 'json',
            success: function(data){
                var divElem = $();
                if(data == "fail"){
                  console.log(username + " is already in use");
                  //divElem.text(username + " is already in use");
                  //divElem.css('color', 'red');
                }
            },
            error: function(){
                console.log("Cannot process request");
            }
        })}})
});
</script>
<style>
input.error {
	border-style: solid;
	border-color: #ff0000;
}

span.error {
	color: red;
}
</style>
</head>
<body>
	<p>
	<p>
	<p>
	<p>
	<p>
		<strong><spring:message code="label.manualregister" /></strong>
	<div id="register_container" style="margin-top: 91px">
		<sf:form action="${pageContext.request.contextPath}/register"
			class="form-horizontal" role="form" modelAttribute="registrationForm"
			method="POST" name="registrationForm">
			<div class="form-group">
				<div class="personal_header col-lg-4">
					<legend><spring:message code="label.user.information" /></legend>
					<!-- <div class="personal_header header"><span><b> ► <spring:message code="label.user.information" /></b></span>
        <div class="personal_content content">-->
					<!-- In case you want to see all the error message on the top of the form, uncomment below line:
      		  <sf:errors path="*" element="div" cssClass="errors" /> -->
					<label class="col-lg-4 control-label" for="firstName"><spring:message
							code="label.user.firstname" /> *</label>
					<div class="col-lg-8">
						<input name="firstName" type="text" class="form-control login"
							id="firstName" value="${registrationForm.firstName}" />
						<sf:errors path="firstName" class="error" />
						</br>
					</div>

					<label class="col-lg-4 control-label" for="lastName"><spring:message
							code="label.user.secondname" />*</label>
					<div class="col-lg-8">
						<input name="lastName" type="text" class="form-control login"
							id="lastName" value="${registrationForm.lastName}" />
						<sf:errors path="lastName" class="error" />
						</br>
					</div>

					<label class="col-lg-4 control-label" for="middleName"><spring:message
							code="label.user.middlename" /> *</label>
					<div class="col-lg-8">
						<input name="middleName" type="text" class="form-control login"
							id="middleName" value="${registrationForm.middleName}" />
						<sf:errors path="middleName" class="error" />
						</br>
					</div>

					<label class="col-lg-4 control-label" for="email"><spring:message
							code="label.user.email" /> *</label>
					<div class="col-lg-8">
						<input name="email" type="text" class="form-control login"
							id="email" value="${registrationForm.email}" />
						<sf:errors path="email" class="error" />
						</br>
					</div>

					<label class="col-lg-4 control-label" for="login"><spring:message
							code="label.login" /> *</label>
					<div class="col-lg-8">
						<input name="login" type="text" class="form-control login"
							id="login" value="${registrationForm.login}" />
						<sf:errors path="login" class="error" />
						</br>
					</div>

					<label class="col-lg-4 control-label" for="password"><spring:message
							code="label.password" /> *</label>
					<div class="col-lg-8">
						<input name="password" type="password" class="form-control login"
							id="password" type="password" />
						<sf:errors path="password" class="error" />
						</br>
					</div>

					<label class="col-lg-4 control-label" for="confirmPassword"><spring:message
							code="label.confirmPassword" /> *</label>
					<div class="col-lg-8">
						<input name="confirmPassword" type="password"
							class="form-control login" id="confirmPassword" type="password" />
						<sf:errors path="confirmPassword" class="error" />
						</br>
					</div>
				</div>
				<!--<div class="address_header header"><span><b> ► Адреса</b></span></div>
            <div class="address_content content">-->
				<div class="address_header col-lg-4">
					<legend><spring:message code="label.user.addressinfo" /></legend>
					<label class="col-lg-4 control-label" for="city"><spring:message
							code="label.user.city" /> *</label>
					<div class="col-lg-8">
						<input name="city" type="text" class="form-control login"
							id="city" value="${registrationForm.city}" />
						<sf:errors path="city" class="error" />
						</br>
					</div>

					<label class="col-lg-4 control-label" for="region"><spring:message
							code="label.user.region" /> *</label>
					<div class="col-lg-8">
						<input name="region" type="text" class="form-control login"
							id="region" value="${registrationForm.region}" />
						<sf:errors path="region" class="error" />
						</br>
					</div>

					<label class="col-lg-4 control-label" for="district"><spring:message
							code="label.user.district" /> </label>
					<div class="col-lg-8">
						<input name="district" type="text" class="form-control login"
							id="district" value="${registrationForm.district}" />
						<sf:errors path="district" class="error" />
						</br>
					</div>

					<label class="col-lg-4 control-label" for="street"><spring:message
							code="label.user.street" /> *</label>
					<div class="col-lg-8">
						<input name="street" type="text" class="form-control login"
							id="street" value="${registrationForm.street}" />
						<sf:errors path="street" class="error" />
						</br>
					</div>

					<label class="col-lg-4 control-label" for="building"><spring:message
							code="label.user.building" /> *</label>
					<div class="col-lg-8">
						<input name="building" type="text" class="form-control login"
							id="building" value="${registrationForm.building}" />
						<sf:errors path="building" class="error" />
						</br>
					</div>

					<label class="col-lg-4 control-label" for="flat"><spring:message
							code="label.user.flat" /> </label>
					<div class="col-lg-8">
						<input name="flat" type="text" class="form-control login"
							id="flat" value="${registrationForm.flat}" />
						<sf:errors path="flat" class="error" />
						</br>
					</div>

					<label class="col-lg-4 control-label" for="postcode"><spring:message
							code="label.user.postcode" /> *</label>
					<div class="col-lg-8">
						<input name="postcode" type="text" class="form-control login"
							id="postcode" value="${registrationForm.postcode}" />
						<sf:errors path="postcode" class="error" />
						</br>
					</div>
				</div>
			<!--<div class="passport_header header"><span><b> ► <spring:message code="label.user.passportinfo" /></b></span>
             </div>
             <div class="passport_content content">-->
				<div class="passport_header col-lg-4">
					<legend><spring:message code="label.user.passportinfo" /></legend>

					<label class="col-lg-4 control-label" for="passport_seria"><spring:message
							code="label.user.seria" /> *</label>
					<div class="col-lg-8">
						<input name="seria" type="text" class="form-control login"
							id="passport_seria" value="${registrationForm.seria}" />
						<sf:errors path="seria" class="error" />
						</br>
					</div>

					<label class="col-lg-4 control-label" for="passport_number"><spring:message
							code="label.user.number" /> *</label>
					<div class="col-lg-8">
						<input name="number" type="text" class="form-control login"
							id="passport_number" value="${registrationForm.number}" />
						<sf:errors path="number" class="error" />
						</br>
					</div>

					<label class="col-lg-4 control-label" for="published_by_data"><spring:message
							code="label.user.published" /> *</label>
					<div class="col-lg-8">
						<input name="publishedByData" type="text"
							class="form-control login" id="published_by_data"
							value="${registrationForm.publishedByData}" />
						<sf:errors path="publishedByData" class="error" />
						</br>
					</div>
				</div>
			<!-- </div> -->
			<p>
	</div>
	<p>
		<sf:button class="btn btn-success" type="submit" id="submit">
			<spring:message code="label.send" />
		</sf:button>
		<sf:button class="btn btn-success" type="reset"
			onclick="location.replace('register');">
			<spring:message code="label.clearall" />
		</sf:button>
	</p>
	</sf:form>
		<p>
	<spring:message code="label.msg.required" />
</div>	
<script src="${base}resource/js/registration/validate_registration.js" type="text/javascript"></script>
</body>