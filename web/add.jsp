<%--
  Created by IntelliJ IDEA.
  User: User
  Date: 15.03.2020
  Time: 15:59
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title><fmt:message key="title.add"/></title>
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
                <form id="adding2"
                      action="${pageContext.request.contextPath}/login.jsp" method="post">
                    <input class="logout-style" type="submit" name="back" value="<fmt:message key="button.back"/>">
                </form>
                <table class="table-2">
                    <tbody class="body-style">
                    <tr>
                        <td class="td-center">
                            <form id="login_form" action="controller" method="post">
                                <input type="hidden" name="command" value="addPeriodical"/>

                                <fieldset class="fieldset-style">
                                    <legend><fmt:message key="field.name"/></legend>
                                    <label>
                                        <input class="input-login-style" required="required" name="name"/>
                                    </label><br/>
                                </fieldset>
                                <br>
                                <fieldset class="fieldset-style">
                                    <legend><fmt:message key="field.price"/></legend>
                                    <label>
                                        <input class="input-login-style" required="required"
                                               name="price" pattern="\d+"/>
                                    </label>
                                </fieldset>
                                <br>
                                <fieldset class="fieldset-style">
                                    <legend><fmt:message key="field.theme"/></legend>
                                    <label>
                                        <input class="input-login-style" required="required" name="theme"/>
                                    </label>
                                </fieldset>
                                <br/>

                                <input class="replenish-style" type="submit" value="<fmt:message key="add.add"/>">
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
