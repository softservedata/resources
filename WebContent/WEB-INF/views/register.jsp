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
<p><p><p><p><p>
<legend><strong><spring:message code="label.manualregister" /></strong></legend>
<div id="register_container" style="margin-top: 91px" class="col-md-4">
<!--   <c:url var="addAction" value="${base}/register"></c:url> -->
   	<sf:form action="${pageContext.request.contextPath}/register" class="form-horizontal" role="form" modelAttribute="registrationForm" method="POST" name="registrationForm">
    	<div class="form-group">
       <div class="personal_header header"><span><b> ► <spring:message code="label.user.information" /></b></span>
        </div>
        <div class="personal_content content">
      <!-- In case you want to see all the error message on the top of the form, uncomment below line:
      		  <sf:errors path="*" element="div" cssClass="errors" /> -->       
              <label for="firstName"><spring:message code="label.user.firstname" /></label> *
              <input name="firstName" type="text" class="form-control login" id="firstName" value="${registrationForm.firstName}" />
              <sf:errors path="firstName" class="error" /></br>

              <label for="lastName" ><spring:message code="label.user.secondname" /></label> *
              <input name="lastName" type="text" class="form-control login" id="lastName" value="${registrationForm.lastName}" />
              <sf:errors path="lastName" class="error" /></br>

              <label for="middleName"><spring:message code="label.user.middlename" /></label> *
              <input name="middleName" type="text" class="form-control login" id="middleName" />
              <sf:errors path="middleName" class="error" /></br>

              <label for="email"><spring:message code="label.user.email" /></label> *
              <input name="email" type="text" class="form-control login" id="email"/>
              <sf:errors path="email" class="error" /></br>

              <label for="login" ><spring:message code="label.login" /></label> *
              <input name="login" type="text" class="form-control login" id="login"/>
              <sf:errors path="login" class="error" /></br>

              <label for="password" ><spring:message code="label.password" /></label> *
              <input name="password" type="password" class="form-control login" id="password" type="password"/>
              <sf:errors path="password" class="error" /></br>

              <label for="confirmPassword" ><spring:message code="label.confirmPassword" /> *</label>
              <input name="confirmPassword" type="password" class="form-control login" id="confirmPassword" type="password"/>
              <sf:errors path="confirmPassword" class="error" /></br>
            </div>
             <p>
            <div class="passport_header header"><span><b> ► <spring:message code="label.user.passportinfo" /></b></span>

            </div>
            <div class="passport_content content">
              <label for="passport_seria"><spring:message code="label.user.seria" /></label> *
              <input name="seria" type="text" class="form-control login" id="passport_seria"/>
              <sf:errors path="seria" class="error" /></br>

              <label for="passport_number"><spring:message code="label.user.number" /></label> *
              <input name="number" type="text" class="form-control login" id="passport_number"/>
              <sf:errors path="number" class="error" /></br>

              <label for="published_by_data"><spring:message code="label.user.published" /></label> *
              <input name="publishedByData" type="text" class="form-control login" id="published_by_data" />
              <sf:errors path="publishedByData" class="error" /></br>
            </div>
            <p>
<%--             <div class="address_header header"><span><b> ► Адреса</b></span></div>
            <div class="address_content content">
                <label for="city">Місто *</label>
                <input name="city" type="text" class="form-control login" id="city" />
               <sf:errors path="city" class="error" /></br>

                <label for="region">Область *</label>
                <input name="region" type="text" class="form-control login" id="region" />
                <sf:errors path="region" class="error" /></br>

                <label for="district">Район</label>
                <input name="district" type="text" class="form-control login" id="district"/>
                <sf:errors path="district" class="error" /></br>

                <label for="street">Вулиця *</label>
                <input name="street" type="text" class="form-control login" id="street"/>
                <sf:errors path="street" class="error" /></br>

                <label for="building">Будинок *</label>
                <input name="building" type="text" class="form-control login" id="building"/>
                <sf:errors path="building" class="error" /></br>

                <label for="flat">Номер квартири</label>
                <input name="flat" type="text" class="form-control login" id="flat"/>
                <sf:errors path="flat" class="error" /></br>

                <label for="postcode">Поштовий код *</label>
                <input name="postCode" type="text" class="form-control login" id="postcode"/>
                <sf:errors path="postCode" class="error" /></br>--%>
            </div> 
            <p>
            <sf:button class="btn btn-success" type="submit" id="submit"><spring:message code="label.send" /></sf:button>
            <sf:button class="btn btn-success" type="reset" onclick="location.replace('register');"><spring:message code="label.clearall" /></sf:button>
            </p>
   </sf:form>
<p><spring:message code="label.msg.required" /> 
</div>

<script src="${base}resource/js/registration/validate_registration.js" type="text/javascript"></script>

</body>