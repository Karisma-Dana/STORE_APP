/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.store_app.model.DAO;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.mycompany.store_app.config.Koneksi;
import com.mycompany.store_app.model.entity.Voucer;


import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author karis
 */
public class VoucerDAO {
    
    public void insert(Voucer vcr){
        
        String querySQL = "INSERT INTO voucer (kode_voucer, jenis_voucer, diskon, stok) VALUES (?, ?, ?, ?)";
        try(Connection conn = Koneksi.getKoneksi();
            PreparedStatement ps = conn.prepareStatement(querySQL)){
            
            
                ps.setString(1, vcr.getKode_voucer());
                ps.setString(2, vcr.getJenis_voucer());
                ps.setDouble(3, vcr.getDiskon()); 
                ps.setInt(4, vcr.getStok());       
                
           
            
            ps.executeUpdate();
            
            System.out.println("Data voucer berhasil disimpan icikbos");
                
            
        }catch(SQLException e){
            System.out.println("Gagal insert data : " + e.getMessage());
        }
    }
    
    
    
//    update full;
    public void update(Voucer vcr) {
    String querySQL = "UPDATE voucer SET kode_voucer = ?, jenis_voucer = ?, diskon = ?, stok = ? WHERE id = ?";
    
    try (Connection conn = Koneksi.getKoneksi();
         PreparedStatement ps = conn.prepareStatement(querySQL)) {
        
        ps.setString(1, vcr.getKode_voucer());
        ps.setString(2, vcr.getJenis_voucer());
        ps.setDouble(3, vcr.getDiskon()); 
        ps.setInt(4, vcr.getStok());       
        ps.setInt(5, vcr.getId());         
        
        int rowsUpdated = ps.executeUpdate();
        if (rowsUpdated > 0) {
            System.out.println("Data voucer berhasil diupdate icikbos");
        } else {
            System.out.println("Data tidak ditemukan, gagal update.");
        }
        
    } catch (SQLException e) {
        System.out.println("Gagal update data: " + e.getMessage());
    }
}
    
    
    public void delete(int id) {
    String querySQL = "DELETE FROM voucer WHERE id = ?";
    
    try (Connection conn = Koneksi.getKoneksi();
         PreparedStatement ps = conn.prepareStatement(querySQL)) {
        
        ps.setInt(1, id);
        
        int rowsDeleted = ps.executeUpdate();
        if (rowsDeleted > 0) {
            System.out.println("Data voucer berhasil dihapus icikbos");
        } else {
            System.out.println("Data tidak ditemukan, gagal hapus.");
        }
        
    } catch (SQLException e) {
        System.out.println("Gagal hapus data: " + e.getMessage());
    }
}
    
    
    public List<Voucer> readAll() {
        List<Voucer> listVoucer = new ArrayList<>();
        String querySQL = "SELECT * FROM voucer";

        try (Connection conn = Koneksi.getKoneksi();
             PreparedStatement ps = conn.prepareStatement(querySQL);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Voucer vcr = new Voucer(); 
                vcr.setId(rs.getInt("id"));
                vcr.setKode_voucer(rs.getString("kode_voucer"));
                vcr.setJenis_voucer(rs.getString("jenis_voucer"));
                vcr.setDiskon(rs.getDouble("diskon"));
                vcr.setStok(rs.getInt("stok"));

                listVoucer.add(vcr);
            }

            System.out.println("berhasil : semua voucerberhasil di baca");

        } catch (SQLException e) {
            System.out.println("Gagal membaca data: " + e.getMessage());
        }

