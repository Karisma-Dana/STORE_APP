/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.store_app.model.entity;

/**
 *
 * @author karis
 */
public class User {
    private String nama;
    private String password;
    private String email;
    
    
    public User(){
        
    }
    
    public User(String nama, String password){
        this.nama = nama;
        this.password = password;
    }
    
    public User(String nama, String password, String email){
        this.nama = nama;
        this.password = password;
        this.email = email;
    }

    public String getNama() {
        return nama;
    }

    public String getPassword() {
        return password;
    }
    public String getEmail(){
        return email;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    public void setEmail(String email){
        this.email = email;
    }
    
    
  
    
}
