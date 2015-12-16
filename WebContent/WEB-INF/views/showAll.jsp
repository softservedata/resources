<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="en">
<head>
<title>Реєстратор</title>
	<spring:url value="/resource/css/normalize.css" var="normalizeCss" />
	<spring:url value="/resource/css/1.css" var="regCss" />
<%-- 	<spring:url value="/resource/js/jquery.1.10.2.min.js" var="jqueryJs" />
	<spring:url value="/resource/js/2.js" var="2" /> --%>
	
	<link href="${normalizeCss}" rel="stylesheet" />
	<link href="${regCss}" rel="stylesheet" />
  <%--   <script src="${jqueryJs}"></script>
    <script src="${2}"></script> --%>

</head>

<body>
    <div class="container">
    <h2>Список всіх типів ресурсів: </h2>
        <div id = "mytable">
        <table id="resourceType" border = 1>
            <tr>
                <th>Id: </th>
                <th>Name: </th>
                <th>DiscreteParameters: </th>
                <th>LinearParameters: </th>
            </tr>
                <c:if test="${not empty listOfResourceType}">
                    <c:forEach items="${listOfResourceType}" var="restype">
               			<tr>
                       		 <td>${restype.typeId}</td> 
                        	<td>${restype.typeName}</td>
                       <td><c:forEach items="${restype.discreteParameters}" var="dis_param">
                            <td>${dis_param.description}</td>
                             <td>${dis_param.unitName}</td>
                        </c:forEach> </td>
                		
                		<c:forEach items="${restype.linearParameters}" var="lin_param">
                             <td>${lin_param.description}</td>
                             <td>${lin_param.unitName}</td>
                        </c:forEach>
                        
                        
                		</tr>
                    </c:forEach>
                </c:if>
           
        </table>
        </div>
    </div>  
</body>
</html>