<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<c:set var="req" value="${pageContext.request}" />
<c:set var="url">${req.requestURL}</c:set>
<c:set var="base" value="${fn:substring(url, 0, fn:length(url) - fn:length(req.requestURI))}${req.contextPath}/" />


<img id="login_img" src="resource/img/pexels-photo.jpg">
<div class="site-title">
   <h3 style="color :white; background-color: blue;"><spring:message code="label.name.part1"/></h3>
   <h3 style="color :white; background-color: blue;"><spring:message code="label.name.part2"/></h3>
</div>


<div class="lang-container" id="header">
    <div>
        <div>
            <ul class="languages">
                <a href="?lang=ua"><img src="resource/img/lang/ua.png"></a></li>
                <a href="?lang=ru"><img src="resource/img/lang/ru.png"></a></li>
                <a href="?lang=en"><img src="resource/img/lang/en.png"></a></li>
            </ul>
        </div>
    </div>
</div>

