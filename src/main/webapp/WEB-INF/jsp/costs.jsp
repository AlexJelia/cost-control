<%@ page import="com.alex.util.TimeUtil" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<html>
<head>
    <title>Costs</title>
    <link rel="stylesheet" href="css/style.css">
</head>
<body>
<section>
    <h3><a href="index.jsp">Home</a></h3>
    <hr/>
    <h2>Costs</h2>
   <%-- get because dont change statement and dont reset by f5--%>
    <form method="get" action="costs">
        <input type="hidden" name="action" value="filter">
        <dl>
            <dt>From Date:</dt>
            <dd><input type="date" name="startDate" value="${param.startDate}"></dd>
        </dl>
        <dl>
            <dt>To Date:</dt>
            <dd><input type="date" name="endDate" value="${param.endDate}"></dd>
        </dl>
        <dl>
            <dt>From Time:</dt>
            <dd><input type="time" name="startTime" value="${param.startTime}"></dd>
        </dl>
        <dl>
            <dt>To Time:</dt>
            <dd><input type="time" name="endTime" value="${param.endTime}"></dd>
        </dl>
        <button type="submit">Filter</button>
    </form>
    <hr/>
    <a href="costs?action=create">Add Cost</a>
    <br><br>
    <table border="1" cellpadding="8" cellspacing="0">
        <thead>
        <tr>
            <th>Date</th>
            <th>Description</th>
            <th>Cost</th>
            <th></th>
            <th></th>
        </tr>
        </thead>
        <c:forEach items="${costsList}" var="cost">
            <jsp:useBean id="cost" type="com.alex.to.CostTo"/>
            <tr data-costExcess="${cost.excess}">
                <td>
                    <%=TimeUtil.toString(cost.getDateTime())%>
                </td>
                <td>${cost.description}</td>
                <td>${cost.cost}</td>
                <td><a href="costs?action=update&id=${cost.id}">Update</a></td>
                <td><a href="costs?action=delete&id=${cost.id}">Delete</a></td>
            </tr>
        </c:forEach>
    </table>
</section>
</body>
</html>