<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="../imports.jspf" %>
<fmt:message key="main.login" var="login"/>
<fmt:message key="main.signup" var="signup"/>
<fmt:message key="main.title" var="title"/>
<fmt:message key="main.error.command" var="command_error"/>
<fmt:message key="main.error.manipulation" var="manipulation_error"/>
<!DOCTYPE html>
<html>
<head>
    <title>${title}</title>
</head>
<body>
<form action="controller" method="post">
    <input type="submit" value="${login}">
    <input type="hidden" name="command" value="LOGIN_MENU">
</form><br/>
<form action="controller" method="post">
    <input type="submit" value="${signup}">
    <input type="hidden" name="command" value="SIGNUP_MENU">
</form><br/>
<c:choose>
    <c:when test="${error eq 'error_command'}">
        <label>
                ${command_error}
        </label><br/>
    </c:when>
    <c:when test="${error eq 'error_manipulation'}">
        <label>
            ${manipulation_error}
        </label>
    </c:when>
</c:choose>
</body>
</html>
