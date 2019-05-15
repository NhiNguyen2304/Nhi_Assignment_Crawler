/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nhi.daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import nhi.data.MyConnection;
import nhi.dto.CurrencyDTO;
import nhi.dto.TypeOfCurrencyDTO;

/**
 *
 * @author admin
 */
public class TypeOfCurrencyDAO {

    private Connection conn;
    private PreparedStatement stm;
    private ResultSet rs;
    private float com;

    public TypeOfCurrencyDAO() {
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

    public ArrayList<TypeOfCurrencyDTO> getCountries(ArrayList<TypeOfCurrencyDTO> currencyCodes) {
        ArrayList<TypeOfCurrencyDTO> countries = new ArrayList<>();
        for (TypeOfCurrencyDTO currencyCode : currencyCodes) {
            try {
                String sql = "Select name from TypeOfCurrency where currencyCode = ? ORDER BY name ASC";
                conn = MyConnection.getConnection();
                stm = conn.prepareStatement(sql);

                stm.setString(1, currencyCode.getCode());

                rs = stm.executeQuery();
                while (rs.next()) {
                    TypeOfCurrencyDTO dto = new TypeOfCurrencyDTO();
                    dto.setCode(currencyCode.getCode());
                    dto.setName(rs.getString("name"));
                    countries.add(dto);
                }

            } catch (Exception e) {
                Logger.getLogger(CurrencyDAO.class.getName()).log(Level.SEVERE, null, e);
            }
        }
        return countries;
    }
}
