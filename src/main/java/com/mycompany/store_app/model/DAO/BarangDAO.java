/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.store_app.model.DAO;

import com.mycompany.store_app.config.Koneksi;
import com.mycompany.store_app.model.entity.Barang;

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
public class BarangDAO {
    
    
    public void insert(Barang barang){
        String querySQL = "INSERT INTO barang (nama, harga, stok) VALUES (?, ?, ?)";
        try(Connection conn = Koneksi.getKoneksi();
            PreparedStatement ps = conn.prepareStatement(querySQL)){
            
            
            ps.setString(1, barang.getNama()) ;
            ps.setDouble(2, barang.getHarga());
            ps.setInt(3, barang.getStok());
           
            
            ps.executeUpdate();
            
            System.out.println("Data barang berhasil disimpan icikbos");
                
            
        }catch(SQLException e){
            System.out.println("Gagal insert data : " + e.getMessage());
        }        
        
    }
    
    public void update(Barang barang) {
        String querySQL = "UPDATE barang SET nama = ?, harga = ?, stok = ? WHERE id = ?";
        try (Connection conn = Koneksi.getKoneksi();
             PreparedStatement ps = conn.prepareStatement(querySQL)) {

            ps.setString(1, barang.getNama());
            ps.setDouble(2, barang.getHarga());
            ps.setInt(3, barang.getStok());
            ps.setInt(4, barang.getId()); // Mengunci barang yang mau diedit berdasarkan ID uniknya

            int rowsUpdated = ps.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Data barang berhasil diupdate, Boss!");
            } else {
                System.out.println("Barang dengan ID tersebut tidak ditemukan.");
            }

        } catch (SQLException e) {
            System.out.println("Gagal update data barang: " + e.getMessage());
        }
    }
    
    
    
    public void delete(int id) {
        String querySQL = "DELETE FROM barang WHERE id = ?";
        try (Connection conn = Koneksi.getKoneksi();
             PreparedStatement ps = conn.prepareStatement(querySQL)) {

            ps.setInt(1, id); // Menargetkan ID barang yang ingin dihapus

            int rowsDeleted = ps.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("Data barang berhasil didelete, Boss!");
            } else {
                System.out.println("Barang dengan ID tersebut tidak ditemukan.");
            }

        } catch (SQLException e) {
            System.out.println("Gagal delete data barang: " + e.getMessage());
        }
    }
    
    

    public List<Barang> readAll() {
        List<Barang> listBarang = new ArrayList<>();
        String querySQL = "SELECT * FROM barang";

        try (Connection conn = Koneksi.getKoneksi();
             PreparedStatement ps = conn.prepareStatement(querySQL);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                
                Barang barang = new Barang();
                barang.setId(rs.getInt("id"));
                barang.setNama(rs.getString("nama"));
                barang.setHarga(rs.getDouble("harga"));
                barang.setStok(rs.getInt("stok"));

                listBarang.add(barang);
            }

        } catch (SQLException e) {
            System.out.println("Gagal membaca data barang: " + e.getMessage());
        }

        return listBarang;
    }
    
    
    public Barang getById(int id_barang){
        Barang barang = null;
        String querySQL = "SELECT * FROM barang WHERE id = ?";
        
        try (Connection conn = Koneksi.getKoneksi();
             PreparedStatement ps = conn.prepareStatement(querySQL)){
            
            ps.setInt(1, id_barang);
            
            try(ResultSet rs = ps.executeQuery()){
                if (rs.next()){
                    int id = rs.getInt("id");
                    String nama = rs.getString("nama");
                    double harga = rs.getDouble("harga");
                    int stok = rs.getInt("stok");
                    barang = new Barang(id, nama, harga, stok);
                }
            }
        }catch(SQLException e){
            System.out.println("Gagal get data : " + e.getMessage());
        }
        
        return barang;
        
    }
    
    public void updateStok(int id, int newStok){
        String querySQL = "UPDATE barang SET stok = ? WHERE = ?";
        
        try(Connection conn = Koneksi.getKoneksi();
             PreparedStatement ps = conn.prepareStatement(querySQL)){
            
            ps.setInt(1, newStok);
            ps.setInt(2, id);
            
            ps.executeUpdate();
            System.out.println("Stok barang ID " + id + "berhasil diperbaharui");
            
        }catch(SQLException e){
            System.out.println("gagal update stok : " + e.getMessage());
        }
        
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
}
