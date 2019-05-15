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
    "gold",
})
@XmlRootElement (name = "golds")
public class GoldDTOList{
    @XmlElement(required = true)
    private List<GoldDTO> gold = new ArrayList<>();

    public List<GoldDTO> getGolds() {
        if (gold == null){
            gold = new ArrayList<GoldDTO>();
        }
        return this.gold;
    }
}
    
    

