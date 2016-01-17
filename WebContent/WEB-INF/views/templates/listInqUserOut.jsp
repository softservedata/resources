<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<div style="text-align: center;">
	<h4>
		<spring:message code="label.inquiry.listInquiryUserOut.pagename" />
	</h4>
</div>



<!--  <p>
	<a href="<c:url value='/inquiry/add/outputInquiry'/>"
		class="btn btn-success" role="button"><spring:message code="label.inquiry.output.pagename"/></a>
</p>  -->
			<table id="datatable" class="table display">			
				<thead>
					<tr>						
						<th><spring:message code="label.inquiry.date" /></th>
						<th><spring:message code="label.inquiry.user" /></th>
						<th><spring:message code="label.resource.registrator" /></th>
						<th hidden="true"><spring:message code="label.inquiry.inquiryType" /></th>
						<th><spring:message code="label.resource.identifier" /></th>
						<th><spring:message code="label.restype.actions" /></th>
					</tr>
				</thead>
				
				<tbody>
					 <c:if test="${not empty listInquiryUserOut}"> 
					
						<c:forEach items="${listInquiryUserOut}" var="inquiryUserOut">						
							<tr>
								<fmt:formatDate value="${inquiryUserOut.date}" pattern="dd.MM.yyyy" var="Date" />
								<td> ${Date}	</td>
								<td> ${inquiryUserOut.userName}	</td>
								<td> ${inquiryUserOut.registratorName}	</td>
								<td hidden="true"> ${inquiryUserOut.inquiryType}	</td>
								<td><a href="get/${inquiryUserOut.resourceIdentifier}"> ${inquiryUserOut.resourceIdentifier}	</a></td>	
								<td> <div class="block">
										<c:if test="${role == 'USER'}">
											<a href="delete/${inquiryUserOut.inquiry_list_id}"
												class="btn btn-danger" role="button"> 
												<spring:message code="label.restype.delete" /></a>
										</c:if>	
										<a href="printOutput/${inquiryUserOut.inquiry_list_id}"
											class="btn btn-primary" role="button"> 
											<spring:message code="label.inquiry.print" /></a>
										<c:if test="${role == 'REGISTRATOR'}">	
											<a href="printOutput/${inquiryUserOut.inquiry_list_id}"
												class="btn btn-primary" role="button"> 
												Друкувати витяг</a>	
										</c:if>
									</div>								
								</td>							
							</tr>	
						</c:forEach>						
					 </c:if> 					
				</tbody>										   			
			</table>	
	<!--  <div id="role">${role} </div>		-->	
			
		
