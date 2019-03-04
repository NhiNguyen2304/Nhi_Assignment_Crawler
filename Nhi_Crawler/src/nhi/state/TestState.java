/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nhi.state;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

/**
 *
 * @author admin
 */
public class TestState {
    
//    public static void mainTest() throws IOException{
//    String[] urls = {nhi.utils.AppConstant.urlMyBoss | nhi.utils.AppConstant.urlBestCare};
//       
//        for (String url : urls) {
//            testWellformed(url);
//        }
//    }
     public static boolean testWellformed(String urlString) throws MalformedURLException, IOException{
        URL url = new URL(urlString);
        URLConnection connection = url.openConnection();
        connection.setReadTimeout(8 * 1000);
        connection.setConnectTimeout(8 * 1000);
        
        String textContent = getString(connection.getInputStream());
        
        textContent = TextUtils.refineHtml(textContent);
        
        if (checkWellformedXml(textContent)){
           
            System.out.println(urlString + "  well-formed");
            return true;
        }
        return false;
    }
    private static String getString (InputStream stream){
        StringBuilder stringBuilder = new StringBuilder();
        String line;
        
            try (BufferedReader bufferReader =
                   new BufferedReader(new InputStreamReader(stream, StandardCharsets.UTF_8))){
                while ((line = bufferReader.readLine()) != null) {                    
                    stringBuilder.append(line);
                }
 
            } catch (IOException e) {
            }
            return stringBuilder.toString();
    }
    private static boolean checkWellformedXml(String src){
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setValidating(false);
        factory.setNamespaceAware(true);
        
        DocumentBuilder builder;
        
        try {
            builder = factory.newDocumentBuilder();
            
        } catch (ParserConfigurationException e) {
        e.printStackTrace();
        return false;
    }
        
        builder.setErrorHandler(new ErrorHandler() {
            @Override
            public void warning(SAXParseException exception) throws SAXException {
                System.out.println(exception.getMessage());
            }

            @Override
            public void error(SAXParseException exception) throws SAXException {
                System.out.println(exception.getMessage());
            }

            @Override
            public void fatalError(SAXParseException exception) throws SAXException {
                System.out.println(exception.getMessage());
            }
        });
        
        try {
            builder.parse(new ByteArrayInputStream(src.getBytes(StandardCharsets.UTF_8)));
            return true;
        } catch (SAXException | IOException e) {
            return false;
        }
        }
}
