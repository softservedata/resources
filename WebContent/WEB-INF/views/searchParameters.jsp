<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c' %>

<c:if test="${not empty discreteParameters or not empty linearParameters}">
    <div><h5><i>Оберіть параметри пошуку:</i></h5></div>
</c:if>
<c:if test="${not empty discreteParameters}">
    <div id="discreteParameters" class="container">
        <c:forEach items="${discreteParameters}" var="discreteParameter">
            <div class="col-md-6 discreteParameter" style="padding-bottom: 4px;"
                 param_id="${discreteParameter.discreteParameterId}">
                <label class="col-md-3">${discreteParameter.description}</label>
                <span class="col-md-2">
                    <select class="form-control compare">
                        <option value="less"> <</option>
                        <option value="equal" selected> =</option>
                        <option value="greater"> ></option>
                    </select>
                </span>
                <span class="col-md-6">
                    <input type="text" class="form-control value"/>
                </span>
                <label class="col-md-1">${discreteParameter.unitName}</label>
            </div>
        </c:forEach>
    </div>
</c:if>
<c:if test="${not empty linearParameters}">
    <div id="linearParameters" class="container">
        <c:forEach items="${linearParameters}" var="linearParameter">
            <div class="col-md-6 linearParameter" style="padding-bottom: 4px;"
                 param_id="${linearParameter.linearParameterId}">
                <label class="col-md-3">${linearParameter.description}:</label>
                <span class="col-md-6 col-md-offset-2">
                    <input type="text" class="form-control value"/>
                </span>
                <label class="col-md-1">${linearParameter.unitName}</label>
                <%--<label class="col-md-1">${linearParameter.description}:</label>--%>
                <%--<span class="col-md-5">--%>
                    <%--<span class="col-md-2"> початок </span>--%>
                    <%--<span class="col-md-2">--%>
                        <%--<select class="form-control">--%>
                            <%--<option value="less"> <</option>--%>
                            <%--<option value="equal" selected> =</option>--%>
                            <%--<option value="greater"> ></option>--%>
                        <%--</select>--%>
                    <%--</span>--%>
                    <%--<span class="col-md-6">--%>
                        <%--<input type="text" class="form-control"/>--%>
                    <%--</span>--%>
                    <%--<label class="col-md-2">${linearParameter.unitName}</label>--%>
                <%--</span>--%>
                <%--<span class="col-md-5 col-md-offset-1">--%>
                    <%--<span class="col-md-2"> кінець </span>--%>
                    <%--<span class="col-md-2">--%>
                        <%--<select class="form-control">--%>
                            <%--<option value="less"> <</option>--%>
                            <%--<option value="equal" selected> =</option>--%>
                            <%--<option value="greater"> ></option>--%>
                        <%--</select>--%>
                    <%--</span>--%>
                    <%--<span class="col-md-6">--%>
                        <%--<input type="text" class="form-control"/>--%>
                    <%--</span>--%>
                    <%--<label class="col-md-2">${linearParameter.unitName}</label>--%>
                <%--</span>--%>
            </div>
        </c:forEach>
    </div>
</c:if>
<c:if test="${not empty discreteParameters or not empty linearParameters}">
    <div class="container"><button class="search btn btn-success" id="search"><span class="glyphicon glyphicon-search"></span> Пошук</button></div>
</c:if>