<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<html>
<head>
    <title>Meal List</title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
    <script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
    <link rel="stylesheet" type="text/css" href="css/bootstrap-extensions.css"/>

</head>
<body>
<%--
<nav class="navbar navbar-default navbar-fixed-top">
    <div class="container-fluid">
        <div class="navbar-header">
            <a class="navbar-brand" href="https://github.com/JavaWebinar/topjava08">TopJava</a>
        </div>
        <ul class="nav navbar-nav">
            <li><a href="index.html" class="glyphicon glyphicon-home" title="Домой">&nbsp;Home</a></li>
            <li><a href="addMeal.jsp" class="glyphicon glyphicon-plus">&nbsp;Add</a></li>
            <li><a href="mealList.jsp" class="glyphicon glyphicon-th-list">&nbsp;List</a></li>
            <li><a href="editMeal.jsp" class="glyphicon glyphicon-pencil">&nbsp;Edit</a></li>
            <li><a href="#" class="	glyphicon glyphicon-trash">&nbsp;Delete</a></li>
        </ul>
        <ul class="nav navbar-nav navbar-right">
            <li><a href="#"><span class="glyphicon glyphicon-user"></span> Sign Up</a></li>
            <li><a href="#"><span class="glyphicon glyphicon-log-in"></span> Login</a></li>
        </ul>
    </div>
</nav>
--%>

<div class="container">
    <div class="padded">
    <a href="index.html">
        <span style="font-size:1.5em" class="glyphicon glyphicon-home">&nbsp;Home</span>
    </a>
    </div>
</div>

<div class="container">
    <div class="col-md-12">
    <table class="table table-hover table-striped table-bordered">
        <c:set var="row_color" scope="page" value="green"/>
        <c:set var="action" value="" scope="page"/>
        <thead>
        <tr>
            <th>Date</th>
            <th>Description</th>
            <th>Calories</th>
            <th>Actions</th>
        </tr>
        </thead>

        <tbody>
        <c:forEach var="meal" items="${requestScope.mealListWithExceeded}">
            <tr style="color: ${meal.exceed ? "red" : "green"}">
                <td>
                    <tags:localDate date="${meal.dateTime}" pattern="dd.MM.yyy HH:mm"/>
                </td>
                <td>${meal.description}</td>
                <td>${meal.calories}</td>
                <td width="15%">
                    <span>&nbsp;&nbsp;&nbsp;</span>
                    <a href="${pageContext.request.contextPath}/meals?action=editMeal&id=${meal.id}">
                        <i class="glyphicon glyphicon-pencil"></i>
                    </a>
                    <span>&nbsp;&nbsp;&nbsp;</span>
                    <a href="${pageContext.request.contextPath}/meals?action=deleteMeal&id=${meal.id}">
                        <i class="glyphicon glyphicon-trash"></i>
                    </a>
                </td>
            </tr>
        </c:forEach>
        <tr>
            <td></td><td></td><td></td>
            <td>
                &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                <a href="meals?action=addMeal"><i class="glyphicon glyphicon-plus"></i></a>
            </td>
        </tr>
        </tbody>

    </table>
    </div>
</div>
</body>
</html>
