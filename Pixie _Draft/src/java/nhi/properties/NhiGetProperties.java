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


/**
 *
 * @author admin
 */
public class NhiGetProperties {
    
    
    public String getPropValue(String key, String fileAndPathXML){
        final Properties properties = new Properties();
        String result = "";
        try {
            
           // String propFileName = fileAndPathXML;
           FileInputStream in = new FileInputStream(fileAndPathXML);
            if (in != null){
                properties.loadFromXML(in);
            } 
            //String test = null;
            result = properties.getProperty(key);
           
           in.close();
        } catch (FileNotFoundException ex) {
             Logger.getLogger(PropertiesExamples.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(NhiGetProperties.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }
    
}
