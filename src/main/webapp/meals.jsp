<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Meals</title>
</head>
<body>
<h3><a href="index.html">Home</a></h3>
<table>
    <tr>
        <td>Date/Time</td>
        <td>Meal</td>
        <td>Calories</td>
        <td colspan="2">Action</td>
    </tr>
    <c:forEach items="${mealsTo}" var="mealTo">
        <jsp:useBean id="mealTo" type="ru.javawebinar.topjava.model.MealTo"/>
        <tr style=" background-color: ${mealTo.excess ? '#ff6773' : '#45fa4e'}">
            <td>${mealTo.dateTime.toLocalDate()} ${mealTo.dateTime.toLocalTime()}</td>
            <td>${mealTo.description}</td>
            <td >${mealTo.calories}</td>
            <td><a href="meals?action=delete&id=${mealTo.id}">Delete</a></td>
            <td><a href="meals?action=edit&id=${mealTo.id}">Edit</a></td>
        </tr>
    </c:forEach>
</table>
<input type="button" onclick="window.location.href='meals?action=add'" value="Add meal">
</body>
</html>