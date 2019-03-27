/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nhi.crawler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
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
import nhi.properties.NhiGetProperties;
import nhi.state.TextUtils;
import nhi.utils.NhiUtils;

/**
 *
 * @author admin
 */
public class WebGiaTableCrawler extends BaseCrawler {

    public boolean getCurrencyFromURL(String baseUrl, String date, int type) {
        String urlWebgia = null;
        BufferedReader reader = null;

        NhiGetProperties prop = new NhiGetProperties();
        TextUtils checkWellformed = new TextUtils();
        String wellFormedDoc = "";
        String line = "";
        String document = "";
        boolean check = false;
        List<LocalDate> totalDates = new ArrayList<>();
        try {
            reader = getBufferReadFromURL(baseUrl);
            String boxStart = prop.getPropValue("startBox", AppConstant.srcWebGiaXML);
            String boxEnd = prop.getPropValue("endBox", AppConstant.srcWebGiaXML);
            boolean isStart = false;
            
            while ((line = reader.readLine()) != null) {

                if (line.contains(boxStart)) {
                    isStart = true;

                }
                if (isStart) {
                    document += line;
                    if (line.contains(boxEnd)) {
                        break;
                    }
                    //System.out.println(line);

                }
            }//end while
            if (!document.isEmpty()) {
                //String testDoc = document.substring(0, document.indexOf("</table>"));
                wellFormedDoc = checkWellformed.refineHtml(document);
            } else {
                Logger.getLogger(WebGiaTableCrawler.class.getName()).log(Level.SEVERE, null, "Null Document");
            }

            //System.out.println("Document " + changingDoc);
            if (!wellFormedDoc.isEmpty()) {
                urlWebgia = getURL(wellFormedDoc, date);
                if (urlWebgia != null) {

                    if (type == 2) {
                        String s = prop.getPropValue("startDate", AppConstant.srcWebGiaXML);
                        LocalDate start = LocalDate.parse(s);

                        while (!start.isAfter(LocalDate.now())) {
                            totalDates.add(start);
                            start = start.plusDays(1);
                        }
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy"); //for format Date from yyyy-mm-dd to dd-mm-yyyy
                        LocalDate localDate = LocalDate.now();//For reference
                        // String urlWebGia = prop.getPropValue("url.webgia", AppConstant.srcWebGiaXML);
                        for (LocalDate totalDate : totalDates) {
                            //System.out.println(" " + totalDate.format(formatter));

                            String parseTime = totalDate.format(formatter) + prop.getPropValue("tailUrlForList",  AppConstant.srcWebGiaXML);
                            check = getCurrencyUrl(urlWebgia + parseTime, totalDate.format(formatter));

                        }
                    }
                    if (type == 1) {
                        check = getCurrencyUrl(urlWebgia, date);
                    }

                }
            }
        } catch (Exception e) {
           Logger.getLogger(WebGiaTableCrawler.class.getName()).log(Level.SEVERE, null, e);
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

    private String getURL(String document, String date) throws UnsupportedEncodingException, XMLStreamException {
        String urlWebGia = null;
        XMLEventReader eventReader = parseStringToEventReader(document);
        NhiGetProperties prop = new NhiGetProperties();
        String aTag = prop.getPropValue("a", AppConstant.srcWebGiaXML);
        String nameAtt = prop.getPropValue("nameAttr", AppConstant.srcWebGiaXML);
        try {
            while (eventReader.hasNext()) {
                XMLEvent event = (XMLEvent) eventReader.next();
                if (event.isStartElement()) {
                    StartElement startElement = event.asStartElement();
                    String tagName = startElement.getName().getLocalPart();
                    if (tagName.equals(aTag)) {
                        Attribute attrUrl = startElement.getAttributeByName(new QName(nameAtt));
                        urlWebGia = attrUrl.getValue().trim();
                        break;
                    }
                }

            }
            System.out.println("URL: " + urlWebGia);

        } catch (Exception e) {
            Logger.getLogger(WebGiaTableCrawler.class.getName()).log(Level.SEVERE, null, e);
        }
        return urlWebGia;
    }

    public boolean getCurrencyUrl(String url, String date) {

        //NhiSaveProperties savePro = new NhiSaveProperties();
        //savePro.saveProInXml(AppConstant.srcWebGiaProp, AppConstant.srcWebGiaXML);
        NhiGetProperties prop = new NhiGetProperties();
        boolean check = false;
        TextUtils checkWellformed = new TextUtils();
        String wellFormedDoc = "";
        BufferedReader reader = null;

        try {
            reader = getBufferReadFromURL(url);
            String line = "";
            String document = "";
            boolean isStart = false;
            boolean isEnd = false;
            String checkEnd = null;
            String tableStart = prop.getPropValue("start", AppConstant.srcWebGiaXML);
            String tableEnd = prop.getPropValue("end", AppConstant.srcWebGiaXML);
            checkEnd = prop.getPropValue("check.end", AppConstant.srcWebGiaXML);
            while ((line = reader.readLine()) != null) {

                if (line.compareTo(tableStart) > 0) {
                    isStart = true;

                }
                if (isStart) {
                    document += line;
                    if (line.compareTo(tableEnd) > 0) {
                        isEnd = true;
                    }
                    //System.out.println(line);

                }
            }//end while
            if (!document.isEmpty() && isStart == true && isEnd == true) {
                int position = document.indexOf(checkEnd);
                String testDoc = document.substring(0, position);
                wellFormedDoc = checkWellformed.refineHtml(testDoc);
            }
            //System.out.println("Document " + changingDoc);
            if (!wellFormedDoc.isEmpty()) {

                check = sTAXPaeserForCurrency(wellFormedDoc, date);

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

    private boolean sTAXPaeserForCurrency(String document, String date) throws UnsupportedEncodingException, XMLStreamException, IOException {
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
        String thTag = null;
        String tdTag = null;
        String hiddenTag = null;
        NhiGetProperties prop = new NhiGetProperties();
        thTag = prop.getPropValue("th", AppConstant.srcWebGiaXML);
        tdTag = prop.getPropValue("td", AppConstant.srcWebGiaXML);
        hiddenTag = prop.getPropValue("hidden", AppConstant.srcWebGiaXML);
        while (eventReader.hasNext()) {

            XMLEvent event = (XMLEvent) eventReader.next();
            if (event.isStartElement()) {
                StartElement startElement = event.asStartElement();
                String tagName = startElement.getName().getLocalPart();
                if (thTag.equals(tagName)) {
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
                if (tdTag.equals(tagName)) {

                    String hidden = "";
                    Attribute attrHref = startElement.getAttributeByName(new QName("class"));
                    if (attrHref != null) {
                        hidden = attrHref.getValue();
                    }
                    if (hidden.equals(hiddenTag)) {
//                        Characters character = event.asCharacters();
//                        id = character.toString();
//                        dto.setCurrencyCode(id);
                        event = (XMLEvent) eventReader.next();
                        Characters character = event.asCharacters();
                        name = character.toString();
                        //dto.setName(name);
                        //System.out.println("Name " + name);
                    } else {

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
                if (check) {
                    isFinished = true;
                }
                isLast = false;
            }
        }

        return isFinished;
    }

}
