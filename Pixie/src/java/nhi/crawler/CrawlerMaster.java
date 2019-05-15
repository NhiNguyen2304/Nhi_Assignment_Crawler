/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nhi.crawler;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Properties;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import nhi.properties.NhiGetProperties;

/**
 *
 * @author admin
 */
public class CrawlerMaster extends TimerTask {

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
        String urlWebGia = getConfigProperties("url.webgia", AppConstant.srcWebGiaXML,
                    this.getClass().getSimpleName());
        String urlVietBao = getConfigProperties("url.vietbao", AppConstant.srcVietBaoXML,
                    this.getClass().getSimpleName());
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

    private String getConfigProperties(String name, String nameXml, String nameClass) {
        FileInputStream is = null;

        try {
            File myFile = new File(getClass().getProtectionDomain()
                    .getCodeSource().getLocation().getFile()
                    .replaceFirst("classes/nhi/crawler/" + nameClass + ".class", ""));

            File f = new File(myFile.getCanonicalPath() + "/properties/" + nameXml);

            is = new FileInputStream(f);

            Properties prop = new Properties();
            prop.loadFromXML(is);
            return prop.getProperty(name);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(CrawlerMaster.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(CrawlerMaster.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    Logger.getLogger(CrawlerMaster.class.getName()).log(Level.SEVERE, null, e);
                }
            }
        }
        return null;
    }
}
