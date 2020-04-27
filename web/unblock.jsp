<%--
  Created by IntelliJ IDEA.
  User: User
  Date: 17.03.2020
  Time: 13:44
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title><fmt:message key="title.unblock"/></title>
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
                      action="${pageContext.request.contextPath}/login.jsp" method="post">
                    <input class="logout-style" type="submit" name="back" value="<fmt:message key="button.back"/>">
                </form>
                <h2 class="text-style-topic"><fmt:message key="unblock.all_blocked_users"/></h2>
                <table class="table-3">
                    <tr class="table-3-tr">
                        <th class="table-3-td">
                            <h3 class="text-style-table"><fmt:message key="field.name"/></h3>
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
                        <c:when test="${empty allBlockedUsers}">
                            <tr class="table-3-tr">
                                <td class="table-3-td" colspan="4">
                                    <h4 class="text-style"><fmt:message key="unblock.no_blocked_users"/></h4>
                                </td>
                            </tr>
                        </c:when>
                        <c:otherwise>
                            <c:forEach var="users" items="${allBlockedUsers}">
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
            </div>
        </div>
        <div class="content-div-2">
            <div class="inner-content">
                <br><br><br>
                <table class="table-2">
                    <tbody class="body-style">
                    <tr>
                        <td class="td-center">
                            <form id="login_form" action="controller" method="post">
                                <input type="hidden" name="command" value="unblockUser"/>
                                <fieldset class="fieldset-style">
                                    <legend><fmt:message key="unblock.users_name"/></legend>
                                    <label>
                                        <input class="input-login-style" required="required" name="login"/>
                                    </label><br/>
                                </fieldset>
                                <br>
                                <input class="replenish-style" type="submit"
                                       value="<fmt:message key="unblock.unblock"/>">
                            </form>
                        </td>
                    </tr>
                    </tbody>
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
