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
				<label>Welcome, ${pageContext.request.userPrincipal.name}</label>
				</div>
				<div class="col-md-5">
				<a href="<c:url value="/logout"/>" class="btn btn-primary btn-sm" role="button">Logout</a>
				</div>
			</c:if>


		</div>
		<div class="col-md-4">
			<ul class="languages">
				<li><a href="?lang=ua"><img
						src="<c:url value='/resource/img/lang/ua.png'/>"></a></li>
				<li><a href="?lang=ru"><img
						src="<c:url value='/resource/img/lang/ru.png'/>"></a></li>
				<li><a href="?lang=en"><img
						src="<c:url value='/resource/img/lang/en.png'/>"></a></li>
			</ul>
		</div>
	</div>
</div>