/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nhi.utils;

import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 *
 * @author admin
 */
public class MybossThread extends BaseThread implements Runnable{
 
   

    @Override
    public void run() {
        while (true) {            
            try {
                MybossCetegoriesCrawler catCetegoriesCrawler = new MybossCetegoriesCrawler();
                Map<String, String> categories = catCetegoriesCrawler.getCategories(AppConstant.urlBestCare);
                for (Map.Entry<String, String> entry : categories.entrySet()) {
                    Thread crawlingThread = new Thread(new MyBossCrawler(entry.getKey(), entry.getValue()));
                    crawlingThread.start();
                    //System.out.println("Categories " + entry.getValue());
                    synchronized (BaseThread.getInstance()){
                        while(BaseThread.isSuspended()){
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
