<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Meals</title>
</head>
<body>
<h3><a href="index.html">Home</a></h3>
<form action="meals" method="get">
    <label>Enter max calories per day: <input type="number" min="0" name="calories"></label>
    <input type="submit" value="Filter">
</form>
<table>
    <tr>
        <td>Date</td>
        <td>Meal</td>
        <td>Time</td>
        <td>Calories</td>
        <td>Action</td>
    </tr>
    <c:forEach items="${mealsTo}" var="mealTo">
        <jsp:useBean id="mealTo" type="ru.javawebinar.topjava.model.MealTo"/>
        <tr style=" background-color: <%if (mealTo.isExcess()) {%> #ff6773 <%} else {%> #45fa4e <%}%>">
            <td>${mealTo.dateTime.toLocalDate()}</td>
            <td>${mealTo.description}</td>
            <td>${mealTo.dateTime.toLocalTime()}</td>
            <td >${mealTo.calories}</td>
            <td><a href="meals?action=delete&id=${mealTo.id}">Delete</a>&nbsp<a href="meals?action=edit&id=${mealTo.id}">Edit</a></td>
        </tr>
    </c:forEach>
</table>
<input type="button" onclick="window.location.href='meals?action=add'" value="Add meal">
</body>
</html>