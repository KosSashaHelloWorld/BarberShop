<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE>
<html lang="en">
<head>
    <title>Personal Area</title>
</head>
<body>
<form action="controller" method="post">
    User ID: ${sessionScope.user_id}
    <br/>
    Username: ${sessionScope.username}
    <br/>
    Status: ${sessionScope.user_role}
    <br/>
    First name: ${sessionScope.first_name}
    <br/>
    Second name: ${sessionScope.second_name}
    <br/>
    Surname: ${sessionScope.sur_name}
    <br/>
    Email: ${sessionScope.user_email}
    <br/>
    Phone: ${sessionScope.user_phone}
    <br/>
    Description: ${sessionScope.user_desc}
    <br/>
    <input type='submit' value='Booking'>
    <input type="text" name="command" value="SHOW_CLIENT_BOOK" hidden>
</form>
<form action="controller" method="post">
    <input type="submit" value="Add book">
    <input type="text" name="command" value="ADD_BOOK" hidden>
</form>
<c:if test="${requestScope.items != null}">
<table>
    <thead>
    <tr>
        <th>Client ID</th>
        <th>Barber ID</th>
        <th>Service ID</th>
        <th>Date</th>
        <th>Time</th>
        <th>Status</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${requestScope.items}" var="item">
        <tr>
            <td>${item.clientId}</td>
            <td>${item.barberId}</td>
            <td>${item.serviceId}</td>
            <td>${item.bookDate}</td>
            <td>${item.bookTime}</td>
            <td>${item.active}</td>
        </tr>
    </c:forEach>
    </tbody>
    <tfoot>
    <tr>
        <td colspan="6">Total books: ${requestScope.items.size()}</td>
    </tr>
    </tfoot>
</table>
</c:if>
</body>
</html>
