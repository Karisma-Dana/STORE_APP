/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.store_app.controller;

import com.mycompany.store_app.model.DAO.PenjualanDAO;
import com.mycompany.store_app.model.entity.Penjualan;
import com.mycompany.store_app.view.PenjualanPanel;
import com.mycompany.store_app.model.DAO.Detail_penjualanDAO;
import com.mycompany.store_app.model.entity.Detail_penjualan;
import com.mycompany.store_app.model.entity.Barang;
import com.mycompany.store_app.model.DAO.BarangDAO;
import java.util.List;
import javax.swing.table.DefaultTableModel;



/**
 *
 * @author karis
 */
public class PenjualanController {
    
    private PenjualanPanel viewPanel;
    private Detail_penjualanDAO detailDAO;
    private BarangDAO barangDAO;
    private PenjualanDAO penjualanDAO;
    
    
    public PenjualanController(PenjualanPanel viewPanel){
        this.viewPanel = viewPanel;
        this.detailDAO = new Detail_penjualanDAO();
        this.barangDAO = new BarangDAO();
        this.penjualanDAO = new PenjualanDAO();
    }
    
    
    public void klikTableNota(){
        javax.swing.JTable tableNota = viewPanel.getTabelNota();
        int selectedRow = tableNota.getSelectedRow();
        
        if (selectedRow != -1){
            String idNota = tableNota.getValueAt(selectedRow, 0).toString();
            int intIdNota = Integer.parseInt(idNota);
            List<Detail_penjualan> listDetail = detailDAO.getDataBy_ID_nota(intIdNota);
            updateTableDetail(listDetail);
            
            
        }
    }
    
    public void updateTableDetail(List<Detail_penjualan> listDetail){
        javax.swing.JTable tableDetail = viewPanel.getTabelDetail();
        DefaultTableModel modelDetail = (DefaultTableModel) tableDetail.getModel();
        
        
        modelDetail.setRowCount(0);
        
        for (Detail_penjualan detail : listDetail){
            
            Barang barang = detail.getBarang();
            Object[] rowData = {
                detail.getId(),
                barang.getNama(),
                detail.getJumlah(), 
                detail.getSubtotal()
               
            };
            modelDetail.addRow(rowData);
        }
    }
    
    public void UpdateTableNota(String keyword){
        javax.swing.JTable tableNota = viewPanel.getTabelNota();
        DefaultTableModel modelNota = (DefaultTableModel) tableNota.getModel();
        
        modelNota.setRowCount(0);
        
        if (keyword == null || keyword.isEmpty() ){
            List<Penjualan> listPenjualan = penjualanDAO.readAll();
            for (Penjualan penjualan : listPenjualan){
                Object[] rowData = {
                    penjualan.getId_nota(),
                    penjualan.getTotal(),
                    penjualan.getTanggal()
                };
                modelNota.addRow(rowData);
            }
        }else {
            Penjualan penjualan = penjualanDAO.getById(Integer.parseInt(keyword));
            Object[] rowData = {
                penjualan.getId_nota(),
                penjualan.getTotal(),
                penjualan.getTanggal()
            
            };
            modelNota.addRow(rowData);
        }
    }
    
}
