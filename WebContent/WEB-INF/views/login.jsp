<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<body>
<div class="signin-container">
  <fieldset id="login_fieldset" class="forms">
    <c:if test="${pageContext.request.userPrincipal.name == null}">
 	    <form:form name="login_form" action="${pageContext.request.contextPath}/login" method="POST">
 	       <table id="err">
 	       <tr><td colspan="2" style="color: red;">${sessionScope["SPRING_SECURITY_LAST_EXCEPTION"].message}</td></tr></table>

           <label for="login-field"><spring:message code="login.username" /></label>
           <br><input type="text" name="username" id="login-field">

           <p><label for="password-field"><spring:message code="login.password" /></label>
           <br><input type="password" name="password" id="password-field">

           <p><input type="submit" title="spring:message code='login.signin'">
           
           <p>
           <input name="autologin" id="autologin" type="checkbox" />
           <label for="autologin"><spring:message code="login.rememberme" /></label>

           <p><a href="${pageContext.request.contextPath}/send-password"><spring:message code="login.forgotpassword" /></a>
        </form:form>
     </c:if>
</fieldset>
</div>
<hr>
<div class="registration-container">
    <fieldset id="login_fieldset">
    <p><spring:message code="login.noaccount" /></p>
    <p><a href="${pageContext.request.contextPath}/register"><spring:message code="login.signup" /></a>
    </fieldset>
</div>
</body>

