/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nhi.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import nhi.daos.CurrencyDAO;
import nhi.dto.CurrencyDTO;

/**
 *
 * @author admin
 */
public class MainServlet extends HttpServlet {

    private final String indexPage = "index.jsp";
    private final String testPage = "test.jsp";
    private final String error = "errorPage.jsp";
    private final String testServlet = "TestServlet";
    private final String exchangeServlet = "ExchangeServlet";
    private final String highestRateServlet = "HighestRateServlet";

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
            String button = request.getParameter("btAction");
            CurrencyDAO dao = new CurrencyDAO();
            ArrayList<CurrencyDTO> countries = new ArrayList<>();

            if (countries != null) {
                countries = dao.getCountries("04-03-2019");
                HttpSession session = request.getSession();
                session.setAttribute("COUNTRIES", countries);
                url = testPage;
            }

            if (button == null) {

            } else if (button.equals("Next")) {
                url = testServlet;
            } else if (button.equals("Exchange")) {
                url = exchangeServlet;
            } else if (button.equals("GetHighest")) {
                url = highestRateServlet;
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
