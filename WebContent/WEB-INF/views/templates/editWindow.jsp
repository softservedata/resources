<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<c:set var="req" value="${pageContext.request}" />
<c:set var="url">${req.requestURL}</c:set>
<c:set var="base"
	value="${fn:substring(url, 0, fn:length(url) - fn:length(req.requestURI))}${req.contextPath}/" />

<spring:url value="/resource/js/move.js" var="moveJs" />

<script src="${moveJs}"></script>

<div class="container">
	<form:form id="editWinodow" modelAttribute="userDTO" method="post"
		action="${base}administrator/users/edit-registrated-user"
		class="form-horizontal">
		<fieldset>
			<div class="row">
				<div class="col-lg-4">
					<legend>
						<spring:message code="label.user.information" />
					</legend>
					<div class="form-group">
						<label class="col-lg-4 control-label" for="textinput"><spring:message code="label.user.firstname" /></label>
						<div class="col-lg-8">
							<input id="textinput" name="firstName" placeholder=""
								class="form-control input-md" type="text"
								value="${userDto.firstName}">
						</div>
					</div>
					<div class="form-group">
						<label class="col-lg-4 control-label" for="textinput"><spring:message
								code="label.user.secondname" /></label>
						<div class="col-lg-8">
							<input id="textinput" name="lastName" placeholder=""
								class="form-control input-md" type="text"
								value="${userDto.lastName}">
						</div>
					</div>
					<div class="form-group">
						<label class="col-lg-4 control-label" for="textinput"><spring:message
								code="label.user.middlename" /></label>
						<div class="col-lg-8">
							<input id="textinput" name="middleName" placeholder=""
								class="form-control input-md" type="text"
								value="${userDto.middleName}">
						</div>
					</div>
					<div class="form-group">
						<label class="col-lg-4 control-label" for="textinput"><spring:message code="label.login"/></label>
						<div class="col-lg-8">
							<input id="textinput" name="login" placeholder=""
								class="form-control input-md" type="text"
								value="${userDto.login}" readonly>
						</div>
					</div>
					<div class="form-group">
						<label class="col-lg-4 control-label" for="textinput"><spring:message
								code="label.password" /></label>
						<div class="col-lg-8">
							<input id="textinput" name="password" placeholder=""
								class="form-control input-md" type="text"
								value="${userDto.password}">
						</div>
					</div>
					<div class="form-group">
						<label class="col-lg-4 control-label" for="textinput"><spring:message
								code="label.user.email" /></label>
						<div class="col-lg-8">
							<input id="textinput" name="email" placeholder=""
								class="form-control input-md" type="text"
								value="${userDto.email}">
						</div>
					</div>
					<div class="form-group">
						<label class="col-lg-4 control-label" for="textinput"><spring:message
								code="label.user.role" /></label>
						<div class="col-lg-8">
							<select id="roleId" name="role" class="form-control"
								style="width: 230px; height: 34px">
								<c:forEach items="${roleList}" var="role">
									<c:choose>
										<c:when test="${userDto.role == role.type}">
											<option selected value="${role.type}">${role.type}</option>
										</c:when>
										<c:otherwise>
											<option value="${role.type}">${role.type}</option>
										</c:otherwise>
									</c:choose>
								</c:forEach>
							</select>
						</div>
					</div>
					<div class="form-group">
						<label class="col-lg-4 control-label" for="textinput"><spring:message
								code="label.user.status" /></label>
						<div class="col-lg-8">
							<select id="userStatusId" name="status" class="form-control"
								style="width: 230px; height: 34px">
								<c:forEach items="${userStatusList}" var="userStatus">
									<c:choose>
										<c:when test="${userDto.status == userStatus}">
											<option selected value="${userStatus}">${userStatus}</option>
										</c:when>
										<c:otherwise>
											<option value="${userStatus}">${userStatus}</option>
										</c:otherwise>
									</c:choose>
								</c:forEach>
							</select>
						</div>
					</div>
				</div>
				<div class="col-lg-4">
					<legend>
						<spring:message code="label.user.addressinfo" />
					</legend>
					<div class="form-group">
						<label class="col-lg-4 control-label" for="textinput"><spring:message
								code="label.user.region" /></label>
						<div class="col-lg-8">
							<input id="textinput" name="address.region" placeholder=""
								class="form-control input-md" type="text"
								value="${userDto.address.region}">
						</div>
					</div>
					<div class="form-group">
						<label class="col-lg-4 control-label" for="textinput"><spring:message
								code="label.user.city" /></label>
						<div class="col-lg-8">
							<input id="textinput" name="address.city" placeholder=""
								class="form-control input-md" type="text"
								value="${userDto.address.city}">
						</div>
					</div>
					<div class="form-group">
						<label class="col-lg-4 control-label" for="textinput"><spring:message
								code="label.user.district" /></label>
						<div class="col-lg-8">
							<input id="textinput" name="address.district" placeholder=""
								class="form-control input-md" type="text"
								value="${userDto.address.district}">
						</div>
					</div>
					<div class="form-group">
						<label class="col-lg-4 control-label" for="textinput"><spring:message
								code="label.user.street" /></label>
						<div class="col-lg-8">
							<input id="textinput" name="address.street" placeholder=""
								class="form-control input-md" type="text"
								value="${userDto.address.street}">
						</div>
					</div>
					<div class="form-group">
						<label class="col-lg-4 control-label" for="textinput"><spring:message
								code="label.user.building" /></label>
						<div class="col-lg-8">
							<input id="textinput" name="address.building" placeholder=""
								class="form-control input-md" type="text"
								value="${userDto.address.building}">
						</div>
					</div>
					<div class="form-group">
						<label class="col-lg-4 control-label" for="textinput"><spring:message
								code="label.user.flat" /></label>
						<div class="col-lg-8">
							<input id="textinput" name="address.flat" placeholder=""
								class="form-control input-md" type="text"
								value="${userDto.address.flat}">
						</div>
					</div>
					<div class="form-group">
						<label class="col-lg-4 control-label" for="textinput"><spring:message
								code="label.user.postcode" /></label>
						<div class="col-lg-8">
							<input id="textinput" name="address.postcode" placeholder=""
								class="form-control input-md" type="text"
								value="${userDto.address.postcode}">
						</div>
					</div>
				</div>
				<div class="col-lg-4">
					<legend>
						<spring:message code="label.user.passportinfo" />
					</legend>
					<div class="form-group">
						<label class="col-lg-4 control-label" for="textinput"><spring:message
								code="label.user.seria" /></label>
						<div class="col-lg-8">
							<input id="textinput" name="passport.seria" placeholder=""
								class="form-control input-md" type="text"
								value="${userDto.passport.seria}">
						</div>
					</div>
					<div class="form-group">
						<label class="col-lg-4 control-label" for="textinput"><spring:message
								code="label.user.number" /></label>
						<div class="col-lg-8">
							<input id="textinput" name="passport.number" placeholder=""
								class="form-control input-md" type="text"
								value="${userDto.passport.number}">
						</div>
					</div>
					<div class="form-group">
						<label class="col-lg-4 control-label" for="textinput"><spring:message
								code="label.user.published" /></label>
						<div class="col-lg-8">
							<input id="textinput" name="passport.published_by_data"
								placeholder="" class="form-control input-md" type="text"
								value="${userDto.passport.published_by_data}">
						</div>
					</div>
				</div>
			</div>
			<div class="wrapper" style="text-align: center">
				<input type="submit" value="Submit" class="btn btn-primary btn-sm">
			</div>
			<p>
		</fieldset>
	</form:form>
</div>