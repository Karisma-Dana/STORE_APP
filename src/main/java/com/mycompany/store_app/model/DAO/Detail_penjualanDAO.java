/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.store_app.model.DAO;


import com.mycompany.store_app.config.Koneksi;
import com.mycompany.store_app.model.entity.Detail_penjualan;
import com.mycompany.store_app.model.DAO.PenjualanDAO;
import com.mycompany.store_app.model.DAO.BarangDAO;
import com.mycompany.store_app.model.entity.Penjualan;
import com.mycompany.store_app.model.entity.Barang;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author karis
 */
public class Detail_penjualanDAO {
    
    private final PenjualanDAO penjualanDAO = new PenjualanDAO();
    private final BarangDAO barangDAO = new BarangDAO();
    
    public void insert(Detail_penjualan dp){
        
        String querySQL = "INSERT INTO detail_penjualan (id_nota, id_barang, jumlah, subtotal) VALUES (?, ?, ?, ?)";
        try(Connection conn = Koneksi.getKoneksi();
            PreparedStatement ps = conn.prepareStatement(querySQL)){
            
            
            ps.setInt(1, dp.getPenjualan().getId_nota()) ;
            ps.setInt(2, dp.getBarang().getId());
            ps.setInt(3, dp.getJumlah());
            ps.setDouble(4, dp.getSubtotal());
           
            
            ps.executeUpdate();
            
            System.out.println("Data detail_penjualan berhasil disimpan icikbos");
                
            
        }catch(SQLException e){
            System.out.println("Gagal insert data : " + e.getMessage());
        }
    }
    
    
    public void update(Detail_penjualan dp) {
        String querySQL = "UPDATE detail_penjualan SET jumlah = ?, subtotal = ? WHERE id = ?";
        
        try (Connection conn = Koneksi.getKoneksi();
             PreparedStatement ps = conn.prepareStatement(querySQL)) {
            
            ps.setInt(1, dp.getJumlah());
            ps.setDouble(2, dp.getSubtotal());
            ps.setInt(3, dp.getId()); 
            
            ps.executeUpdate();
            System.out.println("Detail item berhasil diupdate, Boss!");
            
        } catch (SQLException e) {
            System.out.println("Gagal update detail penjualan: " + e.getMessage());
        }
    }
    
    
    public void delete(int id) {
        String querySQL = "DELETE FROM detail_penjualan WHERE id = ?";
        
        try (Connection conn = Koneksi.getKoneksi();
             PreparedStatement ps = conn.prepareStatement(querySQL)) {
            
            ps.setInt(1, id);
            ps.executeUpdate();
            System.out.println("Item berhasil dihapus dari nota, Boss!");
            
        } catch (SQLException e) {
            System.out.println("Gagal delete detail penjualan: " + e.getMessage());
        }
    }
    
    public List<Detail_penjualan> readAll(){
        List<Detail_penjualan> listDetail = new ArrayList<>();
        
        String querySQL = "SELECT * FROM detail_penjualan";
        
        try (Connection conn = Koneksi.getKoneksi();
             PreparedStatement ps = conn.prepareStatement(querySQL);
             ResultSet rs = ps.executeQuery()) {
            
            while(rs.next()){
                
                int id = rs.getInt("id");
                int id_nota = rs.getInt("id_nota");
                int id_barang = rs.getInt("id_barang");
                int jumlah = rs.getInt("jumlah");
                double subtotal = rs.getDouble("subtotal");
                
                Penjualan penjualan = penjualanDAO.getById(id_nota);
                Barang barang = barangDAO.getById(id_barang);
                
                Detail_penjualan detail = new Detail_penjualan(id, penjualan, barang, jumlah, subtotal);
                listDetail.add(detail);
            }
            
            System.out.println("berhasil : semua data detail penjualan berhasil dibaca");
        }catch(SQLException e){
            System.out.println("Gagal membaca detail penjualan : " + e.getMessage());
        }
        return listDetail;
    }
    
    public List<Detail_penjualan> getDataBy_ID_nota(int idNota_dicari){
        List<Detail_penjualan> listDetail = new ArrayList<>();
        
        String querySQL = "SELECT * FROM detail_penjualan WHERE id_nota = ?";
        
        try (Connection conn = Koneksi.getKoneksi();
             PreparedStatement ps = conn.prepareStatement(querySQL);  
             ){
            
            ps.setInt(1, idNota_dicari);

            try (ResultSet rs = ps.executeQuery()){
                while(rs.next()){
                    
                    int id = rs.getInt("id");
                    int id_nota = rs.getInt("id_nota");
                    int id_barang = rs.getInt("id_barang");
                    int jumlah = rs.getInt("jumlah");
                    double subtotal = rs.getDouble("subtotal");

                    Penjualan penjualan = penjualanDAO.getById(id_nota);
                    Barang barang = barangDAO.getById(id_barang);

                    Detail_penjualan detail = new Detail_penjualan(id, penjualan, barang, jumlah, subtotal);
                    listDetail.add(detail);
                }
            }
            
            System.out.println("berhasil : semua data dengna id_nota : " + idNota_dicari + "berhasil di baca");

        }catch(SQLException e){
            System.out.println("Gagal membaca detail penjualan : " + e.getMessage());
        }
        
        
        
        
        
        return listDetail;
    }
    
    public List<Detail_penjualan> getDataBy_ID_barang(int idBarang_dicari){
        List<Detail_penjualan> listDetail = new ArrayList<>();
        
        String querySQL = "SELECT * FROM detail_penjualan WHERE id_barang = ?";
        
        try (Connection conn = Koneksi.getKoneksi();
             PreparedStatement ps = conn.prepareStatement(querySQL);  
             ){
            
            ps.setInt(1, idBarang_dicari);

            try (ResultSet rs = ps.executeQuery()){
                while(rs.next()){
                    
                    int id = rs.getInt("id");
                    int id_nota = rs.getInt("id_nota");
                    int id_barang = rs.getInt("id_barang");
                    int jumlah = rs.getInt("jumlah");
                    double subtotal = rs.getDouble("subtotal");

                    Penjualan penjualan = penjualanDAO.getById(id_nota);
                    Barang barang = barangDAO.getById(id_barang);

                    Detail_penjualan detail = new Detail_penjualan(id, penjualan, barang, jumlah, subtotal);
                    listDetail.add(detail);
                }
            }
            
            System.out.println("berhasil : semua data dengna id_barang : " + idBarang_dicari + "berhasil di baca");

        }catch(SQLException e){
            System.out.println("Gagal membaca detail penjualan : " + e.getMessage());
        }
       
        return listDetail;
    }
    
    
    
    

    
    
    
    
    



    
    
}
