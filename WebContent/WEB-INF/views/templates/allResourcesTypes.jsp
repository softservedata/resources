<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page contentType="text/html" pageEncoding="UTF-8" %>

<spring:url value="/resource/js/deleteRS.js" var="delJs" />

<script src="${delJs}"></script>

<div style="text-align: center;"><h4><spring:message code="label.restype.pagename"/></h4></div>

<table id="datatable" class="table display">

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
                <td><a href="registrator/resourcetypes/edit/${restype.typeName}">Edit   </a>
                    <a href="registrator/resourcetypes/delete/${restype.typeName}.json">Delete</a>
            </tr>
        </c:forEach>
    </c:if>

    </tbody>
</table>