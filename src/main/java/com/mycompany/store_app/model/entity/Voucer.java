/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.store_app.model.entity;

/**
 *
 * @author karis
 */
public class Voucer {
    private int id;
    private String kode_voucer;
    private String jenis_voucer;
    private double diskon;
    private int stok;
    
    
    
    public Voucer(){
        
    }
    public Voucer(String kode_voucer, String jenis_voucer, double diskon, int stok){
        this.kode_voucer = kode_voucer;
        this.jenis_voucer = jenis_voucer;
        this.diskon = diskon;
        this.stok = stok;
    }
    
    public Voucer(int id, String kode_voucer, String jenis_voucer, double diskon, int stok){
        this.id = id;
        this.kode_voucer = kode_voucer;
        this.jenis_voucer = jenis_voucer;
        this.diskon = diskon;
        this.stok = stok;
    }
    
    
    

    public int getId() {
        return id;
    }

    public String getKode_voucer() {
        return kode_voucer;
    }

    public String getJenis_voucer() {
        return jenis_voucer;
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

    public void setKode_voucer(String kode_voucer) {
        this.kode_voucer = kode_voucer;
    }

    public void setJenis_voucer(String jenis_voucer) {
        this.jenis_voucer = jenis_voucer;
    }

    public void setDiskon(double diskon) {
        this.diskon = diskon;
    }

    public void setStok(int stok) {
        this.stok = stok;
    }
    
    
    
    
}
