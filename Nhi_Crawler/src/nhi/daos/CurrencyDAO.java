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
import java.util.logging.Level;
import java.util.logging.Logger;
import nhi.data.MyConnection;
import nhi.dto.CurrencyDTO;

/**
 *
 * @author admin
 */
public class CurrencyDAO implements Serializable{
    private Connection conn;
    private PreparedStatement stm;
    private ResultSet rs;

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
     public boolean insertCurrency(CurrencyDTO dto){
         boolean check = false;
         try {
             String sql = "insert into Currency (currencyCode,buying,"
                     + "purchaseByTransfer,selling,date) values (?,?,?,?,?)";
             conn = MyConnection.getConnection();
             stm = conn.prepareStatement(sql);
             stm.setString(1, dto.getCurrencyCode());
             stm.setFloat(2, dto.getPurchaseByCash());
             stm.setFloat(3, dto.getPurchaseByTransfer());
             stm.setFloat(4, dto.getSale());
             stm.setString(5, dto.getDate());
             check = stm.executeUpdate() > 0;
         } catch (Exception ex) {
             System.out.println(" " + ex);
              //Logger.getLogger(CurrencyDAO.class.getName()).log(Level.SEVERE, null, ex);
         }finally{
             closeConnection();
         }
         return check;
     }
     public boolean insertCurrencyDate(String date){
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
         }finally{
             closeConnection();
         }
         return check;
     }
    
}
