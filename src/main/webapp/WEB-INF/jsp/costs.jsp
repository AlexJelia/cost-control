<%@ page import="com.alex.util.TimeUtil" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<html>
<jsp:include page="fragments/headTag.jsp"/>
<body>
<jsp:include page="fragments/bodyHeader.jsp"/>

<section>
    <h3><spring:message code="cost.title"/></h3>

    <form method="get" action="costs/filter">
        <dl>
            <dt><spring:message code="cost.startDate"/>:</dt>
            <dd><input type="date" name="startDate" value="${param.startDate}"></dd>
        </dl>
        <dl>
            <dt><spring:message code="cost.endDate"/>:</dt>
            <dd><input type="date" name="endDate" value="${param.endDate}"></dd>
        </dl>
        <dl>
            <dt><spring:message code="cost.startTime"/>:</dt>
            <dd><input type="time" name="startTime" value="${param.startTime}"></dd>
        </dl>
        <dl>
            <dt><spring:message code="cost.endTime"/>:</dt>
            <dd><input type="time" name="endTime" value="${param.endTime}"></dd>
        </dl>
        <button type="submit"><spring:message code="cost.filter"/></button>
    </form>
    <hr>
    <a href="costs/create"><spring:message code="cost.add"/></a>
    <hr>
    <table border="1" cellpadding="8" cellspacing="0">
        <thead>
        <tr>
            <th><spring:message code="cost.dateTime"/></th>
            <th><spring:message code="cost.description"/></th>
            <th><spring:message code="cost.costs"/></th>
            <th></th>
            <th></th>
        </tr>
        </thead>
        <c:forEach items="${costs}" var="cost">
            <jsp:useBean id="cost" scope="page" type="com.alex.to.CostTo"/>
            <tr data-costExcess="${cost.excess}">
                <td>
                    <%=TimeUtil.toString(cost.getDateTime())%>
                </td>
                <td>${cost.description}</td>
                <td>${cost.cost}</td>
                <td><a href="costs/update?id=${cost.id}"><spring:message code="common.update"/></a></td>
                <td><a href="costs/delete?id=${cost.id}"><spring:message code="common.delete"/></a></td>
            </tr>
        </c:forEach>
    </table>
</section>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>