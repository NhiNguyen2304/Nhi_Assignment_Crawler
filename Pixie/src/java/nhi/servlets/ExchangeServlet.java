/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nhi.servlets;

import com.sun.javafx.binding.StringFormatter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import nhi.daos.CurrencyDAO;

/**
 *
 * @author admin
 */
public class ExchangeServlet extends HttpServlet {

    private final String exchangePage = "test.jsp";
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
        try {
            String from = request.getParameter("txtFrom");
            String fromParsed = from.replace("0", "");
            long fromParsedToLong = 0;
            String item = request.getParameter("listCountryTo");
            if (fromParsed != null){
             fromParsedToLong = Long.parseLong(fromParsed);
            }
           
            long result = 0;
            LocalDate localDate = LocalDate.now();//For reference
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy"); //for format Date from yyyy-mm-dd to dd-mm-yyyy
            //String to = request.getParameter("txtTo");
            CurrencyDAO dao = new CurrencyDAO();
            if (fromParsedToLong != 0) {
                if (item.equals(item)) {
                    //float currencyEx = dao.getCurrency(item, localDate.format(formatter));
                    float currencyEx = dao.getCurrency(item, "04-03-2019");
                    result = (long) (fromParsedToLong * currencyEx);
                    //String parsedResult = String.valueOf(result); 
                    DecimalFormat fm = new DecimalFormat("#,000");
                    request.setAttribute("EX", fm.format(result));
                    request.setAttribute("FROM", from);
                    //request.setAttribute("EX", result);
                    url = exchangePage;
                }

            }

        } finally {
            request.getRequestDispatcher(url).forward(request, response);
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
