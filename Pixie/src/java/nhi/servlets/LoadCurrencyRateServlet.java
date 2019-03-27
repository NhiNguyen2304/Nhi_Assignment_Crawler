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
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import nhi.crawler.AppConstant;
import nhi.daos.CurrencyDAO;
import nhi.dto.CurrencyRateDTOList;
import nhi.properties.NhiGetProperties;
import nhi.utils.Currency;
import nhi.utils.CurrencyExchange;
import nhi.utils.NhiUtils;

/**
 *
 * @author admin
 */
public class LoadCurrencyRateServlet extends HttpServlet {
    private final String targetPage = "home.jsp";
    private final String errorPage = "error.jsp";
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
        String url = null;
        try {
            
            CurrencyDAO dao = new CurrencyDAO();
            Currency curr = new Currency();
            NhiGetProperties prop = new NhiGetProperties();
            
            //For today currency rate
            DateTimeFormatter df = DateTimeFormatter.ofPattern("dd-MM-yyyy");
            LocalDate localDate = LocalDate.now();
            CurrencyRateDTOList currencyRates = null;
            currencyRates = dao.getCurrencyRateByDate(localDate.format(df), localDate.minusDays(1).format(df));
             String xmlCurrencyRates = curr.marshalToString(currencyRates);

             //For list of date
            int date = 0;

            date = Integer.parseInt(prop.getPropValue("periodToGetValue", AppConstant.srcTimerXML));
            CurrencyRateDTOList currencyRatesList = null;
            ArrayList<String> listDate = new ArrayList<>();
            listDate = NhiUtils.getDate(date);
            currencyRatesList = dao.getCurrencyRateByDateList(listDate);
           
            String xmlCurrencyRates30 = curr.marshalToString(currencyRatesList);
            request.setAttribute("LISTRATE", xmlCurrencyRates);
            request.setAttribute("RATE30", xmlCurrencyRates30);
            url = targetPage;
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
