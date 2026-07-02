/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.store_app.model.DAO;


import com.mycompany.store_app.config.Koneksi;
import com.mycompany.store_app.model.entity.Penjualan;
import java.time.LocalDateTime;


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
public class PenjualanDAO {
    
    
//    return index dari data yang diinsert. untuk fitur insert detail penjualan
    public int insert(Penjualan penjualan){
        String querySQL = "INSERT INTO penjualan (total) VALUES (?)";
        int generated_ID = 0;
        try(Connection conn = Koneksi.getKoneksi();
            PreparedStatement ps = conn.prepareStatement(querySQL, Statement.RETURN_GENERATED_KEYS)){
            ps.setDouble(1, penjualan.getTotal()) ;
            ps.executeUpdate();
            
            try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    generated_ID = generatedKeys.getInt(1); // Menangkap angka dari MySQL
                }
            }
            
            System.out.println("Data penjualan berhasil disimpan icikbos");
                
        }catch(SQLException e){
            System.out.println("Gagal insert data : " + e.getMessage());
        }
        
        return generated_ID;
    }
      
    public void update(Penjualan penjualan) {
        String querySQL = "UPDATE penjualan SET total = ? WHERE id_nota = ?";
        try (Connection conn = Koneksi.getKoneksi();
             PreparedStatement ps = conn.prepareStatement(querySQL)) {

            ps.setDouble(1, penjualan.getTotal());
            ps.setInt(2, penjualan.getId_nota()); // Mengunci nota mana yang mau diupdate

            int rowsUpdated = ps.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Data penjualan berhasil diupdate, Boss!");
            } else {
                System.out.println("Nota dengan ID tersebut tidak ditemukan.");
            }

        } catch (SQLException e) {
            System.out.println("Gagal update data penjualan: " + e.getMessage());
        }
    }
    
    public void delete(int idNota) {
        String querySQL = "DELETE FROM penjualan WHERE id_nota = ?";
        try (Connection conn = Koneksi.getKoneksi();
             PreparedStatement ps = conn.prepareStatement(querySQL)) {

            ps.setInt(1, idNota);

            int rowsDeleted = ps.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("Data penjualan dan detailnya berhasil didelete, Boss!");
            } else {
                System.out.println("Nota dengan ID tersebut tidak ditemukan.");
            }
            
            System.out.println("berhasil : penjualan dengan id " + idNota + "berhasil di hapus");

        } catch (SQLException e) {
            System.out.println("Gagal delete data penjualan: " + e.getMessage());
        }
    }

    public List<Penjualan> readAll() {
        List<Penjualan> listPenjualan = new ArrayList<>();
        String querySQL = "SELECT * FROM penjualan";

        try (Connection conn = Koneksi.getKoneksi();
             PreparedStatement ps = conn.prepareStatement(querySQL);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                int id = rs.getInt("id_nota");
                double total = rs.getDouble("total");

                LocalDateTime tanggal = rs.getTimestamp("tanggal").toLocalDateTime();

               
                Penjualan penjualan = new Penjualan(id, total, tanggal);
                listPenjualan.add(penjualan);
            }
            
            System.out.println("berhasil : semua data penjualan berhasil di baca");

        } catch (SQLException e) {
            System.out.println("Gagal membaca laporan penjualan: " + e.getMessage());
        }

        return listPenjualan;
    }
    
    
    public Penjualan getById(int id_nota){
        Penjualan penjualan = null;
        String querySQL = "SELECT * FROM penjualan WHERE id_nota = ?";
        
        try (Connection conn = Koneksi.getKoneksi();
             PreparedStatement ps = conn.prepareStatement(querySQL)){
            
            ps.setInt(1, id_nota);
            
            try(ResultSet rs = ps.executeQuery()){
                if (rs.next()){
                    int id = rs.getInt("id_nota");
                    double total = rs.getDouble("total");
                    LocalDateTime tanggal = rs.getTimestamp("tanggal").toLocalDateTime();
                    penjualan = new Penjualan(id, total, tanggal);
                }
            }
            System.out.println("berhasil : data penjualan dengan id " + id_nota + "berhasil didapat");
        }catch(SQLException e){
            System.out.println("Gagal get data : " + e.getMessage());
        }
        
        return penjualan;
        
        
        
        
        
    }
    
    
    
    
    
    
    
    
    
    
    
    
}
