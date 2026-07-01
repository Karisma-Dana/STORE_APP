/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.store_app.model.entity;

/**
 *
 * @author karis
 */
public class Barang {
    private String nama;
    private double harga;
    private int stok;
    
    public Barang(){
        
    }
    
    public Barang( String nama, double harga, int stok){
        this.nama = nama;
        this.harga = harga;
        this.stok = stok;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public void setHarga(double harga) {
        this.harga = harga;
    }

    public void setStok(int stok) {
        this.stok = stok;
    }

    public String getNama() {
        return nama;
    }

    public double getHarga() {
        return harga;
    }

    public int getStok() {
        return stok;
    }
    
    
    
    
    
    
}
