/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nhi.utils;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

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
         if (character.equals("-")) {
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

    public static String formatDate(String date) {
        String result = date.replace("ngay-", "").trim();
        return result;
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
    
    public static ArrayList<String> getDate() {
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
            listDate.add(totalDates.get(i).format(df));

        }
        return listDate;

    }

   

}
