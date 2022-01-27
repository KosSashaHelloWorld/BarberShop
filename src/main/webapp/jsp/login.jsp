<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="../imports.jspf" %>
<fmt:message key="login.error.password" var="error_password"/>
<fmt:message key="login.error.username" var="error_username"/>
<fmt:message key="login.username" var="username"/>
<fmt:message key="login.password" var="password"/>
<fmt:message key="login.remember" var="remember"/>
<fmt:message key="login.title" var="title"/>
<!DOCTYPE html>
<html>
<head>
    <title>${title}</title>
</head>
<body>
<h1>Log In</h1>
<form method="post" action="controller">
    <label for="username">${username}</label>
    <input type="text" name="username" id="username" placeholder="${username}" required>
    <br/>
    <label for="password">${password}</label>
    <input type="password" name="password" id="password" placeholder="${password}" required>
    <br/>
    <input type="submit" value="Log in">
    <label>
        <input type="checkbox" name="remember">${remember}
    </label>
    <label>
        <input type="hidden" name="command" value="LOGIN">
    </label>
</form>
<form method="post" action="controller">
    <label>
        <input type="hidden" name="command" value="SIGNUP_MENU">
    </label>
    <input type="submit" value="Sign up">
</form>
<br/>
<c:choose>
    <c:when test="${requestScope.error eq 'username_error'}">
        <label>
                ${error_username}
        </label>
    </c:when>
    <c:when test="${requestScope.error eq 'password_error'}">
        <label>
                ${error_password}
        </label>
    </c:when>
</c:choose>
</body>
</html>