<%@ include file="../../imports.jspf"%>
<fmt:message key="client.person.title" var="title"/>
<fmt:message key="client.person.user_id" var="user_id"/>
<fmt:message key="client.person.username" var="username"/>
<fmt:message key="client.person.status" var="status"/>
<fmt:message key="client.person.first_name" var="first_name"/>
<fmt:message key="client.person.second_name" var="second_name"/>
<fmt:message key="client.person.surname" var="surname"/>
<fmt:message key="client.person.email" var="email"/>
<fmt:message key="client.person.phone" var="phone"/>
<fmt:message key="client.person.book.show_book" var="show_book"/>
<fmt:message key="client.person.book.add_book" var="add_book"/>
<fmt:message key="client.person.book.barber" var="barber"/>
<fmt:message key="client.person.book.service" var="service"/>
<fmt:message key="client.person.book.date" var="date"/>
<fmt:message key="client.person.book.time" var="time"/>
<fmt:message key="client.person.book.state" var="state"/>
<fmt:message key="client.person.book.state.active" var="active"/>
<fmt:message key="client.person.book.state.inactive" var="inactive"/>
<fmt:message key="client.person.book.no_books" var="no_books"/>
<!DOCTYPE>
<html>
<head>
    <title>${title}</title>
</head>
<body>
<form action="controller" method="post">
    ${user_id}: ${sessionScope.user_id}
    <br/>
    ${username}: ${sessionScope.username}
    <br/>
    ${status}: ${sessionScope.user_role}
    <br/>
    ${first_name}: ${sessionScope.first_name}
    <br/>
    ${second_name}: ${sessionScope.second_name}
    <br/>
    ${surname}: ${sessionScope.sur_name}
    <br/>
    ${email}: ${sessionScope.user_email}
    <br/>
    ${phone}: ${sessionScope.user_phone}
    <br/>
    <input type='submit' value='${show_book}'>
    <input type="text" name="command" value="SHOW_CLIENT_BOOK" hidden>
</form>
<c:choose>
    <c:when test="${requestScope.books.size() > 0}">
        <table>
            <thead>
            <tr>
                <th>${barber}</th>
                <th>${service}</th>
                <th>${date}</th>
                <th>${time}</th>
                <th>${state}</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${requestScope.books}" var="book">
                <tr>
                    <td>${book.barberId}</td>
                        <%--todo: make barber clickable--%>
                    <td>${book.serviceId}</td>
                    <td>${book.bookDate}</td>
                    <td>${book.bookTime}</td>
                    <td>${book.active? active : inactive}</td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </c:when>
    <c:when test="${requestScope.books.size() == 0}">
        <label>
            ${no_books}
            <form action="controller" method="post">
                <input type="submit" value="${add_book}">
                <input type="text" name="command" value="ADD_BOOK_MENU" hidden>
            </form>
        </label>
    </c:when>
</c:choose>
</body>
</html>
