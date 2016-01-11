<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<c:set var="req" value="${pageContext.request}" />
<c:set var="url">${req.requestURL}</c:set>
<c:set var="base"
	value="${fn:substring(url, 0, fn:length(url) - fn:length(req.requestURI))}${req.contextPath}/" />

<!DOCTYPE html>
<html>
<head>



<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->

<!-- fav-icon -->
<link rel="Shortcut Icon" href="/resource/img/tree_leaf.ico"
	type="image/x-icon" />
<title><tiles:getAsString name="title" /></title>

<!-- Bootstrap -->
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css">

<!-- Our own css -->
<link rel="stylesheet" href="${base}resource/css/system.css">

<!-- DataTables CSS-->
<link rel="stylesheet"
	href="https://cdn.datatables.net/1.10.10/css/jquery.dataTables.min.css">

<link rel="stylesheet" href="${base}resource/css/login.css">

<!-- JQuery lib-->
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>

<!-- DataTables lib -->
<script
	src="https://cdn.datatables.net/1.10.10/js/jquery.dataTables.min.js"></script>

<!-- Our JavaScript-->
<script src="${base}resource/js/scripts.js"></script>


</head>
<body>

	<c:if test="${pageContext.request.userPrincipal.name != null}">

		<div class="container">
			<div class="row">
				<div class="col-md-12" id="header">
					<tiles:insertAttribute name="header" />
				</div>
			</div>
			<div class="row">
				<div id="menu">
					<div class="col-md-12" id="menuForAdmin">
						<tiles:insertAttribute name="menuForAdmin" role="ADMIN" />

					</div>
					<div class="col-md-12" id="menuForRegistrator">
						<tiles:insertAttribute name="menuForRegistrator"
							role="REGISTRATOR" />

					</div>
					<div class="col-md-12" id="menuForUser">
						<tiles:insertAttribute name="menuForUser" role="USER" />

					</div>
				</div>
			</div>
			<div class="row">
				<div id="body">
					<tiles:insertAttribute name="body" />
				</div>
			</div>
			<div class="row">
				<div class="col-md-12" id="footer">
					<tiles:insertAttribute name="footer" />
				</div>
			</div>

		</div>


	</c:if>
	<c:if test="${pageContext.request.userPrincipal.name == null}">

		<div class="site-title">
			<h3 style="color: white; background-color: blue;">
				<spring:message code="label.name.part1" />
			</h3>
			<h3 style="color: white; background-color: blue;">
				<spring:message code="label.name.part2" />
			</h3>
		</div>


		<div class="lang-container" id="header">
			<div>
				<div>
					<ul class="languages">
						<a href="?lang=ua"><img src="resource/img/lang/ua.png"></a>
						</li>
						<a href="?lang=ru"><img src="resource/img/lang/ru.png"></a>
						</li>
						<a href="?lang=en"><img src="resource/img/lang/en.png"></a>
						</li>
					</ul>
				</div>
			</div>
		</div>




		<img src="resource/img/ukraine_logo.gif" style="margin-left: 113px;"/>


		<div class="title">
			<div class="signin-container">



				<fieldset id="login_fieldset" class="forms">

					<c:if test="${pageContext.request.userPrincipal.name == null}">

						<form:form name='loginForm' id="loginForm"
							action="${pageContext.request.contextPath}/login" method='POST'>

							<div style="color: red;">${sessionScope["SPRING_SECURITY_LAST_EXCEPTION"].message}</div>

							<div class="form-group">
								<label for="inputEmail">Login</label> <input
									class="form-control" id="login" name="login"
									type="text" placeholder="Username" size="30" autocomplete="on"
									autofocus="autofocus">

							</div>


							<div class="form-group">
								<label for="inputPassword">Password</label> <input
									class="form-control" id="password" name="password"
									type="password" placeholder="Password" size="30"
									autocomplete="on">
							</div>



							<div class="checkbox">
								<label><input type="checkbox"
									id="_spring_security_remember_me" value="true"
									name="_spring_security_remember_me"> Remember me</label>
							</div>
							<button type="submit" class="btn btn-primary"
								style="width: 100px; margin-left: 60px;">Login</button>
							<a href="register" class="btn btn-success"
								role="button" style="width: 100px;">Register</a>


						</form:form>
					</c:if>
				</fieldset>
			</div>






		</div>


		<div style="margin-top: 91px">
			<p style="text-align: center">
				&copy;2015
				<spring:message code="label.copyright" />
			</p>
		</div>



	</c:if>

</body>
</html>