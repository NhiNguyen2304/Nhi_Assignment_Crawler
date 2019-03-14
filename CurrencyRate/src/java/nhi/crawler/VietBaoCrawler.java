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
import nhi.daos.GoldDAO;
import nhi.dto.GoldDTO;
import nhi.state.TextUtils;
import nhi.utils.NhiUtils;

/**
 *
 * @author admin
 */
public class VietBaoCrawler extends BaseCrawler {

    public boolean geCurrency(String url, String date) {
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

                if (line.contains("<table class=\"tbl-gold\">")) {
                    isStart = true;

                }
                if (isStart) {
                    document += line;
                    if (line.contains("</table>")) {
                        break;
                    }
                    //System.out.println(line);

                }
            }//end while
            if (!document.isEmpty()) {
                //String testDoc = document.substring(0, document.indexOf("</table>"));
                changingDoc = checkWellformed.refineHtml(document);
            } else {
                System.out.println("NULLLLL");
            }

            //System.out.println("Document " + changingDoc);
            if (!changingDoc.isEmpty()) {
                check = sTAXPaeserForGold(changingDoc, date);
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

    public boolean sTAXPaeserForGold(String document, String date) throws UnsupportedEncodingException, XMLStreamException, IOException {
        boolean isFinished = false;
        document = document.trim();
        XMLEventReader eventReader = parseStringToEventReader(document);
        String id = "";
        String district = "";
        GoldDTO dto = new GoldDTO();
        GoldDAO dao = new GoldDAO();
        // 000 = start, 100 (pass first td), 110 (pass second td), 111 (pass all) 
        boolean isBuy = false;
        boolean isSale = false;
        boolean isLast = false;

        String buy = "";
        String sold = "";
        String typeOfGold = "";
        String goldPrice = "";
        while (eventReader.hasNext()) {

            XMLEvent event = (XMLEvent) eventReader.next();

            if (event.isStartElement()) {
                StartElement startElement = event.asStartElement();
                String tagName = startElement.getName().getLocalPart();

                if ("strong".equals(tagName)) {
                    event = (XMLEvent) eventReader.next();
                    if (event.isCharacters()) {
                        Characters character = event.asCharacters();
                        district = character.toString();
                        dto.setDistrict(district);
                        //System.out.println("Name " + district);

                    }

                }
                if ("td".equals(tagName)) {
                    String goldName = "";
                    Attribute attrGoldName = startElement.getAttributeByName(new QName("class"));
                    if (attrGoldName != null) {
                        goldName = attrGoldName.getValue().trim();
                    }
                    if (goldName.equals("gold-name") || goldName.equals("bg-yelowlight gold-name")) {
                        event = (XMLEvent) eventReader.next();
                        Characters character = event.asCharacters();
                        typeOfGold = character.toString();
                        dto.setTypeOfGold(typeOfGold);
                        //System.out.println("Type " + typeOfGold);
                    }
                }
                if ("div".equals(tagName)) {
                    String goldPriceTmp = "";
                    Attribute attrGoldPrice = startElement.getAttributeByName(new QName("class"));
                    if (attrGoldPrice != null) {
                        goldPriceTmp = attrGoldPrice.getValue().trim();
                    }
                    if (goldPriceTmp.equals("gold-price price-down") || goldPriceTmp.equals("gold-price price-up") || goldPriceTmp.equals("gold-price")) {
                        event = (XMLEvent) eventReader.next();
                        Characters character = event.asCharacters();
                        if (isBuy == false && isSale == false) {
                            buy = character.toString();
                            dto.setBuy(Float.parseFloat(buy));
                            //System.out.println("Buy Price " + buy);
                            isBuy = true;
                            continue;
                        }
                        if (isBuy == true && isSale == false) {
                            sold = character.toString();
                            dto.setSale(Float.parseFloat(sold));
                            //System.out.println("Sold Price " + sold);
                            isSale = true;
                            isLast = true;
                        }

                    }

                }

            }// close if event.startElement
            if (isBuy == true && isSale == true && isLast == true) {
                boolean check = false;
                dto.setDate(NhiUtils.formatDate(date));
                check = dao.insertGold(dto);
                if (check) {
                    isFinished = true;
                }
                isLast = false;
                isBuy = false;
                isSale = false;
            }

            //test.put(id, name);
        }

        //System.out.println("Date " + formatDate(date));
        //System.out.println("Date " + date);
//            //System.out.println("Check "  + check);
//            if (isBuy == true && isPurchaseTransfer == true && isSale == true && isLast == true) {
//                boolean check = false;
//                dto.setDate(date);
//                check = dao.crawlerCurrency(dto);
//                isLast = false;
//            }
            return isFinished;
    }

   
    // return test;
}
