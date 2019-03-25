/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nhi.dto;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

/**
 *
 * @author admin
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "gold", propOrder = {
    "typeOfGold",
    "sale",
    "buy",
    "district",
    "date",
})
public class GoldDTO implements Serializable {
    private String typeOfGold;
    private float sale;
    private float buy;
    private String district;
    private String date;

    public GoldDTO() {
    }

    public GoldDTO(String typeOfGold, float buy, float sale, String district, String date) {
        this.typeOfGold = typeOfGold;
        this.buy = buy;
        this.sale = sale;
        this.district = district;
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
