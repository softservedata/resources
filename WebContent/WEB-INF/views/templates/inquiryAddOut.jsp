<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<!--
<div style="text-align: center;">
	<h4>
		<spring:message code="label.inquiry.output.pagename" />
	</h4>
</div>

 

		<form:form method="POST" action="addOutputInquiry" >
			<table class="table display">
					<tr>
						<td><spring:message code="label.resource.registrator" />
							(<spring:message code="label.resource.tome" />):</td>
						<td>  <select id="tomeIdentifier" name="tomeIdentifier">
								<option value=""><spring:message
										code="label.resource.registrator.select" />:
								</option>
								<c:forEach items="${tomes}" var="tome">
									<option value="${tome.tomeIdentifier}">${tome.registratorLastName} ${tome.registratorFirstName} 
										${tome.registratorMiddleName} (номер тому: ${tome.tomeIdentifier})</option>
								</c:forEach>
							</select>
						</td>
					</tr>
					<tr>
						<td><spring:message code="label.resource.identifier" />:</td>
						<td> <select name="resourceIdentifier">
								<option value=""><spring:message
										code="label.resource.selectIdentifier" />:
								</option>
								<c:forEach items="${resources}" var="resource">
									<option value="${resource.identifier}">${resource.identifier} </option>
								</c:forEach>
							</select>
						</td>
					</tr>										   			
			</table>				
			<input type="submit"  value="Надіслати запит" class="btn btn-success"/>
		
		</form:form> -->
		
		
		<select id="registratorLogin" name="registratorLogin"  class="form-control">
								<option value=""><spring:message
										code="label.resource.registrator.select" />:
								</option>
								<c:forEach items="${registrators}" var="registrator">
									<option value="${registrator.login}">${registrator.lastName} ${registrator.firstName} 
										${registrator.middleName} </option>
								</c:forEach>
		</select>
							
	