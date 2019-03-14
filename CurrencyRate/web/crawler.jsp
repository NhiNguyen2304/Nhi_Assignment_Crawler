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
                <a href="loginPage.jsp">Xin chào, ${sessionScope.LOGINNAMESESSION}</a> 
            </div>
        </div>
        <nav class="navbar">
            <div class="col-12 logo">
                <a class="" href="#"><img src="images/header-logo.png" alt=""></a>
                <!--
                <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarResponsive" aria-controls="navbarResponsive" aria-expanded="false" aria-label="Toggle navigation">
                  <span class="navbar-toggler-icon"></span>
                </button>
                -->
            </div>


            <div class="navbar-collapse col-12" id="navbarResponsive">
                <ul class="navbar-nav">
                    <li class="col-2 active">
                        <a class="nav-link" href="home.jsp">Trang chủ
                            <span class="sr-only">(current)</span>
                        </a>
                    </li>
                    <li class="col-2">
                        <a class="nav-link" href="home.jsp">NGOẠI TỆ</a>
                    </li>
                    <li class="col-2">
                        <a class="nav-link" href="home.jsp">VÀNG</a>
                    </li>

                </ul>
            </div>



        </nav>


        <div class="section-heading">
            <div class="line-dec"></div>
            <h1>Thu thập dữ liệu</h1>
        </div>
            <div class="container col-3"></div>
        <div class="container col-5">
            <form action="MainServlet" name="Crawl" method="POST">
                <button type="submit"  value="CrawlCurrency" name="btAction">Ngoại tệ</button>
                <button type="submit"  value="CrawlGold" name="btAction">Vàng</button>

            </form>
            <h2>${sessionScope.CHECKS}</h2>
            <h3>${sessionScope.CHECKF}</h3>

        </div>


        <div class="col-12 footer">


            <div class="copyright-text">
                <p>Copyright &copy; 2019 Company Name 

                    - Design: <a rel="nofollow" href="https://www.facebook.com/NhiNguyen">NhiNguyen</a></p>
            </div>
        </div>

    </body>
</html>