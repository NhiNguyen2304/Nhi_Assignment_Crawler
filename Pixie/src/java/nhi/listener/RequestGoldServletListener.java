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
import nhi.utils.CurrencyExchange;
import nhi.utils.NhiUtils;
import nhi.utils.XMLUtils;

/**
 * Web application lifecycle listener.
 *
 * @author admin
 */
@WebListener()
public class RequestGoldServletListener extends CurrencyExchange<GoldDTOList> implements ServletRequestListener {

    @Override
    public void requestDestroyed(ServletRequestEvent sre) {
        System.out.println("Request Distroyed");
    }

    @Override
    public void requestInitialized(ServletRequestEvent sre) {
        System.out.println("Request Initialized");

        GoldDAO goldDao = new GoldDAO();

        DateTimeFormatter df = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate localDate = LocalDate.now();
       
       

        //For today gold rate
        GoldDTOList goldRates = null;
        goldRates = goldDao.getCurrencyRateByDate(localDate.format(df), localDate.minusDays(1).format(df));
        
        
        //For period time
         NhiGetProperties prop = new NhiGetProperties();
        int date = 0;
        date = Integer.parseInt(prop.getPropValue("periodToGetValue", AppConstant.srcTimerXML));
        GoldDTOList goldList = null;
         ArrayList<String> listDate = new ArrayList<>();
         listDate = NhiUtils.getDate(date);
        goldList = goldDao.getGoldRateByDateList(listDate);

        //String xmlCurrencies = marshalToString(currencies);
        String xmlGoldRate = marshalToString(goldRates);
        String xmlGoldRateList = marshalToString(goldList);
        
        //sre.getServletRequest().setAttribute("LIST", xmlCurrencies);
        sre.getServletRequest().setAttribute("LISTGOLDRATE", xmlGoldRate);
        sre.getServletRequest().setAttribute("LISTPERIODGOLD", xmlGoldRateList);

       
    }

}
