/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author sushantbhat
 */
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;


public class MySqlConnectExample {
    public static void main(String[] args) {
        Connection conn1 = null;
        Connection conn2 = null;
        Connection conn3 = null;
        Statement stmt = null;
        try{
            String url1 = "jdbc:mysql://localhost:3306/othello";
            String user = "root";
            String password = "mamatabhat";
            conn1 = DriverManager.getConnection(url1, user, password);
            if (conn1 != null) {
                System.out.println("Connected to the database test1");
            }
            stmt = conn1.createStatement();
            String sql = "insert into users values(0 , 'demo' , 32)";
            stmt.executeUpdate(sql);
        } catch (SQLException ex) {
            Logger.getLogger(MySqlConnectExample.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        


    }
}
