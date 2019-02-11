<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Edit Meal</title>
    <jsp:useBean id="meal" type="ru.javawebinar.topjava.model.Meal" scope="request"/>
</head>
<body>
<h3><a href="meals">Back to meals list</a></h3>
<form action="meals" method="post">
    <input type="hidden" name="id" value="${meal.id}">
    <label>Date</label><input type="date" name="mealDate" value="${meal.dateTime.toLocalDate()}" required>
    <hr/>
    <label>Time</label><input type="time" name="mealTime" value="${meal.dateTime.toLocalTime()}" required>
    <hr/>
    <label>Meal</label><input type="text" name="description" value="${meal.description}" required>
    <hr/>
    <label>Calories</label><input type="number" min="0" name="calories" value="${meal.calories}" required>
    <hr/>
    <input type="submit" value="Save meal">
</form>
</body>
</html>