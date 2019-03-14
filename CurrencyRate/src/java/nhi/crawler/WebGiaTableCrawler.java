/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nhi.crawler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.namespace.QName;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.Characters;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;
import nhi.daos.CurrencyDAO;
import nhi.dto.CurrencyDTO;
import nhi.state.TextUtils;
import nhi.utils.NhiUtils;

/**
 *
 * @author admin
 */
public class WebGiaTableCrawler extends BaseCrawler {

    public boolean getCurrency(String url, String date) {
        boolean check = false;
       TextUtils checkWellformed = new TextUtils();
        String changingDoc = "";
        BufferedReader reader = null;
        Map<String, String> exchangerate = new HashMap<>();
        try {
            reader = getBufferReadFromURL(url);
            String line = "";
            String document = "";
            boolean isStart = false;

            while ((line = reader.readLine()) != null) {

                if (line.contains("<table class=\"table table-bordered table-hover\">")) {
                    isStart = true;

                }
                if (isStart) {
                    document += line;
                    if (line.contains("</tr></tbody></table>")) {

                        break;
                    }
                    //System.out.println(line);

                }
            }//end while
            if (!document.isEmpty()) {
                String testDoc = document.substring(0, document.indexOf("</table>"));
                changingDoc = checkWellformed.refineHtml(testDoc);
            }
            //System.out.println("Document " + changingDoc);
            if (!changingDoc.isEmpty()) {

                check = sTAXPaeserForCurrency(changingDoc, date);
                
                
            }
            //  return exchangerate;
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(WebGiaTableCrawler.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(WebGiaTableCrawler.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NullPointerException ex) {
            Logger.getLogger(WebGiaTableCrawler.class.getName()).log(Level.SEVERE, null, ex);
        } catch (XMLStreamException ex) {
            Logger.getLogger(WebGiaTableCrawler.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            //System.out.println("Finished");
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException ex) {
                    Logger.getLogger(WebGiaTableCrawler.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return check;
    }
//    public void getCategories(String url, String date) {
//        TextUtils checkWellformed = new TextUtils();
//        String changingDoc = "";
//        BufferedReader reader = null;
//        Map<String, String> exchangerate = new HashMap<>();
//        try {
//            reader = getBufferReadFromURL(url);
//            String line = "";
//            String document = "";
//            boolean isStart = false;
//
//            while ((line = reader.readLine()) != null) {
//
//                if (line.contains("<table class=\"table table-bordered table-hover\">")) {
//                    isStart = true;
//
//                }
//                if (isStart) {
//                    document += line;
//                    if (line.contains("</tr></tbody></table>")) {
//
//                        break;
//                    }
//                    //System.out.println(line);
//
//                }
//            }//end while
//            if (!document.isEmpty()) {
//                String testDoc = document.substring(0, document.indexOf("</table>"));
//                changingDoc = checkWellformed.refineHtml(testDoc);
//            } else {
//                System.out.println("NULLLLL");
//            }
//
//            //System.out.println("Document " + changingDoc);
//            if (!changingDoc.isEmpty()) {
//
//                sTAXPaeserForCurrency(changingDoc, date);
//            }
//            //  return exchangerate;
//        } catch (UnsupportedEncodingException ex) {
//            Logger.getLogger(WebGiaTableCrawler.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (IOException ex) {
//            Logger.getLogger(WebGiaTableCrawler.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (NullPointerException ex) {
//            Logger.getLogger(WebGiaTableCrawler.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (XMLStreamException ex) {
//            Logger.getLogger(WebGiaTableCrawler.class.getName()).log(Level.SEVERE, null, ex);
//        } finally {
//            if (reader != null) {
//                try {
//                    reader.close();
//                } catch (IOException ex) {
//                    Logger.getLogger(WebGiaTableCrawler.class.getName()).log(Level.SEVERE, null, ex);
//                }
//            }
//        }
//        // return null;
//    }

    public boolean sTAXPaeserForCurrency(String document, String date) throws UnsupportedEncodingException, XMLStreamException, IOException {
        boolean isFinished = false;
        document = document.trim();
        XMLEventReader eventReader = parseStringToEventReader(document);
        String id = "";
        String name = "";
        CurrencyDTO dto = new CurrencyDTO();
        CurrencyDAO dao = new CurrencyDAO();
        // 000 = start, 100 (pass first td), 110 (pass second td), 111 (pass all) 
        boolean isBuy = false;
        boolean isPurchaseTransfer = false;
        boolean isSale = false;
        boolean isLast = false;
        float buy = 0;
        float purchaseTransfer = 0;
        float sale = 0;
        while (eventReader.hasNext()) {

            XMLEvent event = (XMLEvent) eventReader.next();
            if (event.isStartElement()) {
                StartElement startElement = event.asStartElement();
                String tagName = startElement.getName().getLocalPart();
                if ("th".equals(tagName)) {
                    // Attribute attrHref = startElement.getAttributeByName(new QName("href"));
                    //String link = attrHref.getValue();
                    event = (XMLEvent) eventReader.next();
                    //System.out.println("EVENT " + event.toString());

                    Characters character = event.asCharacters();
                    id = character.toString();
                    dto.setCurrencyCode(id);

                    //System.out.println("ID " + id);
                    isBuy = false;
                    isPurchaseTransfer = false;
                    isSale = false;
                    //test.put(id, character.getData());
                }
                if ("td".equals(tagName)) {

                    String hidden = "";
                    Attribute attrHref = startElement.getAttributeByName(new QName("class"));
                    if (attrHref != null) {
                        hidden = attrHref.getValue();
                    }
                    if (hidden.equals("hidden-xs")) {
//                        Characters character = event.asCharacters();
//                        id = character.toString();
//                        dto.setCurrencyCode(id);
                        event = (XMLEvent) eventReader.next();
                        Characters character = event.asCharacters();
                        name = character.toString();
                        //dto.setName(name);
                        //System.out.println("Name " + name);
                    } 
                    else {

                        event = (XMLEvent) eventReader.next();
                        if (event.isCharacters()) {
                            Characters character = event.asCharacters();
                            if (isBuy == false && isPurchaseTransfer == false && isSale == false) {
                                buy = NhiUtils.formatCharater(character.toString());
                                
                                    dto.setBuying(buy);
                                
                                // System.out.println("buy " + buy);
                                isBuy = true;
                                continue;

                            }
                            if (isBuy == true && isPurchaseTransfer == false && isSale == false) {
                                purchaseTransfer = NhiUtils.formatCharater(character.toString());
                              
                                    dto.setPurchaseByTransfer(purchaseTransfer);
                                
                                //System.out.println("purchaseTransfer " + purchaseTransfer);
                                isPurchaseTransfer = true;
                                continue;

                            }
                            if (isBuy == true && isPurchaseTransfer == true && isSale == false) {
                                sale = NhiUtils.formatCharater(character.toString());
                                
                                
                                    dto.setSale(sale);
                                
                                //System.out.println("sale " + sale);
                                isSale = true;
                                isLast = true;

                            }

                        }

                    }

                }

                //test.put(id, name);
            }
            
            //System.out.println("Check "  + check);
            if (isBuy == true && isPurchaseTransfer == true && isSale == true && isLast == true) {
                boolean check = false;
                dto.setDate(date);
                check = dao.insertCurrency(dto);
                if (check){
                    isFinished = true;
                }
                isLast = false;
            }
        }

         return isFinished;
    }

    

   

}