        return listVoucer;
    }
    
    
    public Voucer getById(int id){
        Voucer vcr = new Voucer();
        String querySQL = "SELECT * FROM voucer WHERE id = ?";
        
        try (Connection conn = Koneksi.getKoneksi();
         PreparedStatement ps = conn.prepareStatement(querySQL)) {
            
            ps.setInt(1, id);
            
            try(ResultSet rs = ps.executeQuery()){
                if (rs.next()) {
                    
                    vcr.setId(rs.getInt("id"));
                    vcr.setKode_voucer(rs.getString("kode_voucer"));
                    vcr.setJenis_voucer(rs.getString("jenis_voucer"));
                    vcr.setDiskon(rs.getDouble("diskon"));
                    vcr.setStok(rs.getInt("stok"));
                }
            }
            System.out.println("berhasil : voucer jenis LIMITED berhasil di baca");
        
        } catch (SQLException e) {
            System.out.println("Gagal membaca data: " + e.getMessage());
        }
   
        return vcr;
        
        
    }
    
    
    public List<Voucer> getLIMITED_voucer(String filter) {
        List<Voucer> listVoucer = new ArrayList<>();
        String querySQL;

        // 1. Tentukan query-nya saja di dalam kondisional
        if (filter == null || filter.isEmpty()) {
            querySQL = "SELECT * FROM voucer WHERE jenis_voucer = 'LIMITED'";
        } else {
            querySQL = "SELECT * FROM voucer WHERE jenis_voucer = 'LIMITED' AND kode_voucer LIKE ?";
        }

        // 2. Eksekusi query dalam satu blok Try-Catch yang bersih
        try (Connection conn = Koneksi.getKoneksi();
             PreparedStatement ps = conn.prepareStatement(querySQL)) {

            // Jika filter diisi, pasang parameter wildcard-nya
            if (filter != null && !filter.isEmpty()) {
                ps.setString(1, "%" + filter + "%");
            }

            try (ResultSet rs = ps.executeQuery()) {
                // 3. Proses looping data cukup ditulis SATU KALI saja di sini
                while (rs.next()) {
                    Voucer vcr = new Voucer(); 
                    vcr.setId(rs.getInt("id"));
                    vcr.setKode_voucer(rs.getString("kode_voucer"));
                    vcr.setJenis_voucer(rs.getString("jenis_voucer"));
                    vcr.setDiskon(rs.getDouble("diskon"));
                    vcr.setStok(rs.getInt("stok"));

                    listVoucer.add(vcr);
                }
                System.out.println("berhasil : voucer jenis LIMITED berhasil di baca");
            }

        } catch (SQLException e) {
            System.out.println("Gagal membaca data voucer LIMITED: " + e.getMessage());
        }

        return listVoucer;
    }
    
    
    public List<Voucer> getPUBLIC_voucer(String filter){
        List<Voucer> listVoucer = new ArrayList<>();

        if (filter == null || filter.isEmpty()){
            String querySQL = "SELECT * FROM voucer WHERE jenis_voucer = 'PUBLIC'";

            try (Connection conn = Koneksi.getKoneksi();
                 PreparedStatement ps = conn.prepareStatement(querySQL);
                 ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {
                    Voucer vcr = new Voucer(); 
                    vcr.setId(rs.getInt("id"));
                    vcr.setKode_voucer(rs.getString("kode_voucer"));
                    vcr.setJenis_voucer(rs.getString("jenis_voucer"));
                    vcr.setDiskon(rs.getDouble("diskon"));
                    vcr.setStok(rs.getInt("stok"));

                    listVoucer.add(vcr);
                }

                System.out.println("berhasil : voucer jenis PUBLIC berhasil di baca");

            } catch (SQLException e) {
                System.out.println("Gagal membaca data: " + e.getMessage());
            }
        } else {
            String querySQL = "SELECT * FROM voucer WHERE jenis_voucer = 'PUBLIC' AND kode_voucer LIKE ? ";
            try (Connection conn = Koneksi.getKoneksi();
                 PreparedStatement ps = conn.prepareStatement(querySQL)) { // Perbaikan: Kurung kurawal try luar

                ps.setString(1, "%" + filter + "%");

                try (ResultSet rs = ps.executeQuery()){
                    while (rs.next()) {
                        Voucer vcr = new Voucer(); 
                        vcr.setId(rs.getInt("id"));
                        vcr.setKode_voucer(rs.getString("kode_voucer"));
                        vcr.setJenis_voucer(rs.getString("jenis_voucer"));
                        vcr.setDiskon(rs.getDouble("diskon"));
                        vcr.setStok(rs.getInt("stok"));

                        listVoucer.add(vcr);
                    }

                    System.out.println("berhasil : voucer jenis PUBLIC berhasil di baca");
                } // Penutup try ResultSet

            } catch (SQLException e) { // Sekarang Catch ini menangkap Try Connection dengan benar
                System.out.println("Gagal membaca data: " + e.getMessage());
            }
        } // Penutup blok else

        return listVoucer;
    }
    
    
    
//    pengecekan kode voucer private atau LIMITED;   
    public Voucer checkVoucer_LIMITED(String kodeVoucer){
        Voucer voucer = null;
        String querySQL = "SELECT * FROM voucer WHERE kode_voucer = ? AND jenis_voucer = 'LIMITED'";
        
        try(Connection conn = Koneksi.getKoneksi();
         PreparedStatement ps = conn.prepareStatement(querySQL)){
            
            ps.setString(1, kodeVoucer);
            try(ResultSet rs = ps.executeQuery()){
                if (rs.next()){
                    voucer = new Voucer();
                    voucer.setId(rs.getInt("id"));
                    voucer.setKode_voucer(rs.getString("kode_voucer"));
                    voucer.setJenis_voucer(rs.getString("jenis_voucer"));
                    voucer.setDiskon(rs.getDouble("diskon"));
                    voucer.setStok(rs.getInt("stok"));
                }
            }
            
            System.out.println("berhasil : voucer jenis LIMITED terdapat di database");
            
        }catch(SQLException e){
            System.out.println("gagal check voucer LIMITED : " + e.getMessage());
        }
       
        
        return voucer;
    }
    
    public void updateStok(int id, int newStok){
        String querySQL = "UPDATE voucer SET stok = ? WHERE id = ?";
        
        try(Connection conn = Koneksi.getKoneksi();
         PreparedStatement ps = conn.prepareStatement(querySQL)){
            
            ps.setInt(1, newStok);
            ps.setInt(2, id);
            
            ps.executeUpdate();
            System.out.println("berhasil update stok voucer");
            
        }catch(SQLException e){
            System.out.println("gagal update stok voucer : " + e.getMessage());
        }
    }
    
    
    public List<Voucer> searchVoucer(String keyword){
        List<Voucer> listVoucer = new ArrayList<>();
        String querySQL = "SELECT * FROM voucer WHERE kode_voucer LIKE ? ";
        
        try (Connection conn = Koneksi.getKoneksi();
             PreparedStatement ps = conn.prepareStatement(querySQL);) {
                
                ps.setString(1, "%" + keyword + "%");
                
                try(ResultSet rs = ps.executeQuery()){
                    while (rs.next()) {
                        Voucer vcr = new Voucer(); 
                        vcr.setId(rs.getInt("id"));
                        vcr.setKode_voucer(rs.getString("kode_voucer"));
                        vcr.setJenis_voucer(rs.getString("jenis_voucer"));
                        vcr.setDiskon(rs.getDouble("diskon"));
                        vcr.setStok(rs.getInt("stok"));

                        listVoucer.add(vcr);
                    }

                System.out.println("berhasil : search voucer berhasil di baca,");
                }
                

            } catch (SQLException e) {
                System.out.println("Gagal membaca data: " + e.getMessage());
            }
        return listVoucer;
    }
    
    
    
    
    
    
    
}
