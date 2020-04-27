<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<%--
  Created by IntelliJ IDEA.
  User: User
  Date: 10.03.2020
  Time: 21:36
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title><fmt:message key="title.create"/></title>
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
                <table class="table-2">
                    <tbody class="body-style">
                    <tr>
                        <td class="td-center">
                            <form id="login_form" action="controller" method="post">
                                <input type="hidden" name="command" value="create"/>

                                <fieldset class="fieldset-style">
                                    <legend><fmt:message key="field.login"/></legend>
                                    <label>
                                        <input class="input-login-style" required="required" name="login" pattern="[\S\d]{8,}"/>
                                    </label>
                                    <p class="small-text"><fmt:message key="create.pattern.login"/></p>
                                    <br/>
                                </fieldset>
                                <br>
                                <fieldset class="fieldset-style">
                                    <legend><fmt:message key="field.password"/></legend>
                                    <label>
                                        <input class="input-login-style" required="required" type="password"
                                               name="password" pattern="(?=^.{8,}$)((?=.*\d)|(?=.*\W+))(?![.\n])(?=.*[A-Z])(?=.*[a-z]).*"/>
                                    </label>
                                    <p class="small-text"><fmt:message key="create.pattern.pass"/></p>
                                </fieldset>
                                <br>
                                <fieldset class="fieldset-style">
                                    <legend><fmt:message key="field.first_name"/></legend>
                                    <label>
                                        <input class="input-login-style" required="required" name="first_name"/>
                                    </label>
                                </fieldset>
                                <br/>
                                <fieldset class="fieldset-style">
                                    <legend><fmt:message key="field.last_name"/></legend>
                                    <label>
                                        <input class="input-login-style" required="required" name="last_name"/>
                                    </label>
                                </fieldset>
                                <br/>
                                <input class="replenish-style" type="submit" value="<fmt:message key="create.register"/>">
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
