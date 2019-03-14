/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nhi.dto;

import java.io.Serializable;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.bind.annotation.XmlType;

/**
 *
 * @author admin
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "currency", propOrder = {
    "currencyCode",
    "buying",
    "purchaseByTransfer",
    "sale",
    "buyingRate",
    "date"
})
public class CurrencyDTO implements Serializable {
    @XmlElement(required = true)
    private String currencyCode;
    private float buying;
    private float purchaseByTransfer;
    private float sale;
    private int buyingRate;
    private String date;

    public CurrencyDTO() {
    }

    public CurrencyDTO(String currencyCode, float buying, float purchaseByTransfer, float sale, int buyingRate, String date) {
        this.currencyCode = currencyCode;
        this.buying = buying;
        this.purchaseByTransfer = purchaseByTransfer;
        this.sale = sale;
        this.buyingRate = buyingRate;
        this.date = date;
    }

    
    

    
    

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }


    public float getBuying() {
        return buying;
    }

    public void setBuying(float buying) {
        this.buying = buying;
    }

    public float getPurchaseByTransfer() {
        return purchaseByTransfer;
    }

    public void setPurchaseByTransfer(float purchaseByTransfer) {
        this.purchaseByTransfer = purchaseByTransfer;
    }

    public float getSale() {
        return sale;
    }

    public void setSale(float sale) {
        this.sale = sale;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getBuyingRate() {
        return buyingRate;
    }

    public void setBuyingRate(int buyingRate) {
        this.buyingRate = buyingRate;
    }

   

    

}
