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
public class TypeOfCurrencyDTO implements Serializable{
    private String code;
    private String name;

    public TypeOfCurrencyDTO() {
    }

    public TypeOfCurrencyDTO(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
}
