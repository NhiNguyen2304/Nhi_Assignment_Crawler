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
        <script src="layout/styles/js/search_gold.js"></script>
        
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

        <!-- Navigation -->
        <nav class="navbar">
            <div class="col-12">
                <link rel="stylesheet" href="layout/styles/css/nav.css">

                <a href="https://www.facebook.com/NhiNguyen" id="logo" target="_blank">PIXIE</a>

                <label for="toggle-1" class="toggle-menu"><ul><li></li> <li></li> <li></li></ul></label>
                <input type="checkbox" id="toggle-1">

                <nav>
                    <ul>
                        <li><a href="LoadCurrencyRateServlet"><i class="icon-home"></i>Tiền tệ</a></li>
                        <li><a href="LoadGoldRateServlet"><i class="icon-user"></i>Giá vàng</a></li>
                        <li><a href="home.jsp"><i class="icon-thumbs-up-alt"></i>Giúp đỡ</a></li>

                    </ul>
                </nav>
            </div>


        </nav>

        <c:set var="listRate" value="${requestScope.LISTGOLDRATE}"/>
        

        <!-- Featured Starts Here -->


        <div class="featured-items col-12">



            <div class="section-heading">
                <div class="line-dec"></div>
                <h2>TỈ LỆ VÀNG HÔM NAY</h2>
                <div class="col-12 feature-product">

                    <c:if test="${not empty listRate}">
                        <c:import charEncoding="UTF-8" var="xstl_rate" url="gold_rate.xsl"/>
                        <x:transform doc="${listRate}" xslt="${xstl_rate}"/>
                    </c:if>
                    <c:if test="${empty listRate}">
                        <h1>Không có cập nhật vàng hôm nay</h1>
                    </c:if>
                </div>
            </div>


        </div>
       

        <!-- Featred Ends Here -->
        <!-- Featured Starts Here -->
        <div class="featured-items col-12">


            <script>
                regObj = '${requestScope.LISTPERIODGOLD}';
                var check = fasle;
            </script>
            <div class="section-heading">
                <div class="line-dec"></div>
                <h2>TÌM KIẾM</h2>
                <div class="col-3">
                    <form name="myForm">
                        Tên vàng <input type="text" name="txtCode" value="" /><br/>
                        <button type="button"
                                onclick="return searchProcess('dataTable', check);">Tìm kiếm</button>

                    </form>
                </div>
                <div class="col-12 feature-product">

                    <table border="1" id="dataTable" style="visibility: hidden;">

                        <tr>

                            <th>Loại</th>
                            <th>Bán ra</th>
                            <th>Mua vào</th>
                            <th>Khu vực</th>
                            <th>Ngày</th>



                        </tr>


                    </table>
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
