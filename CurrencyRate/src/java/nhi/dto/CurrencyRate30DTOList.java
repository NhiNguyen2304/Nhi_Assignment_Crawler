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
@XmlType(name = "", propOrder = {
    "currencyRate",
})
@XmlRootElement (name = "currencyRates")
public class CurrencyRate30DTOList {
    @XmlElement(required = true)
    private List<CurrencyRateDTO> currencyRate = new ArrayList<>();

    public List<CurrencyRateDTO> getCurrencies() {
        if (currencyRate == null){
            currencyRate = new ArrayList<CurrencyRateDTO>();
        }
        return this.currencyRate;
    }
}
    
//@XmlAccessorType(XmlAccessType.FIELD)
//@XmlRootElement(name = "currencyRates")
//public class CurrencyRate30DTOList<T> {
//
//    protected List<T> list;
//
//    public CurrencyRate30DTOList() {
//    }
//
//    public CurrencyRate30DTOList(List<T> list) {
//        this.list = list;
//    }
//
//    @XmlElement(name = "currencyRate")
//    public List<T> getList(T e) {
//       
//         this.list.add(e);
//        
//        return this.list;
//    }
//}
