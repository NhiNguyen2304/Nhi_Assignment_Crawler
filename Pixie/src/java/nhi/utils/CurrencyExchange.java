/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nhi.utils;

import java.io.StringWriter;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import nhi.dto.CurrencyDTOList;

/**
 *
 * @author admin
 * @param <T>
 */
public abstract class CurrencyExchange<T> {
    public String marshalToString (T t){
        try {
            JAXBContext jaxb = JAXBContext.newInstance(t.getClass());
            Marshaller mar = jaxb.createMarshaller();
            //mar.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            //mar.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
            
            StringWriter sw = new StringWriter();
            mar.marshal(t, sw);
            
            return sw.toString();
        } catch (JAXBException e) {
            e.printStackTrace();
        }
        return null;
    }
}
