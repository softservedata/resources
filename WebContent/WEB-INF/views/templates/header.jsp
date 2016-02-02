<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>

<div class="container-fluid" id="header">

	<div class="col-md-8">
		<h3>
			<spring:message code="label.name.part1" />
		</h3>
		<h3>
			<spring:message code="label.name.part2" />
		</h3>
	</div>
	<div class="col-md-4">
		<div class="col-md-8">

			<c:if test="${pageContext.request.userPrincipal.name != null}">

				<div class="col-md-7">
					<label>${pageContext.request.userPrincipal.name}</label>
				</div>
				<div class="col-md-5">
					<a href="<c:url value="/logout"/>" class="btn btn-primary btn-sm"
						role="button"><spring:message code="label.signOut" /></a>
				</div>

			</c:if>


		</div>
		<%-- 		<div class="col-md-4">
			<ul class="languages">
				<li><a href="?lang=ua" class="lang" id="ua"><img
						src="<c:url value='/resource/img/lang/ua.png'/>"></a></li>
				<li><a href="?lang=ru" class="lang" id="ru"><img
						src="<c:url value='/resource/img/lang/ru.png'/>"></a></li>
				<li><a href="?lang=en" class="lang" id="en"><img
						src="<c:url value='/resource/img/lang/en.png'/>"></a></li>
			</ul>
		</div> --%>
		<div class="col-md-4">
			<button type="button" class="btn btn-default dropdown-toggle"
				data-toggle="dropdown" aria-expanded="false">
				<span class="selected"></span><span class="caret"></span>
			</button>
			<ul class="dropdown-menu option" role="menu">
				<li id="ua"><a href="?lang=ua">українська</a></li>
				<li id="ru"><a href="?lang=ru">русский</a></li>
				<li id="en"><a href="?lang=en">english</a></li>
			</ul>
		</div>
	</div>
</div>

<script
	src="<c:url value='/resource/js/lib/jquery.i18n.properties.js'/>"></script>

<!-- define the page language and load massages -->
<script>
	$(document).ready(function() {
		<c:set var="systemLocale" value="${pageContext.response.locale}" />
		var lang = "${pageContext.response.locale}";
		setLocalelanguage(lang);
		getLocaleMassages(lang);
		/*  $(".lang").click(function() {
		 var lang = $(this).attr("id");
		 getLocaleMassages(lang);
		 });  */
	});
	function getLocaleMassages(lang) {
		jQuery.i18n.properties({
			name : 'Messages',
			path : '${pageContext.request.contextPath}'
					+ '/resource/massages/i18n/',
			mode : 'both',
			language : lang,
		});
	}
	function setLocalelanguage(lang) {
		if(lang ==='ua') {
		$("span.selected").text("українська");			
		}
		if(lang ==='ru') {
		$("span.selected").text("русский");		
		}
		if(lang ==='en') {
		$("span.selected").text("english");			
		}

	}
</script>

