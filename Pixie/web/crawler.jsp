<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <title>Pixie</title>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="stylesheet" href="layout/styles/css/index-main.css">
        <link rel="stylesheet" href="layout/styles/css/table-main.css">
        <link rel="stylesheet" href="layout/styles/css/styles.css">
        <link rel="stylesheet" href="layout/styles/css/login-page.css">

    </head>
    <body>
        <div id="pre-header" class="col-12">

            <div id="pre-header-col" class="col-12">
                <c:if test="${sessionScope.LOGINNAMESESSION != null}">
                    <p style="color: white;">Xin chào, ${sessionScope.LOGINNAMESESSION}</p> 
                     <a href="LogoutServlet">Đăng xuất</a>
                </c:if>
                <c:if test="${sessionScope.LOGINNAMESESSION == null}">
                    <a href="loginPage.jsp">Đăng nhập</a> 
                </c:if>
            </div>
        </div>
        <nav class="navbar">
            <div class="col-12">
                <link rel="stylesheet" href="layout/styles/css/nav.css">

                <a href="https://www.facebook.com/NhiNguyen" id="logo" target="_blank">PIXIE</a>

                <label for="toggle-1" class="toggle-menu"><ul><li></li> <li></li> <li></li></ul></label>
                <input type="checkbox" id="toggle-1">

                <nav>
                    <ul>
                        <li><a href="home.jsp"><i class="icon-home"></i>Tiền tệ</a></li>
                        <li><a href="gold.jsp"><i class="icon-user"></i>Giá vàng</a></li>
                        <li><a href="home.jsp"><i class="icon-thumbs-up-alt"></i>Giúp đỡ</a></li>

                    </ul>
                </nav>
            </div>
        </nav>


        <div class="section-heading">
            <div class="line-dec"></div>
            <h2 style="color: black;">Thu thập dữ liệu</h2>
        </div>
            <div class="container col-3"></div>
        <div class="container col-5">
            <form action="MainServlet" name="Crawl" method="POST">
                <button type="submit"  value="CrawlCurrency" name="btAction">Ngoại tệ</button>
                <button type="submit"  value="CrawlCurrencyToday" name="btAction">Ngoại tệ hôm nay</button>
                <button type="submit"  value="CrawlGold" name="btAction">Vàng</button>
                <button type="submit"  value="CrawlGoldToday" name="btAction">Vàng hôm nay</button>

            </form>
            <h2>${requestScope.CHECKS}</h2>
            <h3>${requestScope.CHECKF}</h3>

        </div>


        <div class="col-12 footer">


            <div class="copyright-text">
                <p>Copyright &copy; 2019 Company Name 

                    - Design: <a rel="nofollow" href="https://www.facebook.com/NhiNguyen">NhiNguyen</a></p>
            </div>
        </div>

    </body>
</html>
