<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <title>Pixie</title>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
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
                     <a href="#">Đăng xuất</a>
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
                        <li><a href="MainServlet"><i class="icon-home"></i>Tiền tệ</a></li>
                        <li><a href="LoadGoldRateServlet"><i class="icon-user"></i>Giá vàng</a></li>
                        <li><a href="exchange_suggest.jsp"><i class="icon-thumbs-up-alt"></i>Chuyển đổi tiền</a></li>
                    </ul>
                </nav>
            </div>



        </nav>


        <div class="section-heading">
                <div class="line-dec"></div>
                <h1>Đăng nhập</h1>
            </div>
        <form action="MainServlet" method="POST">
            <div class="imgcontainer col-12">
                <img src="images/img_avatar2.png" alt="Avatar" class="avatar">
            </div>

            <div class="container col-5">
                
                <label for="uname"><b>Username</b></label>
                <input type="text" placeholder="Enter Username" name="txtUsername" required="">

                <label for="psw"><b>Password</b></label>
                <input type="password" placeholder="Enter Password" name="txtPassword" required>

                <button type="submit"  value="Login" name="btAction">Login</button>
                
               
            </div>

            
        </form>



       

         <div class="col-12 footer">


            <div class="copyright-text">
                <p>Copyright &copy; 2019 Company Name 

                    - Design: <a rel="nofollow" href="https://www.facebook.com/NhiNguyen">NhiNguyen</a></p>
            </div>
        </div>

    </body>
</html>
