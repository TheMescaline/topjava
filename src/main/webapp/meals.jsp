<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Meals</title>
</head>
<body>
<form action="meals">
    <input type="number" min="0" name="calories">
    <input type="submit" value="Filter">
</form>
<table>
    <tr>
        <td>Meal</td>
        <td>Date</td>
        <td>Time</td>
        <td>Calories</td>
    </tr>

    <c:forEach items="${meals}" var="meal">
        <jsp:useBean id="meal" type="ru.javawebinar.topjava.model.MealTo"/>
        <tr>
            <td >${meal.description}</td>
            <td>${meal.dateTime.toLocalDate()}</td>
            <td>${meal.dateTime.toLocalTime()}</td>
            <td style="background-color: <%if (meal.isExcess()) {%> red<% } else {%> green <%}%>">${meal.calories}</td>
        </tr>
    </c:forEach>

</table>
</body>
</html>