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

/**
 *
 * @author admin
 */
public class RegistrationDAO implements Serializable{
    private Connection conn;
    private PreparedStatement stm;
    private ResultSet rs;
    private float com;

    public RegistrationDAO() {
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
            //System.out.println(" " + ex);
            Logger.getLogger(CurrencyDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
   public String checkLogin(String username, String password) throws Exception {
         String role = "failed";
        try {
            String sql = "select role from Registration where username = ? and password = ?";
            conn = MyConnection.getConnection();
            stm = conn.prepareStatement(sql);
            stm.setString(1, username);
            stm.setString(2, password);
            rs = stm.executeQuery();
            if (rs.next()) {
                role = rs.getString("role");
            }
        } catch (Exception e) {
              Logger.getLogger(CurrencyDAO.class.getName()).log(Level.SEVERE, null, e);
        }
        return role;
    }
    
}
