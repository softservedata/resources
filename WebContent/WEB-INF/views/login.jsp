<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
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
        <form name="login_form" action="/registrator/login" method="post">
            <p>
                <label for="login-field">Ім"я користувача:</label>
                <br><input type="text" name="login" id="login-field"></br>
            </p>
            <p>
                <label for="password-field">Пароль:</label>
                <br><input type="password" name="password" id="password-field"></br>
            </p>
            <p>
                <input type="submit" value="Увійти">
            </p>
            <p>
                <label for="autologin"><input name="autologin" id="autologin" tabindex="4" type="checkbox"> Запам'ятати мене з цього комп'ютера</label>
            </p>
            <p>
                <a href="/sendpassword">Я забув свій пароль</a>
            </p>
        </form>
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