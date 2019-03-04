/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nhi.dto;

import java.io.Serializable;

/**
 *
 * @author admin
 */
public class CurrencyDTO implements Serializable{
    private String currencyCode;
    private String name;
    private float purchaseByCash;
    private float purchaseByTransfer;
    private float sale;
    private  String date;

    public CurrencyDTO() {
    }

    public CurrencyDTO(String currencyCode, String name, float purchaseByCash, float purchaseByTransfer, float sale, String date) {
        this.currencyCode = currencyCode;
        this.name = name;
        this.purchaseByCash = purchaseByCash;
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

    public float getPurchaseByCash() {
        return purchaseByCash;
    }

    public void setPurchaseByCash(float purchaseByCash) {
        this.purchaseByCash = purchaseByCash;
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

    
    
    
    
    
}
