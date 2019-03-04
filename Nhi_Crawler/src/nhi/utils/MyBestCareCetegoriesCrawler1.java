/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nhi.utils;

import com.sun.xml.internal.stream.events.CharacterEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.plaf.TextUI;

import javax.xml.namespace.QName;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.Characters;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;
import nhi.state.TestState;
import nhi.state.TextUtils;

/**
 *
 * @author admin
 */
public class MyBestCareCetegoriesCrawler1 extends BaseCrawler {

    public Map<String, String> getCategories(String url) {
        TextUtils checkWellformed = new TextUtils();
        String changingDoc = "";
        BufferedReader reader = null;
        try {
            reader = getBufferReadFromURL(url);
            String line = "";
            String document = "";
            boolean isStart = false;
            boolean end = false;

            while ((line = reader.readLine().trim()) != null) { //<li class=\"title-sub\"><a class=\"a-sub\" href=\"/collections/skin-care\">
                //<a href=\"/cham-soc-da\" class=\"nav-link\">Chăm sóc da
                if (line.contains("<span>Chăm sóc da")) {
                    isStart = true;

                }
                if (isStart && !end) {//EXFOLIATE</a>
                    //Viêm da cơ địa</a>
                    if (line.contains("<li class=\"menu-item menu-item-has-children menu-parent-item col-sw-6  \">")) {
                        document += line;
                        break;
                    }
                    //System.out.println(line);
                    document += line;
                }
            }

            changingDoc = checkWellformed.refineHtml(document);
            //System.out.println("Document " + changingDoc);
            return sTAXPaeserForCatergories(changingDoc);
        } catch (UnsupportedEncodingException e) {

        } catch (IOException ex) {
            Logger.getLogger(MyBestCareCetegoriesCrawler1.class.getName()).log(Level.SEVERE, null, ex);
        } catch (XMLStreamException ex) {
            Logger.getLogger(MyBestCareCetegoriesCrawler1.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException ex) {
                Logger.getLogger(MyBestCareCetegoriesCrawler1.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return null;
    }

    public Map<String, String> sTAXPaeserForCatergories(String document) throws UnsupportedEncodingException, XMLStreamException, IOException {
        document = document.trim();
        XMLEventReader eventReader = parseStringToEventReader(document);
        Map<String, String> categories = new HashMap<String, String>();
        while (eventReader.hasNext()) {
            XMLEvent event = (XMLEvent) eventReader.next();
            if (event.isStartElement()) {
                StartElement startElement = event.asStartElement();
                String tagName = startElement.getName().getLocalPart();
                if ("a".equals(tagName)) {
                    Attribute attrHref = startElement.getAttributeByName(new QName("href"));
                    String link = attrHref.getValue();
                    
                   
                    event = (XMLEvent) eventReader.next();
                    if(event.getEventType() == XMLStreamConstants.START_ELEMENT){
                     event = (XMLEvent) eventReader.next();
                        System.out.println("event " + event.toString());
                    }
                    if (event.isCharacters()){
                    Characters character = event.asCharacters();
                    categories.put(link, character.getData());
                   
                    }
                }
            }

        }
       
        return categories;
    }

}
