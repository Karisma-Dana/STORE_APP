/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.store_app.model.entity;

import com.mycompany.store_app.model.entity.Barang;
import com.mycompany.store_app.model.entity.Penjualan;

/**
 *
 * @author karis
 */
public class Detail_penjualan {
    
    private int id;
    private int jumlah;
    private double subtotal;
    
    
//    untuk relation
    private Penjualan penjualan;
    private Barang barang;
    
    
    public Detail_penjualan(){
        
    }
    
//    insert object untuk fitur keranjang. setelah masuk ke kasir baru setting variable object penjualan nya. 
    public Detail_penjualan(Barang barang, int jumlah){
        this.barang = barang;
        this.jumlah = jumlah;
        this.subtotal = barang.getHarga() * jumlah;
    }
    
    
//    insert object untuk read database;
    public Detail_penjualan(int id, Penjualan penjualan, Barang barang, int jumlah, double subtotal){
        this.id = id;
        this.penjualan = penjualan;
        this.barang = barang;
        this.jumlah = jumlah;
        this.subtotal = subtotal;
    }
    
    public int getId() {
        return id;
    }

    public int getJumlah() {
        return jumlah;
    }

    public double getSubtotal() {
        return subtotal;
    }

    public Penjualan getPenjualan() {
        return penjualan;
    }

    public Barang getBarang() {
        return barang;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setJumlah(int jumlah) {
        this.jumlah = jumlah;
        if (this.barang != null) this.subtotal = this.barang.getHarga() * jumlah;
    }

    public void setPenjualan(Penjualan penjualan) {
        this.penjualan = penjualan;
    }

    public void setBarang(Barang barang) {
        this.barang = barang;
        if (this.barang != null) this.subtotal = this.barang.getHarga() * jumlah;
    }
    
    
    
    
}
