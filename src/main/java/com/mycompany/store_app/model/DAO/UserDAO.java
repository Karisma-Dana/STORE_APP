/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.store_app.model.DAO;

import com.mycompany.store_app.config.Koneksi;
import com.mycompany.store_app.model.entity.User;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author karis
 */
public class UserDAO {
    
    
    
//    untuk register
    public void register(User user){
        
        String querySQL = "INSERT INTO users (email, username, password) VALUES (?, ?, SHA2(?, 256))";
        try(Connection conn = Koneksi.getKoneksi();
            PreparedStatement ps = conn.prepareStatement(querySQL)){
            
            
            ps.setString(1, user.getEmail()) ;
            ps.setString(2, user.getNama());
            ps.setString(3, user.getPassword());
                 
           
            
            ps.executeUpdate();
            
            System.out.println("Data user berhasil disimpan icikbos");
                
            
        }catch(SQLException e){
            System.out.println("Gagal insert data : " + e.getMessage());
        }
    }
    
    public void update(User user) {
        String querySQL = "UPDATE users SET username = ?, password = ? WHERE email = ?";
        try (Connection conn = Koneksi.getKoneksi();
             PreparedStatement ps = conn.prepareStatement(querySQL)) {

            ps.setString(1, user.getNama());
            ps.setString(2, user.getPassword()); 
            ps.setString(3, user.getEmail());

            int rowsUpdated = ps.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Password dan username berhasil diupdate, Boss!");
            } else {
                System.out.println("email dengan nama tersebut tidak ditemukan.");
            }

        } catch (SQLException e) {
            System.out.println("Gagal update data: " + e.getMessage());
        }
    }
    
    
    public void delete(String email) {
        String querySQL = "DELETE FROM users WHERE email = ?";
        try (Connection conn = Koneksi.getKoneksi();
             PreparedStatement ps = conn.prepareStatement(querySQL)) {

            ps.setString(1, email);

            int rowsDeleted = ps.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("Data user berhasil didelete, Boss!");
            } else {
                System.out.println("User dengan email tersebut tidak ditemukan.");
            }

        } catch (SQLException e) {
            System.out.println("Gagal delete data user: " + e.getMessage());
        }
    }
    
    
    public List<User> readAll() {
        List<User> listUser = new ArrayList<>();
        String querySQL = "SELECT * FROM users";

        try (Connection conn = Koneksi.getKoneksi();
             PreparedStatement ps = conn.prepareStatement(querySQL);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                User user = new User();
                user.setEmail(rs.getString("email"));
                user.setNama(rs.getString("username"));
                user.setPassword(rs.getString("password"));

                listUser.add(user);
            }
            
            System.out.println("berhasil : semua data dapat dibaca");
        } catch (SQLException e) {
            System.out.println("Gagal membaca data user: " + e.getMessage());
        }

        return listUser;
    }
    
    
//    pengecekan emai unik
    public boolean checkEmail_unik(String email){
        boolean answer = true;
        String querySQL = "SELECT COUNT(*) AS total FROM users WHERE email = ? ";
        try(Connection conn = Koneksi.getKoneksi();
            PreparedStatement ps = conn.prepareStatement(querySQL)){
            ps.setString(1, email);
            
            try(ResultSet rs = ps.executeQuery()){
                if (rs.next()){
                    int jumlah = rs.getInt("total");
                    if (jumlah > 0){
                        answer = false;
                    }
                }
            }
            
        }catch(SQLException e){
            System.out.println("gagal check email : " + e.getMessage());
        }
        
        return answer; 
    }
    
    
//    untuk bagian login
    public User login(String email, String password){
        User user = null;
        String querySQL = "SELECT * FROM users WHERE email = ? AND password = SHA2(?, 256)";
        
        try(Connection conn = Koneksi.getKoneksi();
             PreparedStatement ps = conn.prepareStatement(querySQL)){
            
            ps.setString(1, email);
            ps.setString(2, password);
            
            try(ResultSet rs = ps.executeQuery()){
                if (rs.next()){
                    user = new User();
                    user.setEmail(rs.getString("email"));
                    user.setNama(rs.getString("username"));
                    if (rs.getString("email").equals("pass123@gmail.com")){
                        user.setNama("admin");
                    }
                    user.setPassword(rs.getString("password"));
                }
            }
            System.out.println("berhasil : email dan password sesuai dengan database");
            
        }catch (SQLException e){
            System.out.println("gagal pengecekan login : " + e.getMessage());
        }
        
        return user;
    }
    
    
//
    
    
    
    
    
    
    
    
    
    
    
    
    
    
}
