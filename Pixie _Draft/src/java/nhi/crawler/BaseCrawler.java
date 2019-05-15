/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nhi.crawler;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;

/**
 *
 * @author admin
 */
public class BaseCrawler {

    //private ServletContext context;

    

    
    protected BufferedReader getBufferReadFromURL(String urlString) throws MalformedURLException, IOException {
        URL url = new URL(urlString);
        URLConnection connection = url.openConnection();
        connection.addRequestProperty("User-Agent", "Morilla/5.0 (Window NT 10.0; Win64; x64)");
        InputStream is = connection.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
        return reader;
    }

    protected XMLEventReader parseStringToEventReader(String xmlSection) throws UnsupportedEncodingException, XMLStreamException {
        byte[] byteArray = xmlSection.getBytes("UTF-8");
        ByteArrayInputStream inputStream = new ByteArrayInputStream(byteArray);
        XMLInputFactory inputFactory = XMLInputFactory.newInstance();
        XMLEventReader reader = inputFactory.createXMLEventReader(inputStream);
        return reader;
    }
    protected String getConfigProperties(String name, String nameXml, String nameClass){
        FileInputStream is = null;
        
        try {
            File myFile = new File(getClass().getProtectionDomain()
                    .getCodeSource().getLocation().getFile()
                    .replaceFirst("classes/nhi/crawler/"+ nameClass +".class", ""));
            
            File f = new File(myFile.getCanonicalPath() + "/properties/" + nameXml);
           
            is = new FileInputStream(f);
            
            Properties prop = new Properties();
            prop.loadFromXML(is);
            return prop.getProperty(name);
        } catch (FileNotFoundException ex) {
             Logger.getLogger(BaseCrawler.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(BaseCrawler.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            if (is != null){
                try {
                    is.close();
                } catch (IOException e) {
                    Logger.getLogger(BaseCrawler.class.getName()).log(Level.SEVERE, null, e);
                }
            }
        }
        return null;
    } 

}
