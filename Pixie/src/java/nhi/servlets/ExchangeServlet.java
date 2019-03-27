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
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import nhi.crawler.AppConstant;
import nhi.daos.CurrencyDAO;
import nhi.dto.CurrencyRateDTOList;
import nhi.properties.NhiGetProperties;
import nhi.utils.Currency;
import nhi.utils.NhiUtils;

/**
 *
 * @author admin
 */
public class ExchangeServlet extends HttpServlet {

    private final String exchangePage = "exchange_suggest.jsp";
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
        boolean isError = false;
        try {
             long fromParsedToLong = 0;
            String itemTo = request.getParameter("listCountryTo").trim();
            String itemFrom = request.getParameter("listCountryFrom").trim();
            long result = 0;
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy"); //for format Date from yyyy-mm-dd to dd-mm-yyyy
            LocalDate localDate = LocalDate.now();//For reference
            //String to = request.getParameter("txtTo");
            CurrencyDAO dao = new CurrencyDAO();
            String from = null;
            from = request.getParameter("txtFrom");
            if (from.equals("")) {
                isError = true;
            }
            // String fromParsed = from.replace("0", "");
           
            if (!from.equals("")) {
                fromParsedToLong = Long.parseLong(from);
            }
            

            if (!itemFrom.equals(itemTo) && isError == false) {
                float currencyEx = dao.getCurrency(itemTo, localDate.format(formatter));
                int date = 0;
                NhiGetProperties prop = new NhiGetProperties();
                Currency curr = new Currency();

                date = Integer.parseInt(prop.getPropValue("periodToGetValue", AppConstant.srcTimerXML));
                CurrencyRateDTOList currencyRatesList = null;
                ArrayList<String> listDate = new ArrayList<>();
                listDate = NhiUtils.getDate(date);
                if (itemTo.equals("VND")) {
                    currencyEx = dao.getCurrency(itemFrom, localDate.format(formatter));
                    result = (long) (fromParsedToLong * (int) currencyEx);

                    request.setAttribute("EX", result);
                    request.setAttribute("FROM", from);
                    //request.setAttribute("EX", result);
                    url = exchangePage;
                }
                if (!itemFrom.equals("VND") && !itemTo.equals("VND")) {
                    //float currencyEx = dao.getCurrency(item, localDate.format(formatter));

                    result = (long) (fromParsedToLong * (int) currencyEx);

                    request.setAttribute("EX", result);
                    request.setAttribute("FROM", from);
                    //request.setAttribute("EX", result);
                    url = exchangePage;
                }
                if (itemFrom.equals("VND")) {
                    //String fromParsed = from.replace("0", "");
                    float resultRate = 0;
                    resultRate = (fromParsedToLong / currencyEx);
                    //DecimalFormat fm = new DecimalFormat("#,000");
                    request.setAttribute("EX", resultRate);
                    request.setAttribute("FROM", from);
                    url = exchangePage;
                }
                if (!itemTo.equals("VND")) {

                    currencyRatesList = dao.getCurrencyRateByDateListAndID(listDate, itemTo);
                    String xmlCurrencyRates30 = curr.marshalToString(currencyRatesList);
                    request.setAttribute("RATE30", xmlCurrencyRates30);
                    url = exchangePage;
                }
                if (itemTo.equals("VND")) {
                    currencyRatesList = dao.getCurrencyRateByDateListAndID(listDate, itemFrom);
                    String xmlCurrencyRates30 = curr.marshalToString(currencyRatesList);
                    request.setAttribute("RATE30", xmlCurrencyRates30);
                    url = exchangePage;
                }

            }
            if (itemFrom.equals(itemTo) || isError == true) {
                request.setAttribute("ERROR", "Không thể chuyển đổi hãy kiểm tra loại tiền tệ và số tiền phải lớn hơn 0");
                url = exchangePage;
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
