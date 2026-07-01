/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.store_app.model.entity;

/**
 *
 * @author karis
 */
public class Penjualan {
   
    private double total;
    
    public Penjualan(){
        
    }
    
    public Penjualan(double total){
        this.total = total;
    }
    
    public void setTotal(double total){
        this.total = total;
    }
    public double getTotal(){
        return total;
    }
  
    
}
