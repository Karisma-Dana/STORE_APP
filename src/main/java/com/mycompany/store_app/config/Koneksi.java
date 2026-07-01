/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.store_app.config;

/**
 *
 * @author karis
 */

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
public class Koneksi {
    
     public static Connection getKoneksi(){
        Connection conn = null;
        if (conn == null){
            try{
                String url = "jdbc:mysql://localhost:3306/store_app";
                String user = "root";
                String password = "";
                
                
                
                Class.forName("com.mysql.cj.jdbc.Driver");
                
                conn = DriverManager.getConnection(url, user, password);
                System.out.println("got connection");
                        
                
            }catch(ClassNotFoundException e){
                System.out.println("Driver tidak ditemukan: " + e.getMessage());
            }catch(SQLException e){
                System.out.println("Koneksi Gagal: " + e.getMessage());
            }
        }
        
        
        return conn;
    }
    
}
