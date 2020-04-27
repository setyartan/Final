<%--
  Created by IntelliJ IDEA.
  User: User
  Date: 23.03.2020
  Time: 14:01
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<form action="changeLocale.jsp" method="post">
    language:
    <select name="locale">
        <c:forEach items="${applicationScope.locales}" var="locale">
            <c:set var="selected" value="${locale.key == currentLocale ? 'selected' : '' }"/>
            <option value="${locale.key}" ${selected}>${locale.value}</option>
        </c:forEach>
    </select>
    <input type="submit" value="ok">

</form>
<a href="index.jsp">back</a>
</body>
</html>
