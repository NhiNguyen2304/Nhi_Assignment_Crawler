<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/xml" prefix="x"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Pixie</title>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="stylesheet" href="layout/styles/css/index-main.css">
        <link rel="stylesheet" href="layout/styles/css/table-main.css">
        <link rel="stylesheet" href="layout/styles/css/styles.css">

    </head>
    <body>

        <div id="pre-header" class="col-12">

            <div id="pre-header-col" class="col-12">
                <a href="loginPage.jsp">Đăng nhập</a> 
            </div>
        </div>

        <!-- Navigation -->
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
                        <a class="nav-link" href="home.jsp">TRANG CHỦ
                            <span class="sr-only">(current)</span>
                        </a>
                    </li>
                    <li class="col-2">
                        <a class="nav-link" href="products.html">NGOẠI TỆ</a>
                    </li>
                    <li class="col-2">
                        <a class="nav-link" href="about.html">VÀNG</a>
                    </li>

                </ul>
            </div>



        </nav>



        <!-- Featured Starts Here -->
        <div class="featured-items col-12">



            <div class="section-heading">
                <div class="line-dec"></div>
                <h1>GIÁ NGOẠI TỆ</h1>
                <div class="col-12 feature-product">
                    <c:set var="list" value="${requestScope.LIST}"/>
                    <c:set var="listRate" value="${requestScope.LISTRATE}"/>
                    <c:set var="listRate30" value="${requestScope.RATE30}"/>
                    <c:if test="${not empty list}">

                        <c:import charEncoding="UTF-8" var="xstl" url="currency.xsl"/>
                        <x:transform doc="${list}" xslt="${xstl}"/>
                    </c:if>
                    <c:if test="${empty list}">
                        <h1>Không có cập nhật giá tiền tệ hôm nay</h1>
                    </c:if>

                </div>
            </div>

        </div>

        <div class="featured-items col-12">



            <div class="section-heading">
                <div class="line-dec"></div>
                <h1>HÔM NAY</h1>
                <div class="col-12 feature-product">

                    <c:if test="${not empty listRate}">
                        <c:import charEncoding="UTF-8" var="xstl_rate" url="currency_rate.xsl"/>
                        <x:transform doc="${listRate}" xslt="${xstl_rate}"/>
                    </c:if>
                    <c:if test="${empty listRate}">
                        <h1>Không có cập nhật giá tiền tệ hôm nay</h1>
                    </c:if>
                </div>
            </div>


        </div>
        <div class="featured-items col-12">



            <div class="section-heading">
                <div class="line-dec"></div>
                <h1>30 NGÀY</h1>
                <div class="col-12 feature-product">
                    <c:set var="listRate30" value="${requestScope.RATE30}"/>
                    <c:if test="${not empty listRate30}">
                        <c:import charEncoding="UTF-8" var="xstl_rate" url="currency_rate.xsl"/>
                        <x:transform doc="${listRate30}" xslt="${xstl_rate}"/>
                    </c:if>
                    <c:if test="${empty listRate30}">
                        <h1>Không có cập nhật giá tiền tệ hôm nay</h1>
                    </c:if>
                </div>
            </div>


        </div>


        <!-- Featred Ends Here -->
        <!-- Featured Starts Here -->
        <div class="featured-items col-12">



            <div class="section-heading">
                <div class="line-dec"></div>
                <h1>Biểu đồ</h1>
            </div>

            <div class="col-10 feature-product">
                <div class="feature-item-out">
                    <div id="chartContainer" style="height: 300px; width: 100%;"></div>
                    <script src="https://canvasjs.com/assets/script/canvasjs.min.js"></script>
                    <!--                    <script type="text/javascript" src="layout/styles/js/chart.js"></script>-->
                    <c:set var="listRate30" value="${requestScope.RATE30}"/>
                    <c:if test="${not empty listRate30}">
                        <c:import charEncoding="UTF-8" var="xstl_chart" url="chart.xsl"/>
                        <x:transform doc="${listRate30}" xslt="${xstl_chart}"/>
                    </c:if>
                    <c:if test="${empty listRate30}">
                        <h1>Không có cập nhật giá tiền tệ hôm nay</h1>
                    </c:if>
                </div>
            </div>
        </div>


        <!-- Featred Ends Here -->

        <!-- Sub Footer Starts Here -->
        <div class="col-12 footer">


            <div class="copyright-text">
                <p>Copyright &copy; 2019 Company Name 

                    - Design: <a rel="nofollow" href="https://www.facebook.com/NhiNguyen">NhiNguyen</a></p>
            </div>
        </div>



        <!-- Sub Footer Ends Here -->


    </body>
</html>
