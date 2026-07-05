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
import com.mycompany.store_app.model.entity.Voucher;


import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author karis
 */
public class VoucherDAO {
    
    public void insert(Voucher vcr){
        
        String querySQL = "INSERT INTO voucher (kode_voucher, jenis_voucher, diskon, stok) VALUES (?, ?, ?, ?)";
        try(Connection conn = Koneksi.getKoneksi();
            PreparedStatement ps = conn.prepareStatement(querySQL)){
            
            
                ps.setString(1, vcr.getKode_voucher());
                ps.setString(2, vcr.getJenis_voucher());
                ps.setDouble(3, vcr.getDiskon()); 
                ps.setInt(4, vcr.getStok());       
                
           
            
            ps.executeUpdate();
            
            System.out.println("Data voucher berhasil disimpan icikbos");
                
            
        }catch(SQLException e){
            System.out.println("Gagal insert data : " + e.getMessage());
        }
    }
    
    
    
//    update full;
    public void update(Voucher vcr) {
    String querySQL = "UPDATE voucher SET kode_voucher = ?, jenis_voucher = ?, diskon = ?, stok = ? WHERE id = ?";
    
    try (Connection conn = Koneksi.getKoneksi();
         PreparedStatement ps = conn.prepareStatement(querySQL)) {
        
        ps.setString(1, vcr.getKode_voucher());
        ps.setString(2, vcr.getJenis_voucher());
        ps.setDouble(3, vcr.getDiskon()); 
        ps.setInt(4, vcr.getStok());       
        ps.setInt(5, vcr.getId());         
        
        int rowsUpdated = ps.executeUpdate();
        if (rowsUpdated > 0) {
            System.out.println("Data voucher berhasil diupdate icikbos");
        } else {
            System.out.println("Data tidak ditemukan, gagal update.");
        }
        
    } catch (SQLException e) {
        System.out.println("Gagal update data: " + e.getMessage());
    }
}
    
    
    public void delete(int id) {
    String querySQL = "DELETE FROM voucher WHERE id = ?";
    
    try (Connection conn = Koneksi.getKoneksi();
         PreparedStatement ps = conn.prepareStatement(querySQL)) {
        
        ps.setInt(1, id);
        
        int rowsDeleted = ps.executeUpdate();
        if (rowsDeleted > 0) {
            System.out.println("Data voucher berhasil dihapus icikbos");
        } else {
            System.out.println("Data tidak ditemukan, gagal hapus.");
        }
        
    } catch (SQLException e) {
        System.out.println("Gagal hapus data: " + e.getMessage());
    }
}
    
    
    public List<Voucher> readAll() {
        List<Voucher> listVoucher = new ArrayList<>();
        String querySQL = "SELECT * FROM voucher";

        try (Connection conn = Koneksi.getKoneksi();
             PreparedStatement ps = conn.prepareStatement(querySQL);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Voucher vcr = new Voucher(); 
                vcr.setId(rs.getInt("id"));
                vcr.setKode_voucher(rs.getString("kode_voucher"));
                vcr.setJenis_voucher(rs.getString("jenis_voucher"));
                vcr.setDiskon(rs.getDouble("diskon"));
                vcr.setStok(rs.getInt("stok"));

                listVoucher.add(vcr);
            }

            System.out.println("berhasil : semua voucerberhasil di baca");

        } catch (SQLException e) {
            System.out.println("Gagal membaca data: " + e.getMessage());
        }

        return listVoucher;
    }
    
    
    public Voucher getById(int id){
        Voucher vcr = new Voucher();
        String querySQL = "SELECT * FROM voucher WHERE id = ?";
        
        try (Connection conn = Koneksi.getKoneksi();
         PreparedStatement ps = conn.prepareStatement(querySQL)) {
            
            ps.setInt(1, id);
            
            try(ResultSet rs = ps.executeQuery()){
                if (rs.next()) {
                    
                    vcr.setId(rs.getInt("id"));
                    vcr.setKode_voucher(rs.getString("kode_voucher"));
                    vcr.setJenis_voucher(rs.getString("jenis_voucher"));
                    vcr.setDiskon(rs.getDouble("diskon"));
                    vcr.setStok(rs.getInt("stok"));
                }
            }
            System.out.println("berhasil : voucher jenis LIMITED berhasil di baca");
        
        } catch (SQLException e) {
            System.out.println("Gagal membaca data: " + e.getMessage());
        }
   
        return vcr;
        
        
    }
    
