/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.store_app.model.entity;

/**
 *
 * @author karis
 */

public class Voucher {
    private int id;
    private String kode_voucher;
    private String jenis_voucher;
    private double diskon;
    private int stok;
    
    
    
    public Voucher(){
        
    }
    
    public Voucher(String kode_voucer, String jenis_voucer, double diskon, int stok){
        this.kode_voucher = kode_voucer;
        this.jenis_voucher = jenis_voucer;
        this.diskon = diskon;
        this.stok = stok;
    }
    
    public Voucher(int id, String kode_voucer, String jenis_voucer, double diskon, int stok){
        this.id = id;
        this.kode_voucher = kode_voucer;
        this.jenis_voucher = jenis_voucer;
        this.diskon = diskon;
        this.stok = stok;
    }
    
    
    

    public int getId() {
        return id;
    }

    public String getKode_voucher() {
        return kode_voucher;
    }

    public String getJenis_voucher() {
        return jenis_voucher;
    }

    public double getDiskon() {
        return diskon;
    }

    public int getStok() {
        return stok;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setKode_voucher(String kode_voucer) {
        this.kode_voucher = kode_voucer;
    }

    public void setJenis_voucher(String jenis_voucer) {
        this.jenis_voucher = jenis_voucer;
    }

    public void setDiskon(double diskon) {
        this.diskon = diskon;
    }

    public void setStok(int stok) {
        this.stok = stok;
    }
    
    
    
    
}
