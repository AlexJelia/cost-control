<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<html>
<jsp:include page="fragments/headTag.jsp"/>
<body>
<jsp:include page="fragments/bodyHeader.jsp"/>

<section>
    <jsp:useBean id="cost" type="com.alex.model.Cost" scope="request"/>
    <h3><spring:message code="${cost.isNew() ? 'cost.add' : 'cost.edit'}"/></h3>
    <hr>
    <form method="post" action="costs">
        <input type="hidden" name="id" value="${cost.id}">
        <dl>
            <dt><spring:message code="cost.dateTime"/>:</dt>
            <dd><input type="datetime-local" value="${cost.dateTime}" name="dateTime" required></dd>
        </dl>
        <dl>
            <dt><spring:message code="cost.description"/>:</dt>
            <dd><input type="text" value="${cost.description}" size=40 name="description" required></dd>
        </dl>
        <dl>
            <dt><spring:message code="cost.costs"/>:</dt>
            <dd><input type="number" value="${cost.cost}" name="cost" required></dd>
        </dl>
        <button type="submit"><spring:message code="common.save"/></button>
        <button onclick="window.history.back()" type="button"><spring:message code="common.cancel"/></button>
    </form>
</section>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>
