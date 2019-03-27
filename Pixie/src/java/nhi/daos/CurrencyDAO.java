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
import nhi.dto.CurrencyDTO;
import nhi.dto.CurrencyDTOList;
import nhi.dto.CurrencyRateDTO;
import nhi.dto.CurrencyRateDTOList;
import nhi.dto.TypeOfCurrencyDTO;
import nhi.utils.NhiUtils;

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
            //System.out.println(" " + ex);
            Logger.getLogger(CurrencyDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public boolean insertCurrency(CurrencyDTO dto) {
        boolean check = false;
        try {
            String sql = "insert into Currency (currencyCode,buying,"
                    + "purchaseByTransfer,selling,date) values (?,?,?,?,?)";
            conn = MyConnection.getConnection();
            stm = conn.prepareStatement(sql);
            stm.setString(1, dto.getCurrencyCode());
            stm.setFloat(2, dto.getBuying());
            stm.setFloat(3, dto.getPurchaseByTransfer());
            stm.setFloat(4, dto.getSale());
            stm.setString(5, dto.getDate());
            check = stm.executeUpdate() > 0;
        } catch (SQLException ex) {
            if (!ex.getMessage().contains("duplicate")) {
                Logger.getLogger(CurrencyDAO.class.getName()).log(Level.SEVERE, null, ex);
            }

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
            //System.out.println(" " + ex);
             Logger.getLogger(CurrencyDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            closeConnection();
        }
        return check;
    }

    public ArrayList<CurrencyDTO> getRateCurrency(String code) {
        ArrayList<CurrencyDTO> comparables = null;
        ArrayList<CurrencyDTO> comparablesTmp = null;
        float temp = 0;
        float compaPresent = 0;
        float result = 0;
        boolean isFind = false;
        String percent = null;
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
                    percent = NhiUtils.countPercentRate(result);
                    dto.setBuyingRate((int) result);
                    comparables.add(dto);
                    temp = compaPresent;
                }
                //CurrencyDTO dtoTemp = new CurrencyDTO();
                //dto.setCurrencyCode(rs.getString("currencyCode"));

            }
        } catch (SQLException ex) {
            Logger.getLogger(CurrencyDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            closeConnection();
        }

        return comparables;
    }

    public float getCurrency(String id, String date) {
        float currency = 0;
        try {
            String sql = "Select buying from Currency where currencyCode=? and date=?";
            conn = MyConnection.getConnection();
            stm = conn.prepareStatement(sql);
            stm.setString(1, id);
            stm.setString(2, date);
            rs = stm.executeQuery();
            if (rs.next()) {
                currency = rs.getFloat("buying");
            }
        } catch (SQLException ex) {
            Logger.getLogger(CurrencyDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            closeConnection();
        }
        return currency;

    }

    public float getHighestRate(String code) {
        ArrayList<CurrencyDTO> comparables = null;
        float highestRate = 0;
        float temp = 0;
        float compaPresent = 0;
        float result = 0;
        boolean isFind = false;
        try {
            String sql = "Select TOP 30 buying from Currency where currencyCode=?";
            conn = MyConnection.getConnection();
            stm = conn.prepareStatement(sql);
            stm.setString(1, code);
            rs = stm.executeQuery();
            comparables = new ArrayList<>();

            while (rs.next()) {
                CurrencyDTO dto = new CurrencyDTO();
                // get current rate - past rate
                if (!isFind) {
                    temp = rs.getFloat("buying");
                    isFind = true;
                } else {
                    compaPresent = rs.getFloat("buying");
                    result = compaPresent - temp;

                    if (result > highestRate) {
                        highestRate = result;
                    }

                    temp = compaPresent;
                }
                //CurrencyDTO dtoTemp = new CurrencyDTO();
                //dto.setCurrencyCode(rs.getString("currencyCode"));

            }
        } catch (SQLException ex) {
            Logger.getLogger(CurrencyDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            closeConnection();
        }

        return highestRate;
    }

//     public ArrayList<CurrencyDTO> getCountries(String date){
//         ArrayList<CurrencyDTO> countries = new ArrayList<>();
//         try {
//             String sql = "Select currencyCode, name from Currency where date = ? ORDER BY name ASC";
//             conn = MyConnection.getConnection();
//             stm = conn.prepareStatement(sql);
//             stm.setString(1, date);
//             rs = stm.executeQuery();
//             while(rs.next()){
//                CurrencyDTO dto = new CurrencyDTO();
//                dto.setCurrencyCode(rs.getString("currencyCode"));
//                dto.setName(rs.getString("name"));
//                countries.add(dto);
//             }
//             
//         } catch (Exception e) {
//             e.printStackTrace();
//         }
//         return countries;
//     }
    //getCurrency  return CurrencyDTOList
    public CurrencyDTOList getAllRateCurrency() {
        CurrencyDTOList list = new CurrencyDTOList();
        String currencyCode = null;
        String date = null;
        float transfer = 0;
        float selling = 0;
        float temp = 0;
        float compaPresent = 0;
        float result = 0;
        boolean isFind = false;
        try {
            String sql = "Select * from Currency";
            conn = MyConnection.getConnection();
            stm = conn.prepareStatement(sql);

            rs = stm.executeQuery();
            //Currency2DTO dto = new Currency2DTO();
            while (rs.next()) {

                // get current rate - past rate
                if (!isFind) {
                    temp = rs.getFloat("buying");
                    isFind = true;
                } else {
                    currencyCode = rs.getString("currencyCode");
                    transfer = rs.getFloat("purchaseByTransfer");
                    selling = rs.getFloat("selling");
                    date = rs.getString("date");
                    compaPresent = rs.getFloat("buying");

                    result = compaPresent - temp;

                    CurrencyDTO dto = new CurrencyDTO(currencyCode.trim(), compaPresent, transfer, selling, (int) result, date.trim());
                    //Currency2DTO dto = new Currency2DTO(currencyCode, result);
                    list.getCurrencies().add(dto);
                    temp = compaPresent;
                }
                //CurrencyDTO dtoTemp = new CurrencyDTO();
                //dto.setCurrencyCode(rs.getString("currencyCode"));

            }
        } catch (SQLException ex) {
            Logger.getLogger(CurrencyDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            closeConnection();
        }

        return list;
    }

    public CurrencyDTOList getCurrencyRateByCode(String code) {
        CurrencyDTOList list = new CurrencyDTOList();
        String currencyCode = null;
        String date = null;
        float transfer = 0;
        float selling = 0;
        float temp = 0;
        float compaPresent = 0;
        float result = 0;
        boolean isFind = false;
        try {
            String sql = "Select * from Currency where currencyCode = ?";
            conn = MyConnection.getConnection();
            stm = conn.prepareStatement(sql);
            stm.setString(1, code);
            rs = stm.executeQuery();
            //Currency2DTO dto = new Currency2DTO();
            while (rs.next()) {

                // get current rate - past rate
                if (!isFind) {
                    temp = rs.getFloat("buying");
                    isFind = true;
                } else {
                    currencyCode = rs.getString("currencyCode");
                    transfer = rs.getFloat("purchaseByTransfer");
                    selling = rs.getFloat("selling");
                    date = rs.getString("date");
                    compaPresent = rs.getFloat("buying");

                    result = compaPresent - temp;

                    CurrencyDTO dto = new CurrencyDTO(currencyCode.trim(), compaPresent, transfer, selling, (int) result, date.trim());
                    //Currency2DTO dto = new Currency2DTO(currencyCode, result);
                    list.getCurrencies().add(dto);
                    temp = compaPresent;
                }
                //CurrencyDTO dtoTemp = new CurrencyDTO();
                //dto.setCurrencyCode(rs.getString("currencyCode"));

            }
        } catch (SQLException ex) {
            Logger.getLogger(CurrencyDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            closeConnection();
        }

        return list;
    }

    public CurrencyRateDTOList getCurrencyRateByDateList(ArrayList<String> listDate) {
        //List<CurrencyRate30DTOList> list = new ArrayList<CurrencyRate30DTOList>();
        //List<CurrencyRate30DTOList> listTmp = new ArrayList<CurrencyRate30DTOList>();
        CurrencyRateDTOList list = new CurrencyRateDTOList();
        String currencyCode = null;
        String name = null;
        String date = null;
        float buyingRate = 0;
        float transferRate = 0;
        float sellingRate = 0;
        boolean checkEx = false;
        String firstDate = "";
        String secondDate = "";
        int count = 1;
        for (int i = 0; i < listDate.size(); i++) {

            try {
                String sql = "EXEC CurrencyTmp ?,?";
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
                    currencyCode = rs.getString("currencyCode");
                    name = rs.getString("currencyName");
                    buyingRate = rs.getFloat("buyingRate");
                    transferRate = rs.getFloat("transferRate");
                    sellingRate = rs.getFloat("sellingRate");
                    date = rs.getString("currDate");

                    CurrencyRateDTO dto = new CurrencyRateDTO(currencyCode.trim(), name.trim(), (int) buyingRate, (int) transferRate, (int) sellingRate, date);
                    list.getCurrencies().add(dto);
                }
            } catch (SQLException ex) {
                Logger.getLogger(CurrencyDAO.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                closeConnection();
            }
          

        }

        return list;
    }

     public CurrencyRateDTOList getCurrencyRateByDateListAndID(ArrayList<String> listDate, String id) {
        //List<CurrencyRate30DTOList> list = new ArrayList<CurrencyRate30DTOList>();
        //List<CurrencyRate30DTOList> listTmp = new ArrayList<CurrencyRate30DTOList>();
        CurrencyRateDTOList list = new CurrencyRateDTOList();
        String currencyCode = null;
        String name = null;
        String date = null;
        float buyingRate = 0;
        float transferRate = 0;
        float sellingRate = 0;
        boolean checkEx = false;
        String firstDate = "";
        String secondDate = "";
        int count = 1;
        for (int i = 0; i < listDate.size(); i++) {

            try {
                String sql = "EXEC CurrencyStoreByID ?,?,?";
                conn = MyConnection.getConnection();
                stm = conn.prepareStatement(sql);
                stm.setString(1, listDate.get(i));
                int nextDate = i + 1;
                if (nextDate == listDate.size()){
                    break;
                }
                stm.setString(2, listDate.get(nextDate));
                stm.setString(3, id);
                rs = stm.executeQuery();
                
                //Currency2DTO dto = new Currency2DTO();
                while (rs.next()) {
                    currencyCode = rs.getString("currencyCode");
                    name = rs.getString("currencyName");
                    buyingRate = rs.getFloat("buyingRate");
                    transferRate = rs.getFloat("transferRate");
                    sellingRate = rs.getFloat("sellingRate");
                    date = rs.getString("currDate");

                    CurrencyRateDTO dto = new CurrencyRateDTO(currencyCode.trim(), name.trim(), (int) buyingRate, (int) transferRate, (int) sellingRate, date);
                    list.getCurrencies().add(dto);
                }
            } catch (SQLException ex) {
                Logger.getLogger(CurrencyDAO.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                closeConnection();
            }
          

        }

        return list;
    }
    
    
    public CurrencyRateDTOList getCurrencyRateByDate(String currDate, String prevDate) {
        CurrencyRateDTOList list = new CurrencyRateDTOList();
        String currencyCode = null;
        String name = null;
        String date = null;
        float buyingRate = 0;
        float transferRate = 0;
        float sellingRate = 0;
        try {
            String sql = "EXEC CurrencyInADate ?,?";
            conn = MyConnection.getConnection();
            stm = conn.prepareStatement(sql);
            stm.setString(1, currDate);
            stm.setString(2, prevDate);
            rs = stm.executeQuery();
            //Currency2DTO dto = new Currency2DTO();
            while (rs.next()) {
                currencyCode = rs.getString("currencyCode");
                name = rs.getString("name");
                buyingRate = rs.getFloat("buyingRate");
                transferRate = rs.getFloat("transferRate");
                sellingRate = rs.getFloat("sellingRate");
                date = rs.getString("currDate");

                CurrencyRateDTO dto = new CurrencyRateDTO(currencyCode.trim(), name.trim(), (int) buyingRate, (int) transferRate, (int) sellingRate, date);
                list.getCurrencies().add(dto);

            }
        } catch (SQLException ex) {
            Logger.getLogger(CurrencyDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            closeConnection();
        }

        return list;
    }

    public CurrencyRateDTOList getCurrencyMin(String id) {
        CurrencyRateDTOList list = new CurrencyRateDTOList();
        String currencyCode = null;
        String name = null;
        String date = null;
        float buyingRate = 0;
        float transferRate = 0;
        float sellingRate = 0;
        try {
            String sql = "EXEC CurrencyForTop ?";
            conn = MyConnection.getConnection();
            stm = conn.prepareStatement(sql);
            stm.setString(1, id);

            rs = stm.executeQuery();
            while (rs.next()) {
                currencyCode = rs.getString("currencyCode");
                name = rs.getString("name");
                buyingRate = rs.getFloat("buying");
                transferRate = rs.getFloat("purchaseByTransfer");
                sellingRate = rs.getFloat("selling");
                date = rs.getString("date");

                CurrencyRateDTO dto = new CurrencyRateDTO(currencyCode.trim(), name.trim(), (int) buyingRate, (int) transferRate, (int) sellingRate, date);
                list.getCurrencies().add(dto);
            }
        } catch (SQLException ex) {
            Logger.getLogger(CurrencyDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            closeConnection();
        }
        return list;

    }
     public ArrayList<TypeOfCurrencyDTO> getCountries(String date) {
        ArrayList<TypeOfCurrencyDTO> countries = new ArrayList<>();
        float temp = 0;
        try {
            String sql = "Select currencyCode, buying from Currency where date = ?";
            conn = MyConnection.getConnection();
            stm = conn.prepareStatement(sql);
            stm.setString(1, date);
            rs = stm.executeQuery();
            while (rs.next()) {
                TypeOfCurrencyDTO dto = new TypeOfCurrencyDTO();
                temp = rs.getFloat("buying");
                if (temp > 0){
                dto.setCode(rs.getString("currencyCode"));
                }
                countries.add(dto);
            }

        } catch (Exception e) {
            Logger.getLogger(CurrencyDAO.class.getName()).log(Level.SEVERE, null, e);
        }
        return countries;
    }
    

//      public CurrencyRateDTOList getAverageRate(String code) {
//         CurrencyRateDTOList list = new CurrencyRateDTOList();
//        float averageRate = 0;
//        float sum = 0;
//        float temp = 0;
//        float compaPresent = 0;
//        float result = 0;
//        boolean isFind = false;
//        try {
//            String sql = "Select TOP 30 buying, purchaseByTransfer, selling from Currency where currencyCode = ?";
//            conn = MyConnection.getConnection();
//            stm = conn.prepareStatement(sql);
//            stm.setString(1, code);
//            rs = stm.executeQuery();
//           
//            while (rs.next()) {
//               
//                // get current rate - past rate
//                if (!isFind) {
//                    temp = rs.getFloat("buying");
//                    isFind = true;
//                } else {
//                    compaPresent = rs.getFloat("buying");
//                    result = compaPresent - temp;
//
//                    sum += result;
//
//                    temp = compaPresent;
//                }
//                //CurrencyDTO dtoTemp = new CurrencyDTO();
//                //dto.setCurrencyCode(rs.getString("currencyCode"));
//
//            }
//            averageRate = sum / 30;
//        } catch (Exception e) {
//            e.printStackTrace();
//        }finally {
//            closeConnection();
//        }
//
//        return averageRate;
//    }
}
