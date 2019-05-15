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
import nhi.daos.CurrencyDAO;
import nhi.dto.CurrencyDTO;
import nhi.properties.NhiGetProperties;
import nhi.state.TextUtils;
import nhi.utils.NhiUtils;

/**
 *
 * @author admin
 */
public class NganHangCrawler extends BaseCrawler {

    public boolean getCurrencyFromURL(String baseUrl, String date) {
        String urlNganHang = null;
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
            //String boxStart = prop.getPropValue("startBox", AppConstant.srcWebGiaXML);
            String boxStart = getConfigProperties("startBox", AppConstant.srcTyGiaXML,
                    this.getClass().getSimpleName());
            String boxEnd = getConfigProperties("endBox", AppConstant.srcTyGiaXML,
                    this.getClass().getSimpleName());
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
                urlNganHang = getURL(wellFormedDoc, date);
                if (urlNganHang != null) {
                    check = getCurrencyUrl(urlNganHang, date);
                }
//                if (urlWebgia != null) {
//
//                    if (type == 2) {
//                        String s = getConfigProperties("startDate", AppConstant.srcWebGiaXML,
//                                this.getClass().getSimpleName());
//                        LocalDate start = LocalDate.parse(s);
//
//                        while (!start.isAfter(LocalDate.now())) {
//                            totalDates.add(start);
//                            start = start.plusDays(1);
//                        }
//                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy"); //for format Date from yyyy-mm-dd to dd-mm-yyyy
//                        LocalDate localDate = LocalDate.now();//For reference
//                        // String urlWebGia = prop.getPropValue("url.webgia", AppConstant.srcWebGiaXML);
//                        for (LocalDate totalDate : totalDates) {
//                            //System.out.println(" " + totalDate.format(formatter));
//
//                            String parseTime = totalDate.format(formatter)
//                                    + getConfigProperties("tailUrlForList", AppConstant.srcWebGiaXML,
//                                            this.getClass().getSimpleName());
//                            check = getCurrencyUrl(urlWebgia + parseTime, totalDate.format(formatter));
//
//                        }
//                    }
//                    if (type == 1) {
//                        check = getCurrencyUrl(urlWebgia, date);
//                    }
//
//                }
            }
        } catch (Exception ex) {
           
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

    private String getURL(String document, String date) throws UnsupportedEncodingException, XMLStreamException {
        String urlWebGia = null;
        XMLEventReader eventReader = parseStringToEventReader(document);
        NhiGetProperties prop = new NhiGetProperties();
        String aTag = getConfigProperties("a", AppConstant.srcWebGiaXML,
                this.getClass().getSimpleName());
        String nameAtt = getConfigProperties("nameAttr", AppConstant.srcWebGiaXML,
                this.getClass().getSimpleName());
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
            String tableStart = getConfigProperties("start", AppConstant.srcTyGiaXML,
                    this.getClass().getSimpleName());
            String tableEnd = getConfigProperties("end", AppConstant.srcTyGiaXML,
                    this.getClass().getSimpleName());
//            checkEnd = getConfigProperties("check.end", AppConstant.srcWebGiaXML,
//                                            this.getClass().getSimpleName());
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
//                int position = document.indexOf(checkEnd);
//                String testDoc = document.substring(0, position);
                wellFormedDoc = checkWellformed.refineHtml(document);
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
        String aTag = null;
        String bTag = null;
        String priceUpAttr = null;
        String priceDownAttr = null;
        String hiddenTag = null;
        String classAttr = null;
        NhiGetProperties prop = new NhiGetProperties();
        thTag = getConfigProperties("th", AppConstant.srcTyGiaXML,
                this.getClass().getSimpleName());
        tdTag = getConfigProperties("td", AppConstant.srcTyGiaXML,
                this.getClass().getSimpleName());
        aTag = getConfigProperties("a", AppConstant.srcTyGiaXML,
                this.getClass().getSimpleName());
        bTag = getConfigProperties("b", AppConstant.srcTyGiaXML,
                this.getClass().getSimpleName());
        priceUpAttr = getConfigProperties("uGoldPrice", AppConstant.srcTyGiaXML,
                this.getClass().getSimpleName());
        priceDownAttr = getConfigProperties("dGoldPrice", AppConstant.srcTyGiaXML,
                this.getClass().getSimpleName());
        classAttr = getConfigProperties("classAttr", AppConstant.srcTyGiaXML, 
                this.getClass().getSimpleName());
        while (eventReader.hasNext()) {

            XMLEvent event = (XMLEvent) eventReader.next();
            if (event.isStartElement()) {
                StartElement startElement = event.asStartElement();
                String tagName = startElement.getName().getLocalPart();
                if (bTag.equals(tagName)) {
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

                    Attribute attrHref = startElement.getAttributeByName(new QName(classAttr));
                    if (attrHref != null) {
                        hidden = attrHref.getValue().trim();
                    }
                    if (hidden.equals(priceUpAttr) || hidden.equals(priceDownAttr)) {
//                        Characters character = event.asCharacters();
//                        id = character.toString();
//                        dto.setCurrencyCode(id);
                        event = (XMLEvent) eventReader.next();
                        Characters character = event.asCharacters();

                        if (isBuy == false && isPurchaseTransfer == false && isSale == false) {
                            buy = NhiUtils.formatCharater(character.toString());
                            if (buy != 0) {
                                dto.setBuying(buy);
                                isBuy = true;
                                continue;
                            }

                            System.out.println("buy " + buy);
                        }
                        if (isBuy == true && isPurchaseTransfer == false && isSale == false) {
                            purchaseTransfer = NhiUtils.formatCharater(character.toString());
                            if (purchaseTransfer != 0) {
                                dto.setPurchaseByTransfer(purchaseTransfer);
                                isPurchaseTransfer = true;
                                continue;
                            }

                            //System.out.println("purchaseTransfer " + purchaseTransfer);
                        }
                        if (isBuy == true && isPurchaseTransfer == true && isSale == false) {
                            sale = NhiUtils.formatCharater(character.toString());
                            if (sale != 0) {
                                dto.setSale(sale);
                                isSale = true;
                                isLast = true;
                            }

                            System.out.println("sale " + sale);
                        }

                        //test.put(id, name);
                    }

                    //System.out.println("Check "  + check);
                }

            }
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
