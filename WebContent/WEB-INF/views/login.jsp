<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<div class="title">
	<div class="signin-container">



		<fieldset id="login_fieldset" class="forms">

			<c:if test="${pageContext.request.userPrincipal.name == null}">

				<form:form name='loginForm' id="loginForm"
					action="${pageContext.request.contextPath}/login" method='POST'>

					<div style="color: red;">${sessionScope["SPRING_SECURITY_LAST_EXCEPTION"].message}</div>

					<div class="form-group">
						<label for="inputEmail">Login</label>
						<input class="form-control"
							id="login" name="login" type="text"
							placeholder="Username" size="30" autocomplete="on"
							autofocus="autofocus">

					</div>


					<div class="form-group">
						<label for="inputPassword">Password</label>
						<input
							class="form-control" id="password" name="password"
							type="password" placeholder="Password" size="30"
							autocomplete="on">
					</div>



					<div class="checkbox">
						<label><input type="checkbox" id="_spring_security_remember_me" value="true" name="_spring_security_remember_me"> Remember me</label>
					</div>
					<button type="submit" class="btn btn-primary"
						style="width: 100px; margin-left: 60px;">Login</button>
					<a href="/registrator/register" class="btn btn-success"
						role="button" style="width: 100px;">Register</a>


				</form:form>
			</c:if>
		</fieldset>
	</div>






</div>




