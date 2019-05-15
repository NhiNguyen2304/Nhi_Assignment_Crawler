<%-- 
    Document   : errorPage
    Created on : Mar 2, 2019, 4:36:00 PM
    Author     : admin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Lỗi</title>
    </head>
    <body>
        <h1>Có lỗi xảy ra</h1>
        <h2><font color="red">${requestScope.ERROR}</font></h2>
        <p><a href="loginPage.jsp">Tài khoản hoặc mật khẩu không đúng vui lòng thử lại.</a></p>
    </body>
</html>
