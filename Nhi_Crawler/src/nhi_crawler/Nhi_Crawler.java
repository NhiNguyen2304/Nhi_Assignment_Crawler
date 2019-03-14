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
import javax.xml.bind.JAXBContext;
import nhi.daos.CurrencyDAO;
import nhi.dto.CurrencyDTO;
import nhi.utils.AppConstant;
import nhi.utils.VietBaoCrawler;
import nhi.threads.WebGiaThread;
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

        ArrayList<String> listDate = new ArrayList<>();

        String s = "2019-01-01";
        LocalDate start = LocalDate.parse(s);
        int count = 0;
        List<LocalDate> totalDates = new ArrayList<>();
        DateTimeFormatter df = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate localDate = LocalDate.now();
        while (!localDate.isBefore(start) && count <= 30) {
            totalDates.add(localDate);
            localDate = localDate.minusDays(1);
            count++;
            //start = start.plusDays(1);
        }

        for (int i = 0; i < totalDates.size(); i++) {
            int temp1 = 1;
            listDate.add(totalDates.get(i).format(df));
//             System.out.println(listDate.get(i));
        }
        for (int i = 0; i < listDate.size(); i++) {
            System.out.println(listDate.get(i));
            
        }
        

//        int count = 0;
//        String s = "2019-01-01";
//        LocalDate start = LocalDate.parse(s);
//        LocalDate localDate = LocalDate.now();
//        List<LocalDate> totalDates = new ArrayList<>();
//        while (!localDate.isBefore(start) && count < 30) {
//            totalDates.add(localDate);
//            localDate = localDate.minusDays(1);
//            count++;
//            System.out.println(" " + localDate);
//            //start = start.plusDays(1);
//        }
//        for (int i = 0; i < totalDates.size(); i++) {
//          int tmp1 = 1;
//          int tmp2 = 2;
//            System.out.println("BREAK");
//            System.out.println(totalDates.get(i));
//            System.out.println(totalDates.get(i).minusDays(tmp1));
//            System.out.println("BREAK");
//        }
//         int comaPosition = 0;
//         int dotPosition = 0;
//         String formated = null;
//         String temp = null;
//        String character = "16.091,62";
//         dotPosition = character.indexOf(".");
//        if (dotPosition != character.length() - 3){
//            comaPosition = character.indexOf(",");
//            temp = character.substring(0, comaPosition);
//            formated = temp.replace(".", "");
//        }
//        else {
//           temp = character.substring(0, dotPosition);
//           formated = temp.replace(",", "");
//          
//        }
//        System.out.println(" " + formated);
        //mainCrawOfWebGia();
        // mainCrawOfWebVietBao();
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
