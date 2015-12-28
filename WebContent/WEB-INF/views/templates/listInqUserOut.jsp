<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<!-- <c:set var="req" value="${pageContext.request}" />
<c:set var="url">${req.requestURL}</c:set>
<c:set var="base"
	value="${fn:substring(url, 0, fn:length(url) - fn:length(req.requestURI))}${req.contextPath}/" />
-->

<div style="text-align: center;">
	<h4>
		<spring:message code="label.inquiry.listInquiryUserOut.pagename" />
	</h4>
</div>

<!-- <% session.setAttribute("userLogin", "ivan"); %> -->

<p>
	<a href="${base}inquiry/add/outputInquiry"
		class="btn btn-success" role="button"><spring:message code="label.inquiry.output.pagename"/></a>
		<a href="${base}inquiry/add/inputInquiry"
		class="btn btn-success" role="button">Inquiry for inputting the resource</a>
</p> 

			<table id="datatable" class="table display"> 
			
				<thead>
					<tr>
						<th><spring:message code="label.inquiry.inquiryId" /></th>
						<th><spring:message code="label.inquiry.date" /></th>
						<th><spring:message code="label.inquiry.user" /></th>
						<th><spring:message code="label.resource.registrator" /></th>
						<th hidden="true" ><spring:message code="label.inquiry.inquiryType" /></th>
						<th><spring:message code="label.resource.identifier" /></th>
						<th><spring:message code="label.restype.actions" /></th>
					</tr>
				</thead>
				
				<tbody>
					 <c:if test="${not empty listInquiryUserOut}"> 
					
						<c:forEach items="${listInquiryUserOut}" var="inquiryUserOut">						
							<tr>								
								<td> ${inquiryUserOut.inquiry_list_id}	</td>
								<td> ${inquiryUserOut.date}	</td>
								<td> ${inquiryUserOut.userName}	</td>
								<td> ${inquiryUserOut.registratorName}	</td>
								<td hidden="true"> ${inquiryUserOut.inquiryType}	</td>
								<td> ${inquiryUserOut.resourceIdentifier}	</td>	
								<td>
									<a href="delete/${inquiryUserOut.inquiry_list_id}"
											class="btn btn-danger" role="button"> 
											<spring:message code="label.restype.delete" /></a>									
								</td>							
							</tr>	
						</c:forEach>
						
					 </c:if> 
					
				</tbody>
										   			
			</table>				
			
		
		