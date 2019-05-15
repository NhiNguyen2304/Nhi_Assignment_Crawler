/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nhi.crawler;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.TimerTask;
import java.util.logging.Logger;
import nhi.listener.MyContextListener;
import nhi.properties.NhiGetProperties;

/**
 *
 * @author admin
 */
public class CrawlerMaster extends TimerTask{

//    public void crawlerMaster() {
//        int typeServlet = 1;
//        NhiGetProperties prop = new NhiGetProperties();
//        WebGiaTableCrawler webGia = new WebGiaTableCrawler();
//        VietBaoCrawler webVietBaoCraw = new VietBaoCrawler();
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy"); //for format Date from yyyy-mm-dd to dd-mm-yyyy
//        LocalDate localDate = LocalDate.now();//For reference
//        boolean check = false;
//        String parseTime = localDate.format(formatter) + ".html";
//        String urlWebGia = prop.getPropValue("url.webgia", AppConstant.srcWebGiaXML);
//        String urlVietBao = prop.getPropValue("url.vietbao", AppConstant.srcVietBaoXML);
//        check = webGia.getCurrencyFromURL(urlWebGia, localDate.format(formatter), typeServlet);
//        check = webVietBaoCraw.getGoldFromURL(urlVietBao, localDate.format(formatter), typeServlet);
//        if (check
//                == false) {
//            Logger logger = Logger.getLogger(MyContextListener.class
//                    .getName());
//            logger.severe("Error to crawl today Currency and Gold");
//        }
//    }

    @Override
    public void run() {
         int typeServlet = 1;
        NhiGetProperties prop = new NhiGetProperties();
        WebGiaTableCrawler webGia = new WebGiaTableCrawler();
        VietBaoCrawler webVietBaoCraw = new VietBaoCrawler();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy"); //for format Date from yyyy-mm-dd to dd-mm-yyyy
        LocalDate localDate = LocalDate.now();//For reference
        boolean checkCur = false;
        boolean checkGold = false;
        String parseTime = localDate.format(formatter) + ".html";
        String urlWebGia = prop.getPropValue("url.webgia", AppConstant.srcWebGiaXML);
        String urlVietBao = prop.getPropValue("url.vietbao", AppConstant.srcVietBaoXML);
        checkCur = webGia.getCurrencyFromURL(urlWebGia, localDate.format(formatter), typeServlet);
        checkGold = webVietBaoCraw.getGoldFromURL(urlVietBao, localDate.format(formatter), typeServlet);
        if (checkGold == false) {
            Logger logger = Logger.getLogger(CrawlerMaster.class
                    .getName());
            logger.severe("Error to crawl today Gold");
        }
        if (checkCur == false) {
            Logger logger = Logger.getLogger(CrawlerMaster.class
                    .getName());
            logger.severe("Error to crawl today Currency");
        }
    }

}
