/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nhi.utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author admin
 */
public class PropertiesExamples {

    public PropertiesExamples() {
    }

    public Properties loadTraditionalProperties(final String filePathAndName) {
        final Properties properties = new Properties();
        try {
            final FileInputStream in = new FileInputStream(filePathAndName);
            if (in != null) {
                properties.load(in);
            }
            in.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(PropertiesExamples.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            ex.printStackTrace();
            Logger.getLogger(PropertiesExamples.class.getName()).log(Level.SEVERE, null, ex);
        }
        return properties;
    }

    public void storeXmlProperties(final Properties sourceProperties,
            final OutputStream out) {
        try {
            if (out != null){
                 sourceProperties.storeToXML(out, "Finish Stored");
            }
           out.close();
        } catch (IOException ex) {
             Logger.getLogger(PropertiesExamples.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void storeXMLPropertiesFile(final Properties sourceProperties,
            final String pathAnhFileName) {
        try {
            FileOutputStream fos = new FileOutputStream(pathAnhFileName);
            storeXmlProperties(sourceProperties, fos);
            fos.close();
        } catch (FileNotFoundException ex) {
            //e.printStackTrace();
             Logger.getLogger(PropertiesExamples.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            //ex.printStackTrace();
            Logger.getLogger(PropertiesExamples.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
