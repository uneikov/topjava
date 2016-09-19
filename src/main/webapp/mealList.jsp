<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://topjava.javawebinar.ru/functions" %>
<jsp:useBean id="mealList" scope="request" type="java.util.List"/>
<html>
<head>
    <title>Meal list</title>
    <style>
        .filter {

            margin-left: 50px;
        }
        .container {
            width: 100%;
        }
        .normal {
            color: green;
        }

        .exceeded {
            color: red;
        }
    </style>
</head>
<body>
<h2><a href="index.html">Home</a></h2>
<h3>Meal list</h3>
<div class="container">

    <form action="meals" method="get">
    <div class="filter"><br>
        <label for="frdate"><b>From Date&nbsp;</b></label><input id="frdate" type="date" name="fromDate">
        <label for="todate"><b>To Date&nbsp;</b></label><input id="todate" type="date" name="toDate"><br><br>
        <label for="frtime"><b>From Time&nbsp;</b></label><input id="frtime" type="time" name="fromTime">
        <label for="totime"><b>To Time&nbsp;</b></label><input id="totime" type="time" name="toTime"><br><br>
        <input type="hidden" name="action" value="filter">
        <input type="hidden" name="mealList" value="${mealList}">
        <input type="submit" value="Отфильтровать">
    </div>
    </form><br><hr>

    <a href="meals?action=create">Add Meal</a><hr>

    <table border="1" cellpadding="8" cellspacing="0">
        <thead>
        <tr>
            <th>Date</th>
            <th>Description</th>
            <th>Calories</th>
            <th></th>
            <th></th>
        </tr>
        </thead>

        <c:forEach items="${mealList}" var="meal">
            <jsp:useBean id="meal" scope="page" type="ru.javawebinar.topjava.model.to.MealWithExceed"/>
            <tr class="${meal.exceed ? 'exceeded' : 'normal'}">
                <td>
                        <%--${meal.dateTime.toLocalDate()} ${meal.dateTime.toLocalTime()}--%>
                        <%--<%=TimeUtil.toString(meal.getDateTime())%>--%>
                        ${fn:formatDateTime(meal.dateTime)}
                </td>
                <td>${meal.description}</td>
                <td>${meal.calories}</td>
                <td><a href="meals?action=update&id=${meal.id}">Update</a></td>
                <td><a href="meals?action=delete&id=${meal.id}">Delete</a></td>
            </tr>
        </c:forEach>
    </table>
</div>
</body>
</html>