/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nhi.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Timer;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import nhi.crawler.AppConstant;
import nhi.crawler.NganHangCrawler;
import nhi.crawler.WebGiaTableCrawler;
import nhi.properties.NhiGetProperties;
import nhi.properties.NhiSaveProperties;
import nhi.utils.NhiUtils;

/**
 *
 * @author admin
 */
public class CrawlCurrencyTodayServlet extends HttpServlet {

    private final String crawlerPage = "crawler.jsp";
    private final String error = "errorPage.jsp";

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        String url = error;
        boolean check = false;
         int typeServlet = 1; //type 1 is crawl Today type 2 is crawl all
        try {
          
            String resultSuccess = null;
            String resultFailed = null;
            //HttpSession session = request.getSession();
            
            NganHangCrawler webNganHang = new NganHangCrawler();
            

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy"); //for format Date from yyyy-mm-dd to dd-mm-yyyy
            LocalDate localDate = LocalDate.now();//For reference

            
           
           ServletContext context = this.getServletContext();
            String urlWebGia =  NhiUtils.getConfigProperties(context, AppConstant.srcWebGiaXML,
                                            "url.webgia");
            
            check = webNganHang.getCurrencyFromURL(urlWebGia, localDate.format(formatter));

            if (check == true) {
                resultSuccess = "Tạo dữ liệu ngoại tệ thành công";
                request.setAttribute("CHECKS", resultSuccess);
                url = crawlerPage;
            }
            if (check == false) {
                resultFailed = "Có lỗi xảy ra. Vui lòng xem lại kết nối, hoặc website cần thu thập";
                request.setAttribute("CHECKF", resultFailed);
                url = crawlerPage;
            }

        } finally {
            RequestDispatcher rd = request.getRequestDispatcher(url);
            rd.forward(request, response);
            out.close();
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