public int count(String jenis, String search) {
    if (search == null) search = "";
    
    String querySQL;
    boolean filterByJenis = (jenis != null && !jenis.isEmpty() && !jenis.equals("ALL"));
    if (filterByJenis) {
        querySQL = "SELECT COUNT(*) FROM voucher WHERE jenis_voucher = ? AND kode_voucher LIKE ?";
    } else {
        querySQL = "SELECT COUNT(*) FROM voucher WHERE kode_voucher LIKE ?";
    }
    
    try (Connection conn = Koneksi.getKoneksi();
         PreparedStatement ps = conn.prepareStatement(querySQL)) {
        
        if (filterByJenis) {
            ps.setString(1, jenis);
            ps.setString(2, "%" + search + "%");
        } else {
            ps.setString(1, "%" + search + "%");
        }
        
        try (ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                int count = rs.getInt(1);
                System.out.println("Berhasil : voucher berhasil dihitung. Total: " + count);
                return count;
            }
        }

    } catch (SQLException e) {
        System.out.println("Gagal menghitung data voucher: " + e.getMessage());
    }

    return 0;
}
    
    public List<Voucher> getAllVoucher(int EntryPerPage, int Page, String jenis, String search) {
    List<Voucher> listVoucher = new ArrayList<>();
    
    if (search == null) search = "";
    String querySQL = "SELECT * FROM voucher WHERE kode_voucher LIKE ?";
    
    if (jenis != null && !jenis.isEmpty() && !jenis.equals("ALL")) {
        querySQL += " AND jenis_voucher = ?";
    }
    
    querySQL += " ORDER BY id LIMIT ? OFFSET ?";
    try (Connection conn = Koneksi.getKoneksi();
         PreparedStatement ps = conn.prepareStatement(querySQL)) {
        
        int paramIndex = 1;
        ps.setString(paramIndex++, "%" + search + "%");
        
        if (jenis != null && !jenis.isEmpty() && !jenis.equals("ALL")) {
            ps.setString(paramIndex++, jenis);
        }
        ps.setInt(paramIndex++, EntryPerPage);
        ps.setInt(paramIndex, (Page - 1) * EntryPerPage);
        
        try (ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Voucher vcr = new Voucher();
                vcr.setId(rs.getInt("id"));
                vcr.setKode_voucher(rs.getString("kode_voucher"));
                vcr.setJenis_voucher(rs.getString("jenis_voucher"));
                vcr.setDiskon(rs.getDouble("diskon"));
                vcr.setStok(rs.getInt("stok"));
                listVoucher.add(vcr);
            }
        }

    } catch (SQLException e) {
        System.out.println("Gagal membaca data voucher: " + e.getMessage());
    }

    return listVoucher;
}
    
    public List<Voucher> ambilVoucherPublic(){
        List<Voucher> listVoucher = new ArrayList<>();
        String querySQL = "SELECT * FROM voucher WHERE jenis_voucher = 'PUBLIC'";
        try (Connection conn = Koneksi.getKoneksi();
            PreparedStatement ps = conn.prepareStatement(querySQL)) {
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Voucher vcr = new Voucher(); 
                    vcr.setId(rs.getInt("id"));
                    vcr.setKode_voucher(rs.getString("kode_voucher"));
                    vcr.setJenis_voucher(rs.getString("jenis_voucher"));
                    vcr.setDiskon(rs.getDouble("diskon"));
                    vcr.setStok(rs.getInt("stok"));
                    listVoucher.add(vcr);
                }
                System.out.println("berhasil : voucher berhasil di baca");
            }

        } catch (SQLException e) {
            System.out.println("Gagal membaca data voucher: " + e.getMessage());
        }

        return listVoucher;
    }
    
    public Voucher checkVoucher_LIMITED(String kodeVoucher){
        Voucher voucher = null;
        String querySQL = "SELECT * FROM voucher WHERE kode_voucher = ? AND jenis_voucher = 'LIMITED'";
        
        try(Connection conn = Koneksi.getKoneksi();
         PreparedStatement ps = conn.prepareStatement(querySQL)){
            
            ps.setString(1, kodeVoucher);
            try(ResultSet rs = ps.executeQuery()){
                if (rs.next()){
                    voucher = new Voucher();
                    voucher.setId(rs.getInt("id"));
                    voucher.setKode_voucher(rs.getString("kode_voucher"));
                    voucher.setJenis_voucher(rs.getString("jenis_voucher"));
                    voucher.setDiskon(rs.getDouble("diskon"));
                    voucher.setStok(rs.getInt("stok"));
                }
            }
            
            System.out.println("berhasil : voucher jenis LIMITED terdapat di database");
            
        }catch(SQLException e){
            System.out.println("gagal check voucher LIMITED : " + e.getMessage());
        }
       
        
        return voucher;
    }
    
    public void updateStok(int id, int newStok){
        String querySQL = "UPDATE voucher SET stok = ? WHERE id = ?";
        
        try(Connection conn = Koneksi.getKoneksi();
         PreparedStatement ps = conn.prepareStatement(querySQL)){
            
            ps.setInt(1, newStok);
            ps.setInt(2, id);
            
            ps.executeUpdate();
            System.out.println("berhasil update stok voucher");
            
        }catch(SQLException e){
            System.out.println("gagal update stok voucher : " + e.getMessage());
        }
    }
    
    
    public List<Voucher> searchVoucher(String keyword){
        List<Voucher> listVoucher = new ArrayList<>();
        String querySQL = "SELECT * FROM voucher WHERE kode_voucher LIKE ? ";
        
        try (Connection conn = Koneksi.getKoneksi();
             PreparedStatement ps = conn.prepareStatement(querySQL);) {
                
                ps.setString(1, "%" + keyword + "%");
                
                try(ResultSet rs = ps.executeQuery()){
                    while (rs.next()) {
                        Voucher vcr = new Voucher(); 
                        vcr.setId(rs.getInt("id"));
                        vcr.setKode_voucher(rs.getString("kode_voucher"));
                        vcr.setJenis_voucher(rs.getString("jenis_voucher"));
                        vcr.setDiskon(rs.getDouble("diskon"));
                        vcr.setStok(rs.getInt("stok"));

                        listVoucher.add(vcr);
                    }

                System.out.println("berhasil : search voucher berhasil di baca,");
                }
                

            } catch (SQLException e) {
                System.out.println("Gagal membaca data: " + e.getMessage());
            }
        return listVoucher;
    }
    
    
    
    
    
    
    
}
