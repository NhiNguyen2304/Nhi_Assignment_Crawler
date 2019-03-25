<%-- 
    Document   : index
    Created on : Mar 1, 2019, 8:12:14 AM
    Author     : admin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Test</title>
    </head>
    <body>
        <c:forEach var="list" items="${requestScope.COM}" varStatus="counter">
            <p>${counter.count}</p>
            <c:if test="${list.comparable lt 0}">
                <p>Giam</p>
                <p>${list.comparable}</p>
            </c:if>
            <c:if test="${list.comparable gt 0}">
                <p>Tang</p>
                <p>${list.comparable}</p>
            </c:if>
           
        </c:forEach>
    </body>
</html>
