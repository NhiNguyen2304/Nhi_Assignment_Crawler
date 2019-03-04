/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nhi.utils;

import java.io.BufferedReader;
import java.util.logging.Logger;

import nhi.dto.TblCategory;

/**
 *
 * @author admin
 */
public class MybossEachPageCrawler extends BaseCrawler implements Runnable{
    private String url;
    private TblCategory category;

    public MybossEachPageCrawler(String url, TblCategory category) {
      
        this.url = url;
        this.category = category;
    }
    

    

    @Override
    public void run() {
        BufferedReader reader = null;
        try {
            reader = getBufferReadFromURL(url);
            String line = "";
            String document = "<document>";
            boolean isStart = false;
            boolean isEnding = false;
            int divCounter = 0;
            int divOpen = 0, divlose = 0;
            while((line = reader.readLine()) != null){
                if(line.contains("<ul class=\"thumbnail")){
                    isStart = true;
                }
                if(isStart){
                    document += line;
                }
                if(line.contains("</ul>")){
                    isStart = false;
                }
            }
            document += "</document>";
            try {
                synchronized (BaseThread.getInstance()){
                    while (BaseThread.isSuspended()){
                        BaseThread.getInstance().wait();
                    }
                }
            } catch (InterruptedException e) {
                System.out.println("Interrupt " + e.getMessage());
            }
            
        } catch (Exception e) {
        }
    }
    
}
