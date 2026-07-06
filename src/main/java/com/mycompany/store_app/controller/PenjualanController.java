/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.store_app.controller;

import com.mycompany.store_app.model.DAO.PenjualanDAO;
import com.mycompany.store_app.model.entity.Penjualan;
import java.util.List;

/**
 *
 * @author karis
 */
public class PenjualanController {
    
    private final PenjualanDAO penjualanDAO;
    
    public PenjualanController(){
        this.penjualanDAO = new PenjualanDAO();
    }
    
    public int insert(Penjualan pj){
       return penjualanDAO.insert(pj);
    }
    
    public void update(Penjualan pj){
        penjualanDAO.update(pj);
    }
    
    public void delete(int id){
        penjualanDAO.delete(id);
    }
    
    public List<Penjualan> getAll(){
        return penjualanDAO.readAll();
    }
    
    public Penjualan getDatabyID(int id){
        return penjualanDAO.getById(id);
    }
    
}
