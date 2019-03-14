/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nhi.listener;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;
import javax.servlet.annotation.WebListener;
import nhi.daos.CurrencyDAO;
import nhi.dto.CurrencyDTOList;
import nhi.dto.CurrencyRateDTOList;
import nhi.utils.NhiUtils;
import nhi.utils.XMLUtils;

/**
 * Web application lifecycle listener.
 *
 * @author admin
 */
@WebListener()
public class RequestServletListener implements ServletRequestListener {

    @Override
    public void requestDestroyed(ServletRequestEvent sre) {
        System.out.println("Request Distroyed");
    }

    @Override
    public void requestInitialized(ServletRequestEvent sre) {
        System.out.println("Request Initialized");
        CurrencyDAO dao = new CurrencyDAO();
        CurrencyDTOList currencies = dao.getAllRateCurrency();

        

       //For today currency rate
        DateTimeFormatter df = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate localDate = LocalDate.now();
        CurrencyRateDTOList currencyRates = null;
        currencyRates = dao.getCurrencyRateByDate(localDate.format(df), localDate.minusDays(1).format(df));

        
        //For top previous 30 date
        CurrencyRateDTOList currencyRatesList = null;
        ArrayList<String> listDate = new ArrayList<>();
        listDate = NhiUtils.getDate();
        currencyRatesList = dao.getCurrencyRateByDateList(listDate);

        
        
        
        
        String xmlCurrencyRates30 = null;
        String xmlCurrencies = XMLUtils.marshalToString(currencies);
        String xmlCurrencyRates = XMLUtils.marshalToString(currencyRates);
        xmlCurrencyRates30 = XMLUtils.marshalToString(currencyRatesList);

        sre.getServletRequest().setAttribute("LIST", xmlCurrencies);
        sre.getServletRequest().setAttribute("LISTRATE", xmlCurrencyRates);
        sre.getServletRequest().setAttribute("RATE30", xmlCurrencyRates30);
        //System.out.println("xml: " + xmlCurrency);
    }

//     DateTimeFormatter df = DateTimeFormatter.ofPattern("dd-MM-yyyy");
//        LocalDate localDate = LocalDate.now();
//        CurrencyRateDTOList currencyRates = null;
//         while (!localDate.isBefore(start) && count <= 30) {
//            totalDates.add(localDate);
//            localDate = localDate.minusDays(1);
//            count++;
//            //start = start.plusDays(1);
//        }
//           for (int i = 0; i < totalDates.size(); i++) {
//               int temp1 = 1;
//                  currencyRates = dao.getCurrencyRateByDate(totalDates.get(i).format(df), totalDates.get(i).minusDays(temp1).format(df));      
//             }
//                
//        System.out.println("TEST " + currencyRates);
}
