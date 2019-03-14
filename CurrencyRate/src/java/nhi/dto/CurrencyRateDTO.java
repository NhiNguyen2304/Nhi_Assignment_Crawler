/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nhi.dto;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 *
 * @author admin
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "currency", propOrder = {
    "currencyCode",
    "name",
    "buyingRate",
    "transferRate",
    "sellingRate",
    "date"
})
public class CurrencyRateDTO implements Serializable{
    @XmlElement(required = true)
    private String currencyCode;
    private String name;
    private int buyingRate;
    private int transferRate;
    private int sellingRate;
    private String date;

    public CurrencyRateDTO() {
    }

    public CurrencyRateDTO(String currencyCode, String name, int buyingRate, int transferRate, int sellingRate, String date) {
        this.currencyCode = currencyCode;
        this.name = name;
        this.buyingRate = buyingRate;
        this.transferRate = transferRate;
        this.sellingRate = sellingRate;
        this.date = date;
    }

    
   
    
    

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }


   
    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getBuyingRate() {
        return buyingRate;
    }

    public void setBuyingRate(int buyingRate) {
        this.buyingRate = buyingRate;
    }

    public int getTransferRate() {
        return transferRate;
    }

    public void setTransferRate(int transferRate) {
        this.transferRate = transferRate;
    }

    public int getSellingRate() {
        return sellingRate;
    }

    public void setSellingRate(int sellingRate) {
        this.sellingRate = sellingRate;
    }

  

    

}
