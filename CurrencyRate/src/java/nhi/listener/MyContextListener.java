/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nhi.listener;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Logger;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import nhi.properties.NhiGetProperties;
import nhi.properties.NhiSaveProperties;
import nhi.crawler.WebGiaTableCrawler;
import nhi.crawler.VietBaoCrawler;
import nhi.crawler.AppConstant;

/**
 * Web application lifecycle listener.
 *
 * @author admin
 */
@WebListener()
public class MyContextListener implements ServletContextListener {

   

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        int typeServlet = 1;

        Date date = new Date();
        String realPath = sce.getServletContext().getRealPath("/");
       

        Timer timer = new Timer();
         NhiGetProperties prop = new NhiGetProperties();
        String temp = prop.getPropValue("startTime", AppConstant.srcTimerXML);
        String tempPeriod = prop.getPropValue("period", AppConstant.srcTimerXML);
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {

                NhiGetProperties prop = new NhiGetProperties();
                WebGiaTableCrawler webGia = new WebGiaTableCrawler();
                VietBaoCrawler webVietBaoCraw = new VietBaoCrawler();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy"); //for format Date from yyyy-mm-dd to dd-mm-yyyy
                LocalDate localDate = LocalDate.now();//For reference
                boolean check = false;
                String parseTime = localDate.format(formatter) + ".html";
                String urlWebGia = prop.getPropValue("url.webgia", AppConstant.srcWebGiaXML);
                String urlVietBao = prop.getPropValue("url.vietbao", AppConstant.srcVietBaoXML);
                check = webGia.getCurrencyFromURL(urlWebGia, localDate.format(formatter), typeServlet);
                check = webVietBaoCraw.getGoldFromURL(urlVietBao, localDate.format(formatter), typeServlet);
                if (check == false) {
                    Logger logger = Logger.getLogger(MyContextListener.class
                            .getName());
                    logger.severe("Error to crawl today Currency and Gold");
                }
            }
        }, Integer.parseInt(tempPeriod), Integer.parseInt(temp)); //Time to start, repeat in 1 day
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {

    }
}
