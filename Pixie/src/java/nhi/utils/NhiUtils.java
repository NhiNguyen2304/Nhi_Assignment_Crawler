/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nhi.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletContext;
import nhi.crawler.AppConstant;
import nhi.properties.NhiGetProperties;

/**
 *
 * @author admin
 */
public class NhiUtils implements Serializable {

    public static float formatCharater(String character) {
        int comaPosition = 0;
        int dotPosition = 0;
        String formated = null;
        String temp = null;
        float result = 0;
        
        float zero = 0;
         if (character.equals("0.00")) {
             return 0;
         }
        dotPosition = character.indexOf(".");
        
        if (dotPosition != character.length() - 3 && dotPosition > 0) {
            comaPosition = character.indexOf(",");
            if (comaPosition > 0){
            temp = character.substring(0, comaPosition);
            formated = temp.replace(".", "");
            }
            
        }
        if (dotPosition == character.length() - 3 && dotPosition > 0)
        {
            temp = character.substring(0, dotPosition);
            formated = temp.replace(",", "");

        }
        if (formated != null){
            result = Float.parseFloat(formated);
        }
        return result;
    }
    
   
    public static String formatCharaterForException(String character) {
        //String parseComon = character.replace(",", "");
        //String parseDot = character.replace(".", "");
        String parseComon = character.replace(",", ".");
        return parseComon;
    }

   
    public static String formatForPrint(float num1) {
        String result = null;
        result = String.valueOf(num1);

        return result;

    }

    public static String countPercentRate(float num1) {
        String result = null;
        result = String.valueOf(num1);
        result = result + "%";
        return result;

    }
    
    public static ArrayList<String> getDate(int listSize,ServletContext context) {
        ArrayList<String> listDate = new ArrayList<>();
        NhiGetProperties pro = new NhiGetProperties();
        String s = getConfigProperties(context, AppConstant.srcTimerXML,
                                            "startDate");
        LocalDate start = LocalDate.parse(s);
        int count = 0;
        List<LocalDate> totalDates = new ArrayList<>();
        DateTimeFormatter df = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate localDate = LocalDate.now();
        while (!localDate.isBefore(start) && count < listSize) {
            totalDates.add(localDate);
            localDate = localDate.minusDays(1);
            count++;
            //start = start.plusDays(1);
        }

        for (int i = 0; i < totalDates.size(); i++) {
            listDate.add(totalDates.get(i).format(df));

        }
        return listDate;

    }
    public static String getConfigProperties(ServletContext context, String nameXml, String name){
        InputStream is = null;
        
        try {
            is =  context.getResourceAsStream("/WEB-INF/properties/" + nameXml);
            
            
            
            Properties prop = new Properties();
            prop.loadFromXML(is);
            return prop.getProperty(name);
        } catch (FileNotFoundException ex) {
             Logger.getLogger(NhiUtils.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(NhiUtils.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            if (is != null){
                try {
                    is.close();
                } catch (IOException e) {
                    Logger.getLogger(NhiUtils.class.getName()).log(Level.SEVERE, null, e);
                }
            }
        }
        return null;
    } 

   

}
