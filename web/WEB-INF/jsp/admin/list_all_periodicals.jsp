<%--
  Created by IntelliJ IDEA.
  User: User
  Date: 07.03.2020
  Time: 12:12
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<%@ taglib prefix="ftm" uri="http://java.sun.com/jsp/jstl/fmt" %>
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
        <div class="content-div-2">
            <div class="inner-content">
                <form id="adding2"
                      action="${pageContext.request.contextPath}/index.jsp">
                    <input class="logout-style" type="submit" name="back" value="<fmt:message key="button.back"/>">
                </form>
                <h2 class="text-style-topic"><fmt:message key="list.all_periodicals"/></h2>
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
                        <c:when test="${empty periodicals}">
                            <tr class="table-3-tr">
                                <td class="table-3-td" colspan="2">
                                    <h4 class="text-style"><fmt:message key="list.no_periodicals"/></h4>
                                </td>
                            </tr>
                        </c:when>
                        <c:otherwise>
                            <c:forEach var="periodicals" items="${periodicals}">
                                <tr class="table-3-tr">
                                    <td class="table-3-td">
                                        <h4 class="text-style">${periodicals.name}</h4>
                                    </td>
                                    <td class="table-3-td">
                                        <h4 class="text-style">${periodicals.price}</h4>
                                    </td>
                                </tr>
                            </c:forEach>
                        </c:otherwise>
                    </c:choose>
                </table>
                <table class="table-1">
                    <tr>
                        <td class="td-center-33">
                            <form id="adding" action="${pageContext.request.contextPath}/add.jsp" method="post">
                                <input class="replenish-style" type="submit" name="add"
                                       value="<fmt:message key="add.add"/>">
                            </form>
                        </td>
                        <td class="td-center-33">
                            <form id="deleting" action="${pageContext.request.contextPath}/delete.jsp"
                                  method="post">
                                <input class="replenish-style" type="submit" name="delete"
                                       value="<fmt:message key="delete.delete"/>">
                            </form>
                        </td>
                        <td class="td-center-33">
                            <form id="editing" action="${pageContext.request.contextPath}/edit.jsp" method="post">
                                <input class="replenish-style" type="submit" name="edit"
                                       value="<fmt:message key="edit.edit"/>">
                            </form>
                        </td>
                    </tr>
                </table>
            </div>
        </div>
        <div class="content-div-2">
            <div class="inner-content">
                <form class="td-right" id="log_out" action="controller" method="post">
                    <input type="hidden" value="logout" name="command">
                    <input class="logout-style" type="submit" value="<fmt:message key="button.logout"/>">
                </form>
                <h2 class="text-style-topic"><fmt:message key="list.all_users"/></h2>
                <table class="table-3">
                    <tr class="table-3-tr">
                        <th class="table-3-td">
                            <h3 class="text-style-table"><fmt:message key="field.login"/></h3>
                        </th>
                        <th class="table-3-td">
                            <h3 class="text-style-table"><fmt:message key="field.first_name"/></h3>
                        </th>
                        <th class="table-3-td">
                            <h3 class="text-style-table"><fmt:message key="field.last_name"/></h3>
                        </th>
                        <th class="table-3-td">
                            <h3 class="text-style-table"><fmt:message key="field.account"/></h3>
                        </th>
                    </tr>
                    <c:choose>
                        <c:when test="${empty users}">
                            <tr class="table-3-tr">
                                <td class="table-3-td" colspan="4">
                                    <h4 class="text-style"><fmt:message key="list.no_users"/></h4>
                                </td>
                            </tr>
                        </c:when>
                        <c:otherwise>
                            <c:forEach var="users" items="${users}" begin="1">
                                <tr class="table-3-tr">
                                    <td class="table-3-td">
                                        <h4 class="text-style">${users.login}</h4>
                                    </td>
                                    <td class="table-3-td">
                                        <h4 class="text-style">${users.firstName}</h4>
                                    </td>
                                    <td class="table-3-td">
                                        <h4 class="text-style">${users.lastName}</h4>
                                    </td>
                                    <td class="table-3-td">
                                        <h4 class="text-style">${users.account}</h4>
                                    </td>
                                </tr>
                            </c:forEach>
                        </c:otherwise>
                    </c:choose>
                </table>
                <table class="table-1">
                    <tr>
                        <td class="td-center-50">
                            <form id="blocking" action="<c:url value="/block.jsp"/>" method="post">
                                <input class="replenish-style" type="submit" name="block"
                                       value="<fmt:message key="block.block"/>">
                            </form>
                        </td>
                        <td class="td-center-50">
                            <form id="unblocking" action="<c:url value="/unblock.jsp"/>" method="post">
                                <input class="replenish-style" type="submit" name="unblock"
                                       value="<fmt:message key="unblock.unblock"/>">
                            </form>
                        </td>
                    </tr>
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