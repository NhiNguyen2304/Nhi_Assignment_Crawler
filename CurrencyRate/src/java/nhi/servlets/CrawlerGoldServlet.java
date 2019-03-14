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
import java.util.ArrayList;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import nhi.crawler.AppConstant;
import nhi.crawler.VietBaoCrawler;

/**
 *
 * @author admin
 */
public class CrawlerGoldServlet extends HttpServlet {
     private final String error = "errorPage.jsp";
     private final String crawlerPage = "crawler.jsp";

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
        try {
            HttpSession session = request.getSession();
             String resultSuccess = null;
             String resultFailed = null; 
            VietBaoCrawler webVietBaoCraw = new VietBaoCrawler();
            String s = "2019-02-01";
            LocalDate start = LocalDate.parse(s);
            List<LocalDate> totalDates = new ArrayList<>();
            while (!start.isAfter(LocalDate.now())) {
                totalDates.add(start);
                start = start.plusDays(1);
            }

            LocalDate localDate = LocalDate.now();//For reference
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy"); //for format Date from yyyy-mm-dd to dd-mm-yyyy
            for (LocalDate totalDate : totalDates) {
                //System.out.println(" " + totalDate.format(formatter));
                String parseTime = "ngay" + "-" + totalDate.format(formatter);
                check = webVietBaoCraw.geCurrency(AppConstant.urlVietBao + parseTime, parseTime);

            }
            
            if (check){
                resultSuccess = "Tạo dữ liệu vàng thành công";
                session.setAttribute("CHECKS", resultSuccess);
                url = crawlerPage;
            }
            else {
                resultFailed = "Có lỗi xảy ra. Vui lòng xem lại kết nối, hoặc website cần thu thập";
                session.setAttribute("CHECKF", resultFailed);
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
