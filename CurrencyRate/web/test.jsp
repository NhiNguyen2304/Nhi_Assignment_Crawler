<%-- 
    Document   : test
    Created on : Dec 7, 2017, 2:03:56 PM
    Author     : admin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <form name="change" action="MainServlet">
        <input type="submit" value="Next" name="btAction" />
    </form>
    <form name="Exchage" action="MainServlet">
        <input type="text" name="txtFrom" value="${txtFrom}" /><br/>
        <input type="text" name="txtTo" value="" /><br/>
        <input type="text" name="txtResult" value="${requestScope.EX}" /><br/>
        <input type="submit" value="Exchange" name="btAction" />
    </form>
</html>
