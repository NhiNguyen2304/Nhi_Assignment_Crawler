/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nhi.daos;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import nhi.data.MyConnection;
import nhi.dto.CurrencyDTO;

/**
 *
 * @author admin
 */
public class CurrencyDAO implements Serializable {

    private Connection conn;
    private PreparedStatement stm;
    private ResultSet rs;
    private float com;

    public CurrencyDAO() {
    }

    private void closeConnection() {
        try {
            if (rs != null) {
                rs.close();
            }
            if (stm != null) {
                stm.close();
            }
            if (conn != null) {
                conn.close();
            }
        } catch (Exception ex) {
            System.out.println(" " + ex);
            //Logger.getLogger(CurrencyDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public boolean insertCurrency(CurrencyDTO dto) {
        boolean check = false;
        try {
            String sql = "insert into Currency (currencyCode,name,buying,"
                    + "purchaseByTransfer,selling,date) values (?,?,?,?,?,?)";
            conn = MyConnection.getConnection();
            stm = conn.prepareStatement(sql);
            stm.setString(1, dto.getCurrencyCode());
            stm.setString(2, dto.getName());
            stm.setFloat(3, dto.getBuying());
            stm.setFloat(4, dto.getPurchaseByTransfer());
            stm.setFloat(5, dto.getSale());
            stm.setString(6, dto.getDate());
            check = stm.executeUpdate() > 0;
        } catch (Exception ex) {
            System.out.println(" " + ex);
            //Logger.getLogger(CurrencyDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            closeConnection();
        }
        return check;
    }

    public boolean insertCurrencyDate(String date) {
        boolean check = false;
        try {
            String sql = "insert into Currency (date) values (?)";
            conn = MyConnection.getConnection();
            stm = conn.prepareStatement(sql);
            stm.setString(1, date);
            check = stm.executeUpdate() > 0;

        } catch (Exception ex) {
            System.out.println(" " + ex);
            // Logger.getLogger(CurrencyDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            closeConnection();
        }
        return check;
    }

    public ArrayList<CurrencyDTO> getRateCurrency(String code) {
        ArrayList<CurrencyDTO> comparables = null;
        ArrayList<CurrencyDTO> comparablesTmp = null;
        CurrencyDTO dtoTmp = new CurrencyDTO();
        float temp = 0;
        float compaPresent = 0;
        float result = 0;
        boolean isFind = false;
        try {
            String sql = "Select buying from Currency where currencyCode = ?";
            conn = MyConnection.getConnection();
            stm = conn.prepareStatement(sql);
            stm.setString(1, code);
            rs = stm.executeQuery();
            comparables = new ArrayList<>();
            comparablesTmp = new ArrayList<>();
            while (rs.next()) {
                CurrencyDTO dto = new CurrencyDTO();
                // get current rate - past rate
                if (!isFind) {
                    temp = rs.getFloat("buying");
                    isFind = true;
                } else {
                    compaPresent = rs.getFloat("buying");
                    result = compaPresent - temp;
                    dto.setComparable((int) result);
                    comparables.add(dto);
                    temp = compaPresent;
                }
                //CurrencyDTO dtoTemp = new CurrencyDTO();
                //dto.setCurrencyCode(rs.getString("currencyCode"));

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return comparables;
    }
    public float getCurrency(String id, String date){
    float currency = 0;
        try {
            String sql = "Select buying from Currency where currencyCode=? and date=?";
            conn = MyConnection.getConnection();
            stm = conn.prepareStatement(sql);
            stm.setString(1, id);
            stm.setString(2, date);
            rs = stm.executeQuery();
            if(rs.next()){
                currency = rs.getFloat("buying");
            }
        } catch (Exception e) { 
            e.printStackTrace();
        }
        return currency;
    
    }

}
