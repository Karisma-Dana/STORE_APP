/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.store_app.controller;

import com.mycompany.store_app.model.DAO.VoucerDAO;
import com.mycompany.store_app.model.entity.Voucer;
import java.util.List;

/**
 *
 * @author karis
 */
public class VoucherController {
    private final VoucerDAO voucerDAO;
    
    public VoucherController() {
        this.voucerDAO = new VoucerDAO();
    }
    public void insert(Voucer vcr) {
        voucerDAO.insert(vcr);
    }
    
    public void update(Voucer vcr) {
        voucerDAO.update(vcr);
    }
    
    public void delete(int id) {
        voucerDAO.delete(id);
    }
    
    public List<Voucer> ambilSemuaVoucer() {
        return voucerDAO.readAll();
    }
    
    public List<Voucer> ambilVoucerPublic(String filter) {
        return voucerDAO.getPUBLIC_voucer(filter);
    }
    
    public List<Voucer> ambilVoucerLimited(String filter) {
        return voucerDAO.getLIMITED_voucer(filter);
    }
    
    public Voucer cekValiditasVoucerLimited(String kodeVoucer) {
        return voucerDAO.checkVoucer_LIMITED(kodeVoucer);
    }
    
    public void kurangiStokVoucer(int id, int stokBaru) {
        voucerDAO.updateStok(id, stokBaru);
    }
    
    public Voucer getDataById(int id){
        return voucerDAO.getById(id);
    }
    
    public List<Voucer> search(String keyword){
        return voucerDAO.searchVoucer(keyword);
    }
    
    
    
    
    
    
    
    
    
    
    
    
    

    
    
    
    
    
    
   
    
    
    
    
    
}
