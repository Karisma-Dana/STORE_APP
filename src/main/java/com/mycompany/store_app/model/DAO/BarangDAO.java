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
            
            System.out.println("berhasil : data barang id : " + id + "berhasil dihapus");

        } catch (SQLException e) {
            System.out.println("Gagal delete data barang: " + e.getMessage());
        }
    }
    
    public int countAvailable(double pricefilter, String namefilter){
        String querySQL = "SELECT COUNT(*) FROM barang WHERE harga < ? AND stok > 0 AND nama LIKE ?";
        if (pricefilter == 0){
            pricefilter = 999999999;
        };
        try (Connection conn = Koneksi.getKoneksi();
             PreparedStatement ps = conn.prepareStatement(querySQL)) {

            ps.setDouble(1, pricefilter);
            ps.setString(2, namefilter + "%");

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }

        } catch (SQLException e) {
            System.out.println("Gagal menghitung data barang: " + e.getMessage());
        }

        return 0;
    }
    
    public int count(double pricefilter, String namefilter){
        String querySQL = "SELECT COUNT(*) FROM barang WHERE harga < ? AND nama LIKE ?";
        if (pricefilter == 0){
            pricefilter = 999999999;
        };
        try (Connection conn = Koneksi.getKoneksi();
             PreparedStatement ps = conn.prepareStatement(querySQL)) {

            ps.setDouble(1, pricefilter);
            ps.setString(2, namefilter + "%");

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }

        } catch (SQLException e) {
            System.out.println("Gagal menghitung data barang: " + e.getMessage());
        }

        return 0;
    }
    
    public List<Barang> readAllAvailable(int EntryPerPage, int Page, String namefilter, double pricefilter) {
        List<Barang> listBarang = new ArrayList<>();
        String querySQL = "SELECT * FROM barang WHERE harga < ? AND stok > 0 AND nama LIKE ? ORDER BY id LIMIT ? OFFSET ?";
        if (pricefilter == 0){
            pricefilter = 999999999;
        };
        
        try (Connection conn = Koneksi.getKoneksi();
            PreparedStatement ps = conn.prepareStatement(querySQL)) {

            ps.setDouble(1, pricefilter);
            ps.setString(2, "%" + namefilter + "%");
            ps.setInt(3, EntryPerPage);
            ps.setInt(4, (Math.abs(Page - 1) * EntryPerPage));

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Barang barang = new Barang();
                    barang.setId(rs.getInt("id"));
                    barang.setNama(rs.getString("nama"));
                    barang.setHarga(rs.getDouble("harga"));
                    barang.setStok(rs.getInt("stok"));
                    listBarang.add(barang);
                }
            }

        } catch (SQLException e) {
            System.out.println("Gagal membaca data barang: " + e.getMessage());
        }

        return listBarang;
    }
    
    public List<Barang> readAll(int EntryPerPage, int Page, String namefilter, double pricefilter) {
        List<Barang> listBarang = new ArrayList<>();
        String querySQL = "SELECT * FROM barang WHERE harga < ? AND nama LIKE ? ORDER BY id LIMIT ? OFFSET ?";
        if (pricefilter == 0){
            pricefilter = 999999999;
        };
        try (Connection conn = Koneksi.getKoneksi();
            PreparedStatement ps = conn.prepareStatement(querySQL)) {

            ps.setDouble(1, pricefilter);
            ps.setString(2, "%" + namefilter + "%");
            ps.setInt(3, EntryPerPage);
            ps.setInt(4, (Math.abs(Page - 1) * EntryPerPage));

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Barang barang = new Barang();
                    barang.setId(rs.getInt("id"));
                    barang.setNama(rs.getString("nama"));
                    barang.setHarga(rs.getDouble("harga"));
                    barang.setStok(rs.getInt("stok"));
                    listBarang.add(barang);
                }
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
            
            System.out.println("berhasil : data barang dengan ID : " + id_barang + "berhasil didapat");
        }catch(SQLException e){
            System.out.println("Gagal get data : " + e.getMessage());
        }
        
        return barang;
        
    }
    
    public void updateStok(int id, int newStok){
        String querySQL = "UPDATE barang SET stok = ? WHERE id = ?";
        
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
    
    
    public List<Barang> search(String nama){
        List<Barang> listBarang = new ArrayList<>();
        String querySQL = "SELECT * FROM barang WHERE nama LIKE ?";
        
        try (Connection conn = Koneksi.getKoneksi();
                PreparedStatement ps = conn.prepareStatement(querySQL)){
            
            ps.setString(1, "%" + nama + "%");
            
            try(ResultSet rs = ps.executeQuery()){
                while(rs.next()){
                    Barang barang = new Barang();
                    barang.setId(rs.getInt("id"));
                    barang.setNama(rs.getString("nama"));
                    barang.setHarga(rs.getDouble("harga"));
                    barang.setStok(rs.getInt("stok"));
                    
                    listBarang.add(barang);
                }
            }
            
        }catch(SQLException e){
            System.out.println("Gagal mencari data dosen: " + e.getMessage());
        }
        
        return listBarang;
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
}
