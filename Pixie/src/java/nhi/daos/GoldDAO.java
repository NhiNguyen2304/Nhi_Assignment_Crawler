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
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import nhi.data.MyConnection;
import nhi.dto.GoldDTO;
import nhi.dto.GoldDTOList;

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
            String sql = "insert into Gold (typeOfGold,selling,buying,district,date) values (?,?,?,?,?)";
            conn = MyConnection.getConnection();
            stm = conn.prepareStatement(sql);
            stm.setString(1, dto.getTypeOfGold());
            stm.setFloat(2, dto.getSale());
            stm.setFloat(3, dto.getBuy());
            stm.setString(4, dto.getDistrict());
            stm.setString(5, dto.getDate());
            check = stm.executeUpdate() > 0;
        } catch (SQLException ex) {
            if (!ex.getMessage().contains("duplicate")) {
                Logger.getLogger(GoldDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }finally{
            closeConnection();
        }
        return check;
    }
    
    public GoldDTOList getGoldRateByDateList(ArrayList<String> listDate) {
        //List<CurrencyRate30DTOList> list = new ArrayList<CurrencyRate30DTOList>();
        //List<CurrencyRate30DTOList> listTmp = new ArrayList<CurrencyRate30DTOList>();
        GoldDTOList list = new GoldDTOList();
        String typeOfGold = null;
        String district = null;
        String date = null;
        float buyingRate = 0;
        float sellingRate = 0;
        boolean checkEx = false;
        String firstDate = "";
        String secondDate = "";
        int count = 1;
        for (int i = 0; i < listDate.size(); i++) {

            try {
                String sql = "EXEC GoldTmp ?,?";
                conn = MyConnection.getConnection();
                stm = conn.prepareStatement(sql);
                stm.setString(1, listDate.get(i));
                int nextDate = i + 1;
                if (nextDate == listDate.size()){
                    break;
                }
                stm.setString(2, listDate.get(nextDate));
                rs = stm.executeQuery();

                //Currency2DTO dto = new Currency2DTO();
                while (rs.next()) {
                    typeOfGold = rs.getString("typeOfGold");
                    buyingRate = rs.getFloat("buyingRate");
                    sellingRate = rs.getFloat("sellingRate");
                    date = rs.getString("currDate");
                    district = rs.getString("district");
                    GoldDTO dto = new GoldDTO(typeOfGold, buyingRate, sellingRate, district, date);
                    list.getGolds().add(dto);
                }
            } catch (SQLException ex) {
                Logger.getLogger(CurrencyDAO.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                closeConnection();
            }
          

        }

        return list;
    }
    public GoldDTOList getCurrencyRateByDate(String currDate, String prevDate) {
        GoldDTOList list = new GoldDTOList();
         String typeOfGold = null;
        String district = null;
        String date = null;
        float buyingRate = 0;
        float sellingRate = 0;
        
        try {
            String sql = "EXEC GoldTmp ?,?";
            conn = MyConnection.getConnection();
            stm = conn.prepareStatement(sql);
            stm.setString(1, currDate);
            stm.setString(2, prevDate);
            rs = stm.executeQuery();
            //Currency2DTO dto = new Currency2DTO();
            while (rs.next()) {
                 typeOfGold = rs.getString("typeOfGold");
                    buyingRate = rs.getFloat("buyingRate");
                    sellingRate = rs.getFloat("sellingRate");
                    date = rs.getString("currDate");
                    district = rs.getString("district");
                    GoldDTO dto = new GoldDTO(typeOfGold, buyingRate, sellingRate, district, date);
                    list.getGolds().add(dto);

            }
        } catch (SQLException ex) {
            Logger.getLogger(CurrencyDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            closeConnection();
        }

        return list;
    }
}
