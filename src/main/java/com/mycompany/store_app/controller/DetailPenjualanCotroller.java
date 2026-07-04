/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.store_app.controller;


import com.mycompany.store_app.model.DAO.Detail_penjualanDAO;
import com.mycompany.store_app.model.entity.Detail_penjualan;
import java.util.List;


/**
 *
 * @author karis
 */
public class DetailPenjualanCotroller {
       
    private final Detail_penjualanDAO detailPenjualanDAO;
    
    // Constructor untuk inisialisasi objek DAO
    public DetailPenjualanCotroller() {
        this.detailPenjualanDAO = new Detail_penjualanDAO();
    }
    
    public void insert(Detail_penjualan dp) {
        detailPenjualanDAO.insert(dp);
    }
    
    public void update(Detail_penjualan dp) {
        detailPenjualanDAO.update(dp);
    }
    
    public void delate(int id) {
        detailPenjualanDAO.delete(id);
    }
    
    public List<Detail_penjualan> ambilSemuaDetail() {
        return detailPenjualanDAO.readAll();
    }
    
    public List<Detail_penjualan> ambilDetailByNota(int idNota) {
        return detailPenjualanDAO.getDataBy_ID_nota(idNota);
    }
    
    public List<Detail_penjualan> ambilDetailByBarang(int idBarang) {
        return detailPenjualanDAO.getDataBy_ID_barang(idBarang);
    }
    
    
    
    
}
