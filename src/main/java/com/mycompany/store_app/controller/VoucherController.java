/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.store_app.controller;

import com.mycompany.store_app.model.DAO.VoucherDAO;
import com.mycompany.store_app.model.entity.Voucher;
import java.util.List;

/**
 *
 * @author karis
 */
public class VoucherController {
    private final VoucherDAO voucherDAO;
    
    public VoucherController() {
        this.voucherDAO = new VoucherDAO();
    }
    public void insert(Voucher vcr) {
        voucherDAO.insert(vcr);
    }
    
    public void update(Voucher vcr) {
        voucherDAO.update(vcr);
    }
    
    public void delete(int id) {
        voucherDAO.delete(id);
    }
    
    public int count(String jenis, String search){
        return voucherDAO.count(jenis, search);
    }
    
    public List<Voucher> ambilVoucherPublic(){
        return voucherDAO.ambilVoucherPublic();
    }
    public List<Voucher> ambilVoucherValid(int EntryPerPage, int Page, String jenis, String search) {
        return voucherDAO.getAllVoucher(EntryPerPage, Page, jenis, search);
    }
    
    public Voucher cekValiditasVoucherLimited(String kodeVoucer) {
        return voucherDAO.checkVoucher_LIMITED(kodeVoucer);
    }
    
    public void kurangiStokVoucher(int id, int stokBaru) {
        voucherDAO.updateStok(id, stokBaru);
    }
    
    public Voucher getDataById(int id){
        return voucherDAO.getById(id);
    }
    
    public List<Voucher> search(String keyword){
        return voucherDAO.searchVoucher(keyword);
    }
    
    
    
    
    
    
    
    
    
    
    
    
    

    
    
    
    
    
    
   
    
    
    
    
    
}
