<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%--
  Created by IntelliJ IDEA.
  User: User
  Date: 07.03.2020
  Time: 12:12
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <title><fmt:message key="title.error"/></title>
    <link rel="stylesheet" href="style2.css">
</head>
<body>

<div class="wrapper">
    <div class="header">
        <div class="inner-header">
            <a class="href-none" href="index.jsp">
                <fmt:message key="site.name"/>
            </a>
        </div>
    </div>
    <div class="row">
        <div class="sidebar-1">
            <div class="inner-sidebar-1"></div>
        </div>
        <div class="content">
            <div class="inner-content">
                <br>
                <c:set var="error1" value="${error}" scope="session"/>
                <h1>${error1}</h1>
                <c:choose>
                    <c:when test="${error1 == '1'}">
                        <h1><fmt:message key="error.login"/></h1>
                        <a type="submit" href="index.jsp" name="Back">Back</a>
                    </c:when>
                    <c:when test="${error1 == '2'}">
                        <h1><fmt:message key="error.add"/></h1>
                        <a type="submit" href="index.jsp" name="Back">Back</a>
                    </c:when>
                    <c:when test="${error1 == '3'}">
                        <h1><fmt:message key="error.block"/></h1>
                        <a type="submit" href="index.jsp" name="Back">Back</a>
                    </c:when>
                    <c:when test="${error1 == '4'}">
                        <h1><fmt:message key="error.create_login"/></h1>
                        <a type="submit" href="index.jsp" name="Back">Back</a>
                    </c:when>
                    <c:when test="${error1 == '5'}">
                        <h1><fmt:message key="error.delete"/></h1>
                        <a type="submit" href="index.jsp" name="Back">Back</a>
                    </c:when>
                    <c:when test="${error1 == '6'}">
                        <h1><fmt:message key="error.edit"/></h1>
                        <a type="submit" href="index.jsp" name="Back">Back</a>
                    </c:when>
                    <c:when test="${error1 == '7'}">
                        <h1><fmt:message key="error.replenish"/></h1>
                        <a type="submit" href="index.jsp" name="Back">Back</a>
                    </c:when>
                    <c:when test="${error1 == '8'}">
                        <h1><fmt:message key="error.edit"/></h1>
                        <a type="submit" href="index.jsp" name="Back">Back</a>
                    </c:when>

                </c:choose>
            </div>
        </div>
    </div>
</div>
</body>
</html>
