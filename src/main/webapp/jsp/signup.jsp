<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="../imports.jspf"%>

<fmt:message key="signup.title" var="title"/>
<fmt:message key="signup.registration" var="registration"/>
<fmt:message key="signup.signup" var="signup"/>
<fmt:message key="signup.username" var="username"/>
<fmt:message key="signup.password" var="password"/>
<fmt:message key="signup.first_name" var="first_name"/>
<fmt:message key="signup.second_name" var="second_name"/>
<fmt:message key="signup.sur_name" var="sur_name"/>
<fmt:message key="signup.email" var="email"/>
<fmt:message key="signup.phone" var="phone"/>
<fmt:message key="signup.error.invalid_username" var="invalid_username"/>
<fmt:message key="signup.error.invalid_password" var="invalid_password"/>
<fmt:message key="signup.error.invalid_first_name" var="invalid_first_name"/>
<fmt:message key="signup.error.invalid_second_name" var="invalid_second_name"/>
<fmt:message key="signup.error.invalid_sur_name" var="invalid_sur_name"/>
<fmt:message key="signup.error.invalid_email" var="invalid_email"/>
<fmt:message key="signup.error.invalid_phone" var="invalid_phone"/>
<fmt:message key="signup.error.not_unique_username" var="not_unique_username"/>
<fmt:message key="signup.error.not_unique_phone" var="not_unique_phone"/>
<fmt:message key="signup.error.not_unique_email" var="not_unique_email"/>

<!DOCTYPE html>
<html>
<head>
    <title>${title}</title>
</head>
<body>
<h1>${registration}</h1>
<form action="controller" method="post">
    <label for="username">${username}</label>
    <input type="text" name="username" id="username" value="${valid_username}" placeholder="${username}">
    <c:choose>
        <c:when test="${username_error eq 'invalid'}"><div id="err_message"><b>${invalid_username}</b></div></c:when>
        <c:when test="${username_error eq 'not_unique'}"><div id="err_message"><b>${not_unique_username}</b></div></c:when>
    </c:choose>
    <br/>
    <label for="password">${password}</label>
    <input type="password" name="password" id="password" placeholder="${username}">
    <c:if test="${password_error eq 'invalid'}"><div id="err_message"><b>${invalid_password}</b></div></c:if>
    <br/>
    <label for="first_name">${first_name}</label>
    <input type="text" name="first_name" id="first_name" value="${valid_first_name}" placeholder="${first_name}">
    <c:if test="${first_name_error eq 'invalid'}"><div id="err_message"><b>${invalid_first_name}</b></div></c:if>
    <br/>
    <label for="second_name">${second_name}</label>
    <input type="text" name="second_name" id="second_name" value="${valid_second_name}" placeholder="${second_name}">
    <c:if test="${second_name_error eq 'invalid'}"><div id="err_message"><b>${invalid_second_name}</b></div></c:if>
    <br/>
    <label for="sur_name">${sur_name}</label>
    <input type="text" name="sur_name" id="sur_name" value="${valid_sur_name}" placeholder="${sur_name}">
    <c:if test="${sur_name_error eq 'invalid'}"><div id="err_message"><b>${invalid_sur_name}</b></div></c:if>
    <br/>
    <label for="email">${email}</label>
    <input type="text" name="email" id="email" value="${valid_email}" placeholder="${email}">
    <c:choose>
        <c:when test="${email_error eq 'invalid'}"><div id="err_message"><b>${invalid_email}</b></div></c:when>
        <c:when test="${email_error eq 'not_unique'}"><div id="err_message"><b>${not_unique_email}</b></div></c:when>
    </c:choose>
    <br/>
    <label for="phone">${phone}</label>
    <input type="text" name="phone" id="phone" value="${valid_phone}" placeholder="${phone}">
    <c:choose>
        <c:when test="${phone_error eq 'invalid'}"><div id="err_message"><b>${invalid_phone}</b></div></c:when>
        <c:when test="${phone_error eq 'not_unique'}"><div id="err_message"><b>${not_unique_phone}</b></div></c:when>
    </c:choose>
    <br/>
    <input type="submit" value="${signup}">
    <input type="hidden" name="command" value="SIGNUP">
    <input type="hidden" name="user_role" value="client">
</form>
</body>
</html>
