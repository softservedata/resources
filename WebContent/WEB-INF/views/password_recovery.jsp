<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%-- <%@ taglib prefix="spring" uri="http://www.springframework.org/tags”%>  --%>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Login Page for Examples</title>
<script type="text/javascript" src="<c:url value='/resource/js/lib/captcha.js'/>"></script>
<script type="text/javascript">
$(function () {
    var captcha = new CAPTCHA({
        selector: '#captcha',
        width: 250,
        height: 100
        });
        captcha.generate();

});
</script>
<style type="text/css">
body {
	font: 14px / 1.4 Verdana, Helvetica, Arial, Sans-serif;
    background-color: #e3e3e3;
}

fieldset {
	display: block;
    border: 2px groove threedface;
}

.button {
	font: 14px / 1.4 Verdana, Helvetica, Arial, Sans-serif;
	background-color: #3fb0ac;
}
</style>
</head>
<h3>Request Password Reset</h3>
<!-- <div name="sendPassInfo"> -->
In order to get your new password, type below your email, given while registration, and letter with recovery code will be sent
<div class="passwordRecoveryContainer">
    <form id="passwordRecoveryForm" method="GET" action="<c:url value='/send_password'/>" >
		<p><label for="email">Your email:
		<input type="text" name="email" class="sendpass">
        <p><input type="submit" class="button" id="submit" name="submit" value="Відправити" disabled>
        <input type="reset" class="button" name="reset" value="Очистити">
	</form>
</div>
<div id="captcha">
    <p><label>Введіть символи</label>
</div>
<input type="submit" onClick="location.reload();" name="new_captcha" value="Get new code">

</hr>
<div id="haveCodeLinkContainer">
<p><a id="haveCodeLink" href="<c:url value='/recovery/code'/>">Already have a recovery code?</a></p>
</div>
<div id="contactAdminContainer">
<p>Don’t remember your registered email or have any other issues with login? <a href="<c:url value='/help'/>" id="forgottenEmailLink">Contact Administrator</a>
</div>
</body>
</html>