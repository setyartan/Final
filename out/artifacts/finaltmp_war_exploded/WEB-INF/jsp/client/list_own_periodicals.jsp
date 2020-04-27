<%--
  Created by IntelliJ IDEA.
  User: User
  Date: 07.03.2020
  Time: 12:12
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <title><fmt:message key="title.account"/></title>
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
                <table class="table-1">
                    <tr>
                        <td class="td-left">
                            <h1 class="text-style">${user.firstName} ${user.lastName}</h1>
                            <br>
                        </td>
                        <td class="td-right">
                            <form id="log_out" action="controller" method="post">
                                <input type="hidden" value="logout" name="command">
                                <input class="replenish-style" type="submit" value="<fmt:message key="button.logout"/>">
                            </form>
                        </td>
                    </tr>
                    <tr>
                        <td class="td-left"><p class="text-style"><fmt:message key="user.my_balance"/>: ${user.account}</p>
                            <form id="replenish" action="replenish.jsp" method="post">
                                <input class="logout-style" type="submit" name="replenish" value="<fmt:message key="replenish.replenish"/>">
                            </form>
                        </td>
                    </tr>
                </table>
                <br>

                <h2 class="text-style-topic"><fmt:message key="list.my_periodicals"/></h2>
                <br>

                <table class="table-3">
                    <tr class="table-3-tr">
                        <th class="table-3-td">
                            <h3 class="text-style-table"><fmt:message key="field.name"/></h3>
                        </th>
                        <th class="table-3-td">
                            <h3 class="text-style-table"><fmt:message key="field.price"/></h3>
                        </th>
                    </tr>
                    <c:choose>
                        <c:when test="${empty ownPeriodicals}">
                            <tr class="table-3-tr">
                                <td class="table-3-td" colspan="2">
                                    <h4 class="text-style"><fmt:message key="list_no_own_periodicals"/></h4>
                                </td>
                            </tr>
                        </c:when>
                        <c:otherwise>
                            <c:forEach var="list" items="${ownPeriodicals}">
                                <tr class="table-3-tr">
                                    <td class="table-3-td">
                                        <h4 class="text-style">${list.name}</h4>
                                    </td>
                                    <td class="table-3-td">
                                        <h4 class="text-style">${list.price}</h4>
                                    </td>
                                </tr>
                            </c:forEach>
                        </c:otherwise>
                    </c:choose>
                </table>
            </div>
        </div>
        <div class="sidebar-2">
            <div class="inner-sidebar-2"></div>
        </div>
    </div>
    <div class="footer">
        <div class="inner-footer"></div>
    </div>
</div>

</body>

</html>