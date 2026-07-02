/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.store_app.model.entity;
import java.time.LocalDateTime;

/**
 *
 * @author karis
 */
public class Penjualan {
    private int id_nota;
    private double total;
    private LocalDateTime tanggal;
    
    public Penjualan(){
        
    }
    
    public Penjualan(double total){
        this.total = total;
    }
    
    public Penjualan(int id_nota, double total, LocalDateTime tanggal){
        this.id_nota = id_nota;
        this.total = total;
        this.tanggal = tanggal;
    }

    public int getId_nota() {
        return id_nota;
    }

    public double getTotal() {
        return total;
    }

    public LocalDateTime getTanggal() {
        return tanggal;
    }

    public void setId_nota(int id_nota) {
        this.id_nota = id_nota;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public void setTanggal(LocalDateTime tanggal) {
        this.tanggal = tanggal;
    }
    
    
    
    
  
    
}
