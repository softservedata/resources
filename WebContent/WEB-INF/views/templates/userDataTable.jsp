<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>


<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<c:set var="req" value="${pageContext.request}" />
<c:set var="url">${req.requestURL}</c:set>
<c:set var="base"
	value="${fn:substring(url, 0, fn:length(url) - fn:length(req.requestURI))}${req.contextPath}/" />
	
<spring:url value="/resource/js/ajaxDataTable.js" var="ajaxDataTable" />
<script src="${ajaxDataTable}"></script>

<div class="container">
	<div class="dataTable_wrapper">
         <table class="table table-striped table-bordered table-hover" id="example">
			<thead>
				<tr>
	                                            <th>Ім'я</th>
	                                            <th>Прізвище</th>
	                                            <th>По батькові</th>
	                                            <th>Електронна пошта</th>
	                                            <th>Дії</th>
	           </tr>
			</thead>
	        <tfoot style="display: table-header-group">
	        	<tr>
		        	<th><input id="inputIndex0" class="form-control"  type="text" placeholder="Ім'я" /></th>
		        	<th><input id="inputIndex1" class="form-control"  type="text" placeholder="Прізвище" /></th>
		        	<th><input id="inputIndex2" class="form-control"  type="text" placeholder="По батькові" /></th>
		        	<th><input id="inputIndex3" class="form-control"  type="text" placeholder="Електронна пошта" /></th>
		        	<th><input type="submit" id="bth-search" class="btn  btn-primary" value="Пошук"/></th>
		        </tr>  
	        </tfoot>
<!-- 	        <tbody> -->
<!-- 	        </tbody> -->

         </table>
    </div>
</div>
	