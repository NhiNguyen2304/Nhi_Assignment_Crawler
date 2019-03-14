/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nhi.utils;

import java.io.Serializable;
import java.io.StringWriter;
import java.util.List;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import nhi.dto.CurrencyDTOList;
import nhi.dto.CurrencyRate30DTOList;
import nhi.dto.CurrencyRateDTOList;

/**
 *
 * @author admin
 */
public class XMLUtils implements Serializable{
    public static String marshalToString (CurrencyDTOList currencies){
        try {
            JAXBContext jaxb = JAXBContext.newInstance(CurrencyDTOList.class);
            Marshaller mar = jaxb.createMarshaller();
            //mar.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            //mar.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
            
            StringWriter sw = new StringWriter();
            mar.marshal(currencies, sw);
            
            return sw.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
     public static String marshalToString (CurrencyRateDTOList currencies){
        try {
            JAXBContext jaxb = JAXBContext.newInstance(CurrencyRateDTOList.class);
            Marshaller mar = jaxb.createMarshaller();
            //mar.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            //mar.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
            
            StringWriter sw = new StringWriter();
            mar.marshal(currencies, sw);
            
            return sw.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
     public static String marshalToStringTest (CurrencyRate30DTOList currencies){
        try {
            JAXBContext jaxb = JAXBContext.newInstance(CurrencyRate30DTOList.class);
            Marshaller mar = jaxb.createMarshaller();
            //mar.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            //mar.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
            
            StringWriter sw = new StringWriter();
            mar.marshal(currencies, sw);
            
            return sw.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
      public static String marshalToString (List<CurrencyRate30DTOList> currencies){
        try {
            JAXBContext jaxb = JAXBContext.newInstance(CurrencyRate30DTOList.class);
            Marshaller mar = jaxb.createMarshaller();
            //mar.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            //mar.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
            
            StringWriter sw = new StringWriter();
            for (int i = 0; i < currencies.size(); i++) {
                mar.marshal(currencies.get(i), sw);
                
            }
          
            
            return sw.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
   
}
