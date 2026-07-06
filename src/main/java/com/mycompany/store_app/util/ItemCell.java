/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.store_app.util;
import com.mycompany.store_app.model.entity.Barang;
import java.text.DecimalFormat;
/**
 *
 * @author Flame
 */
public class ItemCell {


    private Barang barang;
    private int qty;
    private double total;
    
    public ItemCell(Barang barang) {
        this.barang = barang;
        this.qty = 0;
        this.total = 0;
    }
    
    public Object[]toTableRow(int rowNum){
        DecimalFormat df = new DecimalFormat("#,##0.##");
        return new Object[]{this, rowNum, getBarang().getNama(), "RP "+df.format(getBarang().getHarga()), getBarang().getStok(), qty, "RP "+df.format(total)};
    }
    
    public Barang getBarang() {
        return barang;
    }

    public void setBarang(Barang barang) {
        this.barang = barang;
    }
    
    /**
     * @return the qty
     */
    public int getQty() {
        return qty;
    }

    /**
     * @param qty the qty to set
     */
    public void setQty(int qty) {
        this.qty = qty;
    }

    /**
     * @return the total
     */
    public double getTotal() {
        return total;
    }

    /**
     * @param total the total to set
     */
    public void setTotal(double total) {
        this.total = total;
    }




    
    

}
