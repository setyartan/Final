<%--
  Created by IntelliJ IDEA.
  User: User
  Date: 12.03.2020
  Time: 16:08
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title><fmt:message key="title.search"/></title>
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
                    <form id="1" action="controller" method="post">
                        <tr>
                            <td class="td-center-search" colspan="3">
                                <h2 class="text-style"><fmt:message key="search.search"/></h2>
                            </td>
                        </tr>

                        <tr>
                            <td class="td-center" colspan="3">
                                <label>
                                    <input class="input-login-style" width="30%" name="search">
                                </label>
                            </td>
                        </tr>

                        <tr>
                            <td class="td-center-search-2">
                                <p class="text-style"><fmt:message key="search.sort_by_name"/></p>
                                <label>
                                    <select name="sortingByName">
                                        <option value="None"><fmt:message key="search.none"/></option>
                                        <option value="Up"><fmt:message key="search.up"/></option>
                                        <option value="Down"><fmt:message key="search.down"/></option>
                                    </select>
                                </label>
                            </td>
                            <td class="td-center-search-2">
                                <p class="text-style"><fmt:message key="search.sort_by_theme"/></p>
                                <label>
                                    <input class="input-editing-style" name="theme">
                                </label>
                            </td>

                            <td class="td-center-search-2">
                                <p class="text-style"><fmt:message key="search.sort_by_price"/></p>
                                <label>
                                    <select name="sortingByPrice">
                                        <option value="None"><fmt:message key="search.none"/></option>
                                        <option value="Up"><fmt:message key="search.up"/></option>
                                        <option value="Down"><fmt:message key="search.down"/></option>
                                    </select>
                                </label>
                            </td>
                        </tr>
                        <tr>
                            <td class="td-center" colspan="3">
                                <input type="hidden" name="command" value="search"/>
                                <input class="replenish-style" type="submit" value="<fmt:message key="search.search"/>">
                            </td>
                        </tr>
                        <c:choose>
                            <c:when test="${user != null && user.roleId != 0}">
                                <tr>
                                    <td class="td-center" colspan="3">
                                        <h4 class="text-style"><fmt:message key="search.balance"/>: ${user.account}</h4>
                                    </td>
                                </tr>
                            </c:when>
                        </c:choose>
                    </form>
                </table>

                <table class="table-3">
                    <tr class="table-3-tr">
                        <th class="table-3-td">
                            <h3 class="text-style-table"><fmt:message key="field.name"/></h3>
                        </th>
                        <th class="table-3-td">
                            <h3 class="text-style-table"><fmt:message key="field.price"/></h3>
                        </th>
                        <c:choose>
                            <c:when test="${user != null && user.roleId != 0}">
                                <th class="table-3-td">
                                    <h3 class="text-style-table"><fmt:message key="search.subscription"/></h3>
                                </th>
                            </c:when>
                        </c:choose>
                    </tr>
                    <c:choose>
                        <c:when test="${list.get(0).price != -1}">
                            <c:forEach var="list1" items="${list}">
                                <form id="2" action="controller" method="post">
                                    <tr class="table-3-tr">
                                        <td class="table-3-td">
                                            <input type="hidden" name="periodical" value="${list1.name}"/>
                                            <input type="hidden" name="user" value="${user.login}">
                                            <h4 class="text-style-table">${list1.name}</h4>
                                        </td>
                                        <td class="table-3-td">
                                            <h4 class="text-style-table">${list1.price}</h4>
                                        </td>
                                        <c:choose>
                                            <c:when test="${user != null && user.roleId != 0}">
                                                <td class="table-3-td">
                                                    <c:choose>
                                                        <c:when test="${statuses.contains(list1)}">
                                                            <input class="text-style-table" type="hidden" name="command"
                                                                   value="unsubscribe"/>
                                                            <input type="submit"
                                                                   value="<fmt:message key="search.unsubscribe"/>">
                                                        </c:when>
                                                        <c:otherwise>
                                                            <input class="text-style-table" type="hidden" name="command"
                                                                   value="subscribe"/>
                                                            <input type="submit"
                                                                   value="<fmt:message key="search.subscribe"/>">
                                                        </c:otherwise>
                                                    </c:choose>
                                                </td>
                                            </c:when>
                                        </c:choose>
                                    </tr>
                                </form>
                            </c:forEach>
                        </c:when>
                        <c:otherwise>
                            <tr class="table-3-tr">
                                <c:choose>
                                    <c:when test="${user != null && user.roleId != 0}">
                                        <td class="table-3-td" colspan="3">
                                    </c:when>
                                    <c:otherwise>
                                        <td class="table-3-td" colspan="2">
                                    </c:otherwise>
                                </c:choose>
                                        <h4 class="text-style-table"><fmt:message key="search.no_periodicals"/></h4>
                                </td>
                            </tr>
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
