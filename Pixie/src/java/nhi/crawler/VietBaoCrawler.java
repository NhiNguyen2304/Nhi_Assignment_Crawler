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
import java.util.HashMap;
import java.util.List;
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
import nhi.properties.NhiGetProperties;
import nhi.state.TextUtils;
import nhi.utils.NhiUtils;

/**
 *
 * @author admin
 */
public class VietBaoCrawler extends BaseCrawler {

    public boolean getGoldFromURL(String baseUrl, String date, int type) {
        String urlVietBao = null;
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
           
            String boxStart = getConfigProperties("startTableUrl", AppConstant.srcVietBaoXML,
                    this.getClass().getSimpleName());
            String boxEnd = getConfigProperties("endTableUrl", AppConstant.srcVietBaoXML,
                    this.getClass().getSimpleName());
            boolean isStart = false;
            boolean isEnd = false;
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
                Logger.getLogger(VietBaoCrawler.class.getName()).log(Level.SEVERE, null, "Null Document");
            }

            //System.out.println("Document " + changingDoc);
            if (!wellFormedDoc.isEmpty()) {
                urlVietBao = getURL(wellFormedDoc, date);
                if (type == 2) {
                  
                     String s =  getConfigProperties("startDate", AppConstant.srcVietBaoXML,
                    this.getClass().getSimpleName());
                    LocalDate start = LocalDate.parse(s);

                    while (!start.isAfter(LocalDate.now())) {
                        totalDates.add(start);
                        start = start.plusDays(1);
                    }
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy"); //for format Date from yyyy-mm-dd to dd-mm-yyyy
                    LocalDate localDate = LocalDate.now();//For reference
                    // String urlWebGia = prop.getPropValue("url.webgia", AppConstant.srcWebGiaXML);
                    
                    String elementFormat = getConfigProperties("formatUrlEle", AppConstant.srcVietBaoXML,
                    this.getClass().getSimpleName());
                    for (LocalDate totalDate : totalDates) {
                        //System.out.println(" " + totalDate.format(formatter));

                        String parseTime = elementFormat + totalDate.format(formatter);
                        check = getGold(urlVietBao + parseTime, totalDate.format(formatter));

                    }
                }
                if (type == 1) {
                    check = getGold(urlVietBao, date);
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
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
        String urlVietbao = null;
        XMLEventReader eventReader = parseStringToEventReader(document);
        NhiGetProperties prop = new NhiGetProperties();
        String aTag = getConfigProperties("a", AppConstant.srcVietBaoXML,
                    this.getClass().getSimpleName());
        String nameAtt = getConfigProperties("nameAttr", AppConstant.srcVietBaoXML,
                    this.getClass().getSimpleName());
        try {
            while (eventReader.hasNext()) {
                XMLEvent event = (XMLEvent) eventReader.next();
                if (event.isStartElement()) {
                    StartElement startElement = event.asStartElement();
                    String tagName = startElement.getName().getLocalPart();
                    if (tagName.equals(aTag)) {
                        Attribute attrUrl = startElement.getAttributeByName(new QName(nameAtt));
                        urlVietbao = attrUrl.getValue().trim();
                        break;
                    }
                }

            }
            //  System.out.println("URL: " + urlVietbao);

        } catch (Exception e) {
            Logger.getLogger(WebGiaTableCrawler.class.getName()).log(Level.SEVERE, null, e);
        }
        return urlVietbao;
    }

    public boolean getGold(String url, String date) {

       
        boolean check = false;
        TextUtils checkWellformed = new TextUtils();
        String wellFormedDoc = "";
        BufferedReader reader = null;
        Map<String, String> exchangerate = new HashMap<>();
        try {
            reader = getBufferReadFromURL(url);
            String line = "";
            String document = "";
            boolean isStart = false;
            String start = getConfigProperties("start", AppConstant.srcVietBaoXML,
                    this.getClass().getSimpleName());
            String end = getConfigProperties("end", AppConstant.srcVietBaoXML,
                    this.getClass().getSimpleName());

            while ((line = reader.readLine()) != null) {

                if (line.contains(start)) {
                    isStart = true;

                }
                if (isStart) {
                    document += line;
                    if (line.contains(end)) {
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
                check = sTAXPaeserForGold(wellFormedDoc, date);
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
        GoldDTO dto = new GoldDTO();
        GoldDAO dao = new GoldDAO();
        // 000 = start, 100 (pass first td), 110 (pass second td), 111 (pass all) 
        boolean isBuy = false;
        boolean isSale = false;
        boolean isLast = false;

        String buy = "";
        String dateFormat = "";
        String sold = "";
        String district = "";
        String typeOfGold = "";
        String goldPrice = "";
        NhiGetProperties prop = new NhiGetProperties();
        getConfigProperties("dGoldPrice", AppConstant.srcVietBaoXML,
                    this.getClass().getSimpleName());
        String divTag = getConfigProperties("div", AppConstant.srcVietBaoXML,
                    this.getClass().getSimpleName());
        String tdTag =  getConfigProperties("td", AppConstant.srcVietBaoXML,
                    this.getClass().getSimpleName());
        String strongTag =  getConfigProperties("strong", AppConstant.srcVietBaoXML,
                    this.getClass().getSimpleName());
        String goldNameTag = getConfigProperties("goldName", AppConstant.srcVietBaoXML,
                    this.getClass().getSimpleName());
        String ygoldNameTag = getConfigProperties("yGoldName", AppConstant.srcVietBaoXML,
                    this.getClass().getSimpleName());
        String goldPriceTag = getConfigProperties("goldPrice", AppConstant.srcVietBaoXML,
                    this.getClass().getSimpleName());
        String goldPriceUpTag = getConfigProperties("uGoldPrice", AppConstant.srcVietBaoXML,
                    this.getClass().getSimpleName());
        String goldPriceDownTag = getConfigProperties("dGoldPrice", AppConstant.srcVietBaoXML,
                    this.getClass().getSimpleName());

        while (eventReader.hasNext()) {

            XMLEvent event = (XMLEvent) eventReader.next();

            if (event.isStartElement()) {
                StartElement startElement = event.asStartElement();
                String tagName = startElement.getName().getLocalPart();
                if (strongTag.equals(tagName)) {
                    event = (XMLEvent) eventReader.next();
                    if (event.isCharacters()) {
                        Characters character = event.asCharacters();
                        district = character.toString();
                        dto.setDistrict(district);
                        System.out.println("Name " + district);

                    }

                }

                if (tdTag.equals(tagName)) {
                    String goldName = "";
                    Attribute attrGoldName = startElement.getAttributeByName(new QName("class"));
                    if (attrGoldName != null) {
                        goldName = attrGoldName.getValue().trim();
                    }
                    if (goldName.equals(goldNameTag) || goldName.equals(ygoldNameTag)) {
                        event = (XMLEvent) eventReader.next();
                        Characters character = event.asCharacters();
                        typeOfGold = character.toString();

                        dto.setTypeOfGold(typeOfGold);
                        System.out.println("Type " + typeOfGold);
                    }
                }
                if (divTag.equals(tagName)) {
                    String goldPriceTmp = "";
                    Attribute attrGoldPrice = startElement.getAttributeByName(new QName("class"));
                    if (attrGoldPrice != null) {
                        goldPriceTmp = attrGoldPrice.getValue().trim();
                    }
                    if (goldPriceTmp.equals(goldPriceDownTag) || goldPriceTmp.equals(goldPriceUpTag) || goldPriceTmp.equals(goldPriceTag)) {
                        event = (XMLEvent) eventReader.next();
                        Characters character = event.asCharacters();
                        if (isBuy == false && isSale == false) {
                            buy = character.toString();
                            dto.setBuy(Float.parseFloat(buy));
                            System.out.println("Buy Price: " + buy);
                            isBuy = true;
                            continue;
                        }
                        if (isBuy == true && isSale == false) {
                            sold = character.toString();
                            dto.setSale(Float.parseFloat(sold));
                            System.out.println("Sold Price: " + sold);
                            isSale = true;
                            isLast = true;
                        }

                    }

                }

            }// close if event.startElement
            if (isBuy == true && isSale == true && isLast == true) {
                boolean check = false;
                
                String redundancy = getConfigProperties("formatUrlEle", AppConstant.srcVietBaoXML,
                    this.getClass().getSimpleName());
                dateFormat = date.replace(redundancy, "").trim();
                dto.setDate(dateFormat);
                System.out.println("Date " + dateFormat);
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
