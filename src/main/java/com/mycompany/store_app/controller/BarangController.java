/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.store_app.controller;
import com.mycompany.store_app.model.DAO.BarangDAO;
import com.mycompany.store_app.model.entity.Barang;
import java.util.List;

/**
 *
 * @author karis
 */
public class BarangController {
   private final BarangDAO barangDAO;
   
    public BarangController() {
        this.barangDAO = new BarangDAO();
    }
   
    public void insert(Barang barang) {
        barangDAO.insert(barang);
    }
   
    public void update(Barang barang) {
        barangDAO.update(barang);
    }
    public void delete(int id) {
        barangDAO.delete(id);
    }
    
    public int count(double pricefilter, String namabarang){
        return barangDAO.count(pricefilter, namabarang);
    }
    
    public List<Barang> ambilSemuaBarangAvailable(int EntryperPage, int Page, String namabarang, double pricefilter) {
        System.out.println(pricefilter);
        return barangDAO.readAllAvailable(EntryperPage, Page, namabarang, pricefilter);
    }
    
    public int countAvailable(double pricefilter, String namabarang){
        return barangDAO.countAvailable(pricefilter, namabarang);
    }
    
    public List<Barang> ambilSemuaBarang(int EntryperPage, int Page, String namabarang, double pricefilter) {
        return barangDAO.readAll(EntryperPage, Page, namabarang, pricefilter);
    }
    
    public Barang ambilBarangById(int id) {
        return barangDAO.getById(id);
    }
   
    public void perbaruiStok(int id, int stokBaru) {
        barangDAO.updateStok(id, stokBaru);
    }
   
    public List<Barang> searchByName(String nama) {
        return barangDAO.search(nama);
    }
   
   
   
   
   
   
   
   
   
   
    
    
    
}
