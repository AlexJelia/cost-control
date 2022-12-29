<%@ page import="com.alex.util.TimeUtil" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<html>
<head>
    <title>Meal list</title>
    <style>
        .normal {
            color: green;
        }
        .excess {
            color: red;
        }
    </style>
</head>
<body>
<section>
    <h3><a href="index.html">Home</a></h3>
    <hr/>
    <h2>Costs</h2>
    <a href="costs?action=create">Add Meal</a>
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
            <jsp:useBean id="cost" type="com.alex.model.CostTo"/>
            <tr class="${cost.excess ? 'excess' : 'normal'}">
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