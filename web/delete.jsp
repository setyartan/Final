<%--
  Created by IntelliJ IDEA.
  User: User
  Date: 16.03.2020
  Time: 12:57
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title><fmt:message key="title.delete"/></title>
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
                <h2 class="text-style-topic"><fmt:message key="delete.all_periodicals"/></h2>
                <table class="table-3">
                    <tr class="table-3-tr">
                        <th class="table-3-td">
                            <h3 class="text-style-table"><fmt:message key="field.id"/></h3>
                        </th>
                        <th class="table-3-td">
                            <h3 class="text-style-table"><fmt:message key="field.name"/></h3>
                        </th>
                    </tr>
                    <c:choose>
                        <c:when test="${empty periodicals}">
                            <tr class="table-3-tr">
                                <td class="table-3-td" colspan="2">
                                    <h4 class="text-style"><fmt:message key="delete.no_periodicals_to_delete"/></h4>
                                </td>
                            </tr>
                        </c:when>
                        <c:otherwise>
                            <c:forEach var="periodicals" items="${periodicals}">
                                <tr class="table-3-tr">
                                    <td class="table-3-td">
                                        <h4 class="text-style">${periodicals.id}</h4>
                                    </td>
                                    <td class="table-3-td">
                                        <h4 class="text-style">${periodicals.name}</h4>
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
                <br><br>
                <table class="table-2">
                    <tbody class="body-style">
                    <tr>
                        <td class="td-center">
                            <form id="login_form" action="controller" method="post">
                                <input type="hidden" name="command" value="deletePeriodical"/>
                                <fieldset class="fieldset-style">
                                    <legend><fmt:message key="delete.id"/></legend>
                                    <label>
                                        <input class="input-login-style" required="required" name="id"/>
                                    </label><br/>
                                </fieldset>
                                <br>
                                <input class="replenish-style" type="submit" value="<fmt:message key="delete.delete"/>">
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
