/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nhi.dto;

import java.io.Serializable;
import java.util.List;

/**
 *
 * @author admin
 */
public class CurrencyDTO implements Serializable{
    private String currencyCode;
    private String name;
    private float buying;
    private float purchaseByTransfer;
    private float sale;
    private  String date;
    private int comparable;
    

    public CurrencyDTO() {
    }

    public CurrencyDTO(String currencyCode, String name, float buying, float purchaseByTransfer, float sale, String date) {
        this.currencyCode = currencyCode;
        this.name = name;
        this.buying = buying;
        this.purchaseByTransfer = purchaseByTransfer;
        this.sale = sale;
        this.date = date;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public float getComparable() {
        return comparable;
    }

    public void setComparable(int comparable) {
        this.comparable = comparable;
    }

    
    
    
    
    
    
}
