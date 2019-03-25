/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nhi.properties;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.zip.InflaterInputStream;
import nhi.utils.PropertiesExamples;

/**
 *
 * @author admin
 */
public class NhiGetProperties {
    String result = "";
    InputStream inputStream;
    
    public String getPropValue(String key){
        try {
            Properties prop = new Properties();
            String propFileName = "src/nhi/properties/nhi-xml.properties";
            inputStream = new FileInputStream(propFileName);
            if (inputStream != null){
                prop.loadFromXML(inputStream);
            } 
            String test = null;
            result = prop.getProperty(key);
            result = test + " Ne";
           inputStream.close();
        } catch (FileNotFoundException ex) {
             Logger.getLogger(PropertiesExamples.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(NhiGetProperties.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }
    
}
