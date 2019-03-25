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
import nhi.crawler.AppConstant;
import nhi.daos.CurrencyDAO;
import nhi.daos.GoldDAO;
import nhi.dto.CurrencyDTOList;
import nhi.dto.CurrencyRateDTOList;
import nhi.dto.GoldDTOList;
import nhi.properties.NhiGetProperties;
import nhi.properties.NhiSaveProperties;
import nhi.utils.CurrencyExchange;
import nhi.utils.NhiUtils;
import nhi.utils.XMLUtils;

/**
 * Web application lifecycle listener.
 *
 * @author admin
 */
@WebListener()
public class RequestServletListener extends CurrencyExchange<CurrencyRateDTOList> implements ServletRequestListener{

    @Override
    public void requestDestroyed(ServletRequestEvent sre) {
        System.out.println("Request Distroyed");
    }

    @Override
    public void requestInitialized(ServletRequestEvent sre) {
//        System.out.println("Request Initialized");
//            CurrencyDAO dao = new CurrencyDAO();
//        
//       
//       //For today currency rate
//        DateTimeFormatter df = DateTimeFormatter.ofPattern("dd-MM-yyyy");
//        LocalDate localDate = LocalDate.now();
//        CurrencyRateDTOList currencyRates = null;
//        currencyRates = dao.getCurrencyRateByDate(localDate.format(df), localDate.minusDays(1).format(df));
//
//        
//        //For top previous 30 date currency
//        NhiGetProperties prop = new NhiGetProperties();
//        int date = 0;
//       
//       date = Integer.parseInt(prop.getPropValue("periodToGetValue", AppConstant.srcTimerXML));
//        CurrencyRateDTOList currencyRatesList = null;
//        ArrayList<String> listDate = new ArrayList<>();
//        listDate = NhiUtils.getDate(date);
//        currencyRatesList = dao.getCurrencyRateByDateList(listDate);
//
//        //For today gold rate
//     
//        
//        
//       
//        //String xmlCurrencies = marshalToString(currencies);
//        String xmlCurrencyRates = marshalToString(currencyRates);
//        String xmlCurrencyRates30 = marshalToString(currencyRatesList);
//       
//
//        //sre.getServletRequest().setAttribute("LIST", xmlCurrencies);
//        sre.getServletRequest().setAttribute("LISTRATE", xmlCurrencyRates);
//        sre.getServletRequest().setAttribute("RATE30", xmlCurrencyRates30);
//        System.out.println("xml: " + xmlCurrencyRates);
    }


}
