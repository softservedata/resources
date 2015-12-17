<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>

<div>
    <table>
        <tr>
            <th><spring:message code="label.user.fullname"/></th>
            <th><spring:message code="label.user.email"/></th>
            <th><spring:message code="label.user.address"/></th>
            <th><spring:message code="label.user.role"/></th>
        </tr>

        <c:if test="${not empty userList}">
            <c:forEach items="${userList}" var="user">
                <tr>
                    <td>${user.lastName}+" "+${user.firstName}+" "+${user.middleName}</td>
                    <td>${user.email}</td>
                    <td>
                        <c:forEach items="${user.address}" var="add">
                            ${add.postCode}+" "+${add.city}+", "+${add.street}+" "+${add.building}
                            <br/>
                        </c:forEach>
                    </td>
                    <td>${user.role}</td>

                </tr>
            </c:forEach>
        </c:if>

    </table>
</div>