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
import nhi.data.MyConnection;
import nhi.dto.GoldDTO;

/**
 *
 * @author admin
 */
public class GoldDAO implements Serializable {

    private Connection conn;
    private PreparedStatement stm;
    private ResultSet rs;

    public GoldDAO() {
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
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean insertGold(GoldDTO dto) {
        boolean check = false;
        try {
            String sql = "insert into Gold (district,typeOfGold,buying,selling,date) values (?,?,?,?,?)";
            conn = MyConnection.getConnection();
            stm = conn.prepareStatement(sql);
            stm.setString(1, dto.getDistrict());
            stm.setString(2, dto.getTypeOfGold());
            stm.setFloat(3, dto.getBuy());
            stm.setFloat(4, dto.getSale());
            stm.setString(5, dto.getDate());
            check = stm.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return check;
    }
}
