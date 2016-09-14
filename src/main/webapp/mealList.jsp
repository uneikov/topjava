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
    <script src="css/bootstrap-extensions.css"></script>

</head>
<body>
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
<br><br><br><br>

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
            <%-- Choose color for the current table row --%>
            <c:if test="${meal.exceed==true}">
                <c:set var="row_color" value="red"/>
            </c:if>
            <%-- Print table row to screen --%>
            <tr style="color: ${row_color}">
                <td>
                    <tags:localDate date="${meal.dateTime}" pattern="dd.MM.yyy HH:mm"/>
                </td>
                <td>${meal.description}</td>
                <td>${meal.calories}</td>
                <td width="15%">
                    <span>&nbsp;&nbsp;&nbsp;</span>
                    <a href="${pageContext.request.contextPath}/meals?action=edit&id=${meal.id}">
                        <i class="glyphicon glyphicon-pencil"></i>
                    </a>
                    <span>&nbsp;&nbsp;&nbsp;</span>
                    <a href="${pageContext.request.contextPath}/meals?action=delete&date=${meal.dateTime}&desc=${meal.description}&cal=${meal.calories}">
                        <i class="glyphicon glyphicon-trash"></i>
                    </a>
                </td>
            </tr>
            <%-- Set row color to default --%>
            <c:set var="row_color" value="green"/>
        </c:forEach>
        <tr>
            <td></td><td></td><td></td>
            <td>
                &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                <a href="meals?action=add"><i class="glyphicon glyphicon-plus"></i></a>
            </td>
        </tr>
        </tbody>

    </table>
    </div>
</div>
</body>
</html>
<%--
  <fmt:parseDate value="${meal.dateTime}" pattern="yyyy-MM-dd'T'HH:mm" var="parsedDate" type="both"/>
  <fmt:formatDate value="${parsedDate}"  pattern="dd.MM.yyy HH:mm" />

  <c:forEach var="meal_ex" items="${requestScope.mealListWithExceeded}">
      <c:if test="${meal.dateTime==meal_ex.dateTime && meal_ex.exceed==true}">
          <c:set var="row_color" value="red"/>
      </c:if>
  </c:forEach>

--%>
