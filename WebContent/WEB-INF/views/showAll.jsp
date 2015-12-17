<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="en">
<head>
<title>Реєстратор</title>
	<spring:url value="/resource/css/normalize.css" var="normalizeCss" />
	<spring:url value="/resource/css/2.css" var="regCss" /> 
 	<spring:url value="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js" var="jqueryJs" /> 
	<spring:url value="/resource/js/deleteRS.js" var="delJs" />  
	
	<link href="${normalizeCss}" rel="stylesheet" />
    <link href="${regCss}" rel="stylesheet" />
     <script src="${jqueryJs}"></script>
     <script src="${delJs}"></script> 
</head>

<body>
 <a href="http://localhost:8080/resources/registrator/resourcetypes/add-resource-types" >Щоб додати новий тип ресурсу, натисніть тут</a>
 <a href="http://localhost:8080/resources/registrator/resourcetypes/add-resource-types" >Редагувати тип ресурсу</a>
 <a href="http://localhost:8080/resources/registrator/resourcetypes/add-resource-types" >Видалити тип ресурсу</a>
<br/>


  <table class="layout">
  <caption>Список всіх типів ресурсів</caption>
  <thead><tr>
  <th>ID</th>
  <th>Name</th>
  <th>Discrete parameters</th>
  <th>Linear parameters</th>
<th>Actions</th>
  </tr></thead>
  
  <tbody>
   <c:if test="${not empty listOfResourceType}">
                    <c:forEach items="${listOfResourceType}" var="restype">
               			<tr>
                       		 <td>${restype.typeId}</td> 
                        	<td>${restype.typeName}</td>
                   <td>    <c:forEach items="${restype.discreteParameters}" var="dis_param">
                          
                          <div class = "block"> 		
                        <div>опис: ${dis_param.description} </div>
                          <div>ОМ: ${dis_param.unitName} </div>
							</div>							
						                       			
                        	</c:forEach> 
                       </td>
                       
                        <td>
                		
                		<c:forEach items="${restype.linearParameters}" var="lin_param">
                         <div class = "block"> 
                          <div> опис: ${lin_param.description} </div>
                        <div> ОМ: ${lin_param.unitName} </div>
                         </div>    
                             
                        </c:forEach>
                        </td>
                       <td><a href="edit/${restype.typeName}">Edit   </a>
                <a href="delete/${restype.typeName}.json">Delete</a>
                		</tr> 
                    </c:forEach>
   </c:if>
   
  </tbody>
 </table>
 

</body>
</html>