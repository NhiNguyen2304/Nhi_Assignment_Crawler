/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nhi.threads;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import nhi.utils.AppConstant;
import nhi.utils.BaseThread;
import nhi.utils.MybossThread;
import nhi.utils.WebGiaTableCrawler;

/**
 *
 * @author admin
 */
public class WebGiaThread extends BaseThread implements Runnable {

    @Override
    public void run() {
        while (true) {
            try {
                WebGiaTableCrawler webGia = new WebGiaTableCrawler();
                
                List<LocalDate> listDate = new ArrayList<>();
                //listDate = getListDate();
                for (LocalDate totalDate : listDate) {
                     DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy"); //for format Date from yyyy-mm-dd to dd-mm-yyyy
                    String parseTime = totalDate.format(formatter) + ".html";
                    //Thread crawlingThread = new Thread(new WebGiaTableCrawler(AppConstant.urlWebgia + parseTime, totalDate.format(formatter)));
                   // crawlingThread.start();
                    //System.out.println("Categories " + entry.getValue());
                    synchronized (BaseThread.getInstance()) {
                        while (BaseThread.isSuspended()) {
                            BaseThread.getInstance().wait();
                        }
                    }

                }
            } catch (InterruptedException e) {
                Logger.getLogger(MybossThread.class.getName()).log(Level.SEVERE, null, e);

            }
        }
    }

   

}
