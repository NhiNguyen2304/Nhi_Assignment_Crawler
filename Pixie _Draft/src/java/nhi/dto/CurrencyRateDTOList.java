/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nhi.dto;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 *
 * @author admin
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "currencyRate",
})
@XmlRootElement (name = "currencyRates")
public class CurrencyRateDTOList{
    @XmlElement(required = true)
    private List<CurrencyRateDTO> currencyRate = new ArrayList<>();

    public List<CurrencyRateDTO> getCurrencies() {
        if (currencyRate == null){
            currencyRate = new ArrayList<CurrencyRateDTO>();
        }
        return this.currencyRate;
    }
}
    
    

