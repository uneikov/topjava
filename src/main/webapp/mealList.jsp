<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<html>
<head>
    <title>Meal List</title>
</head>
<body>
<h2><a href="index.html">Home</a></h2>
<h2>Meal list</h2>
<c:set var="row_color" scope="page" value="green"/>
<div class="container" style="width: 100%">
<table class="table table-hover" border="1" cellpadding="3" cellspacing="3" >

    <thead>
    <tr>
        <th width="33%">DATE</th>
        <th width="33%">DESCRIPTION</th>
        <th width="33%">CALORIES</th>
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
            <td width="33%">
                <tags:localDate date="${meal.dateTime}" pattern="dd.MM.yyy HH:mm"/>
            </td>
            <td width="33%">${meal.description}</td>
            <td width="33%">${meal.calories}</td>
        </tr>
        <%-- Set row color to default --%>
        <c:set var="row_color" value="green"/>
    </c:forEach>
    </tbody>

</table>
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
