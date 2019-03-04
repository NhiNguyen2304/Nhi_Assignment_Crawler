/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nhi.data;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;

/**
 *
 * @author admin
 */
public class MyConnection implements Serializable{
   
    public static Connection getConnection(){
       
         Connection con = null;
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            con = DriverManager.getConnection("jdbc:sqlserver://localhost:1433;databaseName=NhiDB", "sa", "123qwe");   
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        return con;
    }
       
}
