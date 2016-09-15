<jsp:useBean id="meal" scope="request" type="ru.javawebinar.topjava.model.Meal"/>
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
<br><br>
<div class="container">
    <div align="center">
            <span style="color: #337ab7; font-size:2em" class="glyphicon glyphicon-pencil">&nbsp;Edit meal</span>
    </div>
</div>
<br>
<div class="container">

    <br>
    <form  class="form-inline" action="meals" method="post">
        <div class="form-group">
            <label for="d">Дата:</label>
            <input type="date" class="form-control" id="d" name="mealDate" value="${mealDate}">
        </div><div class="form-group">
        <label for="t">&nbsp;Время:</label>
        <input type="time" class="form-control" id="t" name="mealTime" value="${mealTime}">
    </div><div class="form-group">
        <label for="info">&nbsp;Описание:</label>
        <input type="text" class="form-control" id="info" name="mealType" value="${meal.description}">
    </div><div class="form-group">
        <label for="cal">&nbsp;Количество калорий:</label>
        <input type="number" class="form-control" id="cal" name="mealCal" value="${meal.calories}">
    </div>
        <input type="hidden" name="action" value="editMeal" />
        <input type="hidden" name="id" value="${meal.id}" />
        <button type="submit" class="btn btn-default">&nbsp;Изменить</button>
    </form>
</div>
<div class="container">
    <div class="padded">
        <a href="meals?action=mealList">
            <span style="font-size:1.5em" class="glyphicon glyphicon-arrow-left">&nbsp;Back</span>
        </a>
    </div>
</div>
</body>
</html>