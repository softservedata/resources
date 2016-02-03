<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<style>
input.error {
	border-style: solid;
	border-color: #ff0000;
}

span.error {
	color: red;
}
h4{
text-align: center;
}
</style>

<div id="register_container">
	<form:form action="manualregistration"
		class="form-horizontal" role="form" modelAttribute="registrationForm"
		method="POST" name="registrationForm">
		<div class="form-group col-sm-12">
			<div class="personal_header col-lg-4">
				<h4><spring:message code="label.user.information" /></h4> <label
					class="col-lg-4 control-label" for="firstName"><spring:message
						code="label.user.firstname" /> *</label>
				<div class="col-lg-8">
					<input name="firstName" type="text" class="form-control login"
						id="firstName" value="${registrationForm.firstName}" />
					<form:errors path="firstName" class="error" />
					<br>
				</div>

				<label class="col-lg-4 control-label" for="lastName"><spring:message
						code="label.user.secondname" />*</label>
				<div class="col-lg-8">
					<input name="lastName" type="text" class="form-control login"
						id="lastName" value="${registrationForm.lastName}" />
					<form:errors path="lastName" class="error" />
					<br>
				</div>

				<label class="col-lg-4 control-label" for="middleName"><spring:message
						code="label.user.middlename" /> *</label>
				<div class="col-lg-8">
					<input name="middleName" type="text" class="form-control login"
						id="middleName" value="${registrationForm.middleName}" />
					<form:errors path="middleName" class="error" />
					<br>
				</div>

				<label class="col-lg-4 control-label" for="email"><spring:message
						code="label.user.email" /> *</label>
				<div class="col-lg-8">
					<input name="email" type="text" class="form-control login"
						id="email" value="${registrationForm.email}" />
					<form:errors path="email" class="error" />
					<br>
				</div>

				<label class="col-lg-4 control-label" for="login"><spring:message
						code="label.login" /> *</label>
				<div class="col-lg-8">
					<input name="login" type="text" class="form-control login"
						id="login" value="${registrationForm.login}" />
					<form:errors path="login" class="error" />
					<br>
				</div>

				<label class="col-lg-4 control-label" for="password"><spring:message
						code="label.password" /> *</label>
				<div class="col-lg-8">
					<input name="password" type="password" class="form-control login"
						id="password" type="password" />
					<form:errors path="password" class="error" />
					<br>
				</div>

				<label class="col-lg-4 control-label" for="confirmPassword"><spring:message
						code="label.confirmPassword" /> *</label>
				<div class="col-lg-8">
					<input name="confirmPassword" type="password"
						class="form-control login" id="confirmPassword" type="password" />
					<form:errors path="confirmPassword" class="error" />
					<br>
				</div>
			</div>
			<div class="address_header col-lg-4">
				<h4><spring:message code="label.user.addressinfo" /></h4> <label
					class="col-lg-4 control-label" for="city"><spring:message
						code="label.user.city" /> *</label>
				<div class="col-lg-8">
					<input name="city" type="text" class="form-control login" id="city"
						value="${registrationForm.city}" />
					<form:errors path="city" class="error" />
					<br>
				</div>

				<label class="col-lg-4 control-label" for="region"><spring:message
						code="label.user.region" /> *</label>
				<div class="col-lg-8">
					<input name="region" type="text" class="form-control login"
						id="region" value="${registrationForm.region}" />
					<form:errors path="region" class="error" />
					<br>
				</div>

				<label class="col-lg-4 control-label" for="district"><spring:message
						code="label.user.district" /> </label>
				<div class="col-lg-8">
					<input name="district" type="text" class="form-control login"
						id="district" value="${registrationForm.district}" />
					<form:errors path="district" class="error" />
					<br>
				</div>

				<label class="col-lg-4 control-label" for="street"><spring:message
						code="label.user.street" /> *</label>
				<div class="col-lg-8">
					<input name="street" type="text" class="form-control login"
						id="street" value="${registrationForm.street}" />
					<form:errors path="street" class="error" />
					<br>
				</div>

				<label class="col-lg-4 control-label" for="building"><spring:message
						code="label.user.building" /> *</label>
				<div class="col-lg-8">
					<input name="building" type="text" class="form-control login"
						id="building" value="${registrationForm.building}" />
					<form:errors path="building" class="error" />
					<br>
				</div>

				<label class="col-lg-4 control-label" for="flat"><spring:message
						code="label.user.flat" /> </label>
				<div class="col-lg-8">
					<input name="flat" type="text" class="form-control login" id="flat"
						value="${registrationForm.flat}" />
					<form:errors path="flat" class="error" />
					<br>
				</div>

				<label class="col-lg-4 control-label" for="postcode"><spring:message
						code="label.user.postcode" /> *</label>
				<div class="col-lg-8">
					<input name="postcode" type="text" class="form-control login"
						id="postcode" value="${registrationForm.postcode}" />
					<form:errors path="postcode" class="error" />
					<br>
				</div>
			</div>
			<div class="passport_header col-lg-4">
				<h4><spring:message code="label.user.passportinfo" /></h4> <label
					class="col-lg-4 control-label" for="passport_seria"><spring:message
						code="label.user.seria" /> *</label>
				<div class="col-lg-8">
					<input name="seria" type="text" class="form-control login"
						id="passport_seria" value="${registrationForm.seria}" />
					<form:errors path="seria" class="error" />
					<br>
				</div>
				<label class="col-lg-4 control-label" for="passport_number"><spring:message
						code="label.user.number" />*</label>
				<div class="col-lg-8">
					<input name="number" type="text" class="form-control login"
						id="passport_number" value="${registrationForm.number}" />
					<form:errors path="number" class="error" />
					<br>
				</div>
				<label class="col-lg-4 control-label" for="published_by_data"><spring:message
						code="label.user.published" /> *</label>
				<div class="col-lg-8">
					<input name="publishedByData" type="text"
						class="form-control login" id="published_by_data"
						value="${registrationForm.publishedByData}" />
					<form:errors path="publishedByData" class="error" />
					<br>
				</div>
				<h4><spring:message
						code="label.manualregister.otherdata" /></h4>
				<label class="col-lg-4 control-label" for="phone_number"><spring:message
						code="label.manualregister.phonenumber" /></label>
				<div class="col-lg-8">
					<input name="phoneNumber" type="text" class="form-control login"
						id="phone_number" value="${registrationForm.phoneNumber}" placeholder = "<spring:message
						code="label.manualregister.phoneexample" />" />
					<form:errors path="phoneNumber" class="error" />
					<br>
				</div>
				<label class="col-lg-4 control-label" for="territorial_Community"><spring:message
						code="label.manualregister.community" /> *</label>
				<div class="col-lg-8">
					<select name="territorialCommunity" class="form-control"
						id="territorial_Community" required>
						<option value=""><spring:message
						code="label.manualregister.choosecommunity" /></option>
						<c:forEach items="${territorialCommunities}" var="communa">
							<option value="${communa.name}">${communa.name}</option>
						</c:forEach>
					</select>
					<form:errors path="territorialCommunity" class="error" />
					<br>
				</div>
				<label class="col-lg-4 control-label" for="dateOfAccession"><spring:message
						code="label.manualregister.dateOfaccession" />*</label>
				<div class="col-lg-8">
					<input name="dateOfAccession" class="form-control" type="text"
						id="datepicker">
					<div class="control-group error">
						<form:errors path="dateOfAccession" class="error" />
						<br>
					</div>
				</div>
			</div>
		</div>
		<p>
			<button class="btn btn-success" type="submit" id="submit">
				<spring:message code="label.send" />
			</button>
			<button class="btn btn-success" type="reset">
				<spring:message code="label.clearall" />
			</button>
		</p>
	</form:form>
	<spring:message code="label.msg.required" />
</div>
<script src="<c:url value='/resource/js/registration/checkUserName.js'/>"></script>
<script src="<c:url value='/resource/js/lib/jquery-ui.datepscker.min.js'/>"></script>
<link rel="stylesheet" href="https://code.jquery.com/ui/1.11.3/themes/smoothness/jquery-ui.css">

<script>
	$("#datepicker").datepicker({
		dateFormat : 'dd.mm.yy'
	});
	if ($("#datepicker").val() === "") {
		$("#datepicker").datepicker("setDate", new Date);
	}	
</script>