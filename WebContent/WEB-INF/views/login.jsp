<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!--<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Login Page</title>
<link rel="stylesheet" href="resource/css/login.css" type="text/css" media="screen">
</head>-->
<body>
<!--<img id="login_img" src="resource/img/pexels-photo.jpg">-->
<div class="title">
<div class="signin-container">



  <fieldset id="login_fieldset" class="forms">
  
  <c:if test="${pageContext.request.userPrincipal.name == null}">
<%--  --%>
	<form:form name='loginForm' id="loginForm" action="${pageContext.request.contextPath}/login" method='POST'>

		<table width="50%" border="0" align="center"
			style="padding-bottom: 50px;">
			<tr>
				<td colspan="2">&nbsp;</td>
			</tr>
			<tr>
				<td colspan="2" style="color: red;">${sessionScope["SPRING_SECURITY_LAST_EXCEPTION"].message}
				</td>
			</tr>
			<tr>
				<td colspan="2">&nbsp;</td>
			</tr>
			<tr>
				<td colspan="2" style="color: blue; font-size: 20px;"><b>Login
						Here </b></td>
			</tr>


			<tr>
				<td width="35%">Username</td>
				<td><input id="j_username" name="j_username" type="text"
					placeholder="Username" size="30" autocomplete="on"
					autofocus="autofocus"></td>
			</tr>
			<tr>
				<td>Password</td>
				<td><input id="j_password" name="j_password" type="password"
					placeholder="Password" size="30" autocomplete="on"></td>
			</tr>
			<tr>
				<td colspan="2">&nbsp;</td>
			</tr>

			<tr>
				<td>&nbsp;</td>
				<td style="height: 20px;" class="tdLabelC">
					<button id="subbtn">Sign In</button>
				</td>
			</tr>
		</table>

	</form:form>
	
</c:if>
  
       
        
        
        
  </fieldset>
</div>
<hr>
<div class="registration-container">
    <fieldset id="login_fieldset">
    <p>Я новий користувач</p>
    <p><a href="/registrator/register">Зареєструватися</a></p>
    </fieldset>
</div>
<!--<footer>Developed and Designed by <a href="http://www.softserveinc.com/">SoftServe Inc.</footer>
</body>
</html>-->


<!--  <form name="login_form" action="/registrator/login" method="post"> -->
<!--             <p> -->
<!--                 <label for="login-field">Ім"я користувача:</label> -->
<!--                 <br><input type="text" name="login" id="login-field"></br> -->
<!--             </p> -->
<!--             <p> -->
<!--                 <label for="password-field">Пароль:</label> -->
<!--                 <br><input type="password" name="password" id="password-field"></br> -->
<!--             </p> -->
<!--             <p> -->
<!--                 <input type="submit" value="Увійти"> -->
<!--             </p> -->
<!--             <p> -->
<!--                 <label for="autologin"><input name="autologin" id="autologin" tabindex="4" type="checkbox"> Запам'ятати мене з цього комп'ютера</label> -->
<!--             </p> -->
<!--             <p> -->
<!--                 <a href="/sendpassword">Я забув свій пароль</a> -->
<!--             </p> -->
<!--         </form> -->


