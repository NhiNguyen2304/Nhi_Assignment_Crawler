/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nhi_crawler;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import nhi.daos.CurrencyDAO;
import nhi.dto.CurrencyDTO;
import nhi.utils.AppConstant;
import nhi.utils.MyBestCareCetegoriesCrawler1;
import nhi.utils.VietBaoCrawler;
import nhi.utils.WebGiaTableCrawler;

/**
 *
 * @author admin
 */
public class Nhi_Crawler {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
  

        CurrencyDTO dto = new CurrencyDTO();
        CurrencyDAO dao = new CurrencyDAO();

        mainCrawOfWebGia();
       mainCrawOfWebVietBao();
      //MyTyGiaCrawler webGia = new VietBaoCrawler();
       //webGia.geCurrency(AppConstant.urlVietBao, "ngay-02-02-2019");
        //
    }

    private static void mainCrawOfWebGia() {
        WebGiaTableCrawler webGia = new WebGiaTableCrawler();
        String s = "2019-01-01";
        LocalDate start = LocalDate.parse(s);
        List<LocalDate> totalDates = new ArrayList<>();
        while (!start.isAfter(LocalDate.now())) {
            totalDates.add(start);
            start = start.plusDays(1);
        }

        LocalDate localDate = LocalDate.now();//For reference
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy"); //for format Date from yyyy-mm-dd to dd-mm-yyyy
        for (LocalDate totalDate : totalDates) {
            //System.out.println(" " + totalDate.format(formatter));
           
            String parseTime = totalDate.format(formatter) + ".html";
            webGia.getCategories(AppConstant.urlWebgia + parseTime, totalDate.format(formatter));

        }

    }
     private static void mainCrawOfWebVietBao() {
         VietBaoCrawler webVietBaoCraw = new VietBaoCrawler();
        String s = "2019-02-01";
        LocalDate start = LocalDate.parse(s);
        List<LocalDate> totalDates = new ArrayList<>();
        while (!start.isAfter(LocalDate.now())) {
            totalDates.add(start);
            start = start.plusDays(1);
        }

        LocalDate localDate = LocalDate.now();//For reference
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy"); //for format Date from yyyy-mm-dd to dd-mm-yyyy
        for (LocalDate totalDate : totalDates) {
            //System.out.println(" " + totalDate.format(formatter));
            String parseTime = "ngay" + "-" + totalDate.format(formatter);
            webVietBaoCraw.geCurrency(AppConstant.urlVietBao + parseTime, parseTime);

        }

        //webGia.geCurrency(AppConstant.urlWebgia, "ngay-25-02-2019");
    }

}
