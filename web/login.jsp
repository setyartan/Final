<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: User
  Date: 07.03.2020
  Time: 20:36
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title><fmt:message key="title.login"/></title>
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
                <table class="table-2">
                    <tbody class="body-style">
                    <tr>
                        <td class="td-center">
                            <form id="login_form" action="controller" method="post">
                                <input type="hidden" name="command" value="login"/>

                                <fieldset class="fieldset-style">
                                    <legend><fmt:message key="field.login"/></legend>
                                    <label>
                                        <input class="input-login-style" required="required" name="login"/>
                                    </label><br/>
                                </fieldset>
                                <br>
                                <fieldset class="fieldset-style">
                                    <legend><fmt:message key="field.password"/></legend>
                                    <label>
                                        <input class="input-login-style" required="required" type="password"
                                               name="password"/>
                                    </label>
                                </fieldset>
                                <br>
                                <input class="replenish-style" type="submit" value="<fmt:message key="login.loggingin"/>">
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
