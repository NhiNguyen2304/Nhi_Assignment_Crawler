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
public class GoldDTO implements Serializable{
    private String district;
    private String typeOfGold;
    private float buy;
    private float sale;
    private String date;

    public GoldDTO() {
    }

    public GoldDTO(String district, String typeOfGold, float buy, float sale, String date) {
        this.district = district;
        this.typeOfGold = typeOfGold;
        this.buy = buy;
        this.sale = sale;
        this.date = date;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getTypeOfGold() {
        return typeOfGold;
    }

    public void setTypeOfGold(String typeOfGold) {
        this.typeOfGold = typeOfGold;
    }

    public float getBuy() {
        return buy;
    }

    public void setBuy(float buy) {
        this.buy = buy;
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
