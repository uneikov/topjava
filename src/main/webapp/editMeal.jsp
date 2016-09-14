<jsp:useBean id="mealCal" scope="request" type="java.lang.Integer"/>
<jsp:useBean id="mealType" scope="request" type="java.lang.String"/>
<jsp:useBean id="mealDate" scope="request" type="java.time.LocalDate"/>
<jsp:useBean id="mealTime" scope="request" type="java.time.LocalTime"/>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <title>Add meal</title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
    <script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
</head>
<body>
<div class="container">
    <h2 align="center">Edit meal</h2>
    <br>
    <form  class="form-inline" action="meals" method="post">
        <div class="form-group">
            <label for="d">Дата:</label>
            <input type="date" class="form-control" id="d" name="mealDate" value="${mealDate}">
        </div><div class="form-group">
        <label for="t">Время:</label>
        <input type="time" class="form-control" id="t" name="mealTime" value="${mealTime}">
    </div><div class="form-group">
        <label for="info">Описание:</label>
        <input type="text" class="form-control" id="info" name="mealType" value="${mealType}">
    </div><div class="form-group">
        <label for="cal">Количество калорий:</label>
        <input type="number" class="form-control" id="cal" name="mealCal" value="${mealCal}">
    </div>
        <input type="hidden" name="action" value="editMeal" />
        <input type="hidden" name="id" value="${meal.id}" />
        <button type="submit" class="btn btn-default">Изменить</button>
    </form>
    <h2><a href="index.html">Home</a></h2>
</div>
</body>
</html>