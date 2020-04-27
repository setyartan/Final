<%--
  Created by IntelliJ IDEA.
  User: User
  Date: 07.03.2020
  Time: 12:12
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <title><fmt:message key="title.index"/></title>
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
                            <a href="create.jsp"><fmt:message key="index.new_here"/></a>
                        </td>
                        <td class="td-right">
                            <a href="login.jsp"><fmt:message key="index.my_account"/></a>
                        </td>
                    </tr>
                </table>

                <table class="table-1">
                    <tr>
                        <td class="td-center">
                            <form id="search_form" action="controller" method="post">
                                <input type="hidden" name="command" value="search">
                                <input class="search-style" type="submit" value="<fmt:message key="index.search"/>">
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
        <div class="inner-footer">
            <a href="settings.jsp">sett</a>
        </div>
    </div>
</div>

</body>
</html>