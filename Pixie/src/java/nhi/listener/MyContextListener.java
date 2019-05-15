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
import nhi.crawler.CrawlerMaster;
import nhi.servlets.CrawlCurrencyTodayServlet;
import nhi.utils.NhiUtils;
import org.apache.catalina.valves.CrawlerSessionManagerValve;

/**
 * Web application lifecycle listener.
 *
 * @author admin
 */
//@WebListener()
//public class MyContextListener implements ServletContextListener {
//
//    @Override
//    public void contextInitialized(ServletContextEvent sce) {
//        try {
//            System.out.println("Listener Crawl Innitialize");
//            
//            
//            String temp = NhiUtils.getConfigProperties(sce.getServletContext(), AppConstant.srcTimerXML,
//                                            "startTime");
//            String tempPeriod = NhiUtils.getConfigProperties(sce.getServletContext(), AppConstant.srcTimerXML,
//                                            "period");
//            CrawlerMaster crawler = new CrawlerMaster();
//            
//            Timer timer = new Timer();
//            int typeServlet = 1;
//            timer.schedule(crawler, Integer.parseInt(tempPeriod), Integer.parseInt(temp)); //Time to start, repeat in 1 day
//
//        } catch (Exception e) {
//           // e.printStackTrace();
//        }
//
//        
//    }
//
//    @Override
//    public void contextDestroyed(ServletContextEvent sce) {
//        System.out.println("Crawler Listener has been shutdown");
//    }
//}
