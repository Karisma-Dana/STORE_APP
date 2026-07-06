/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.store_app.model.entity;
import java.awt.Component;
import javax.swing.DefaultCellEditor;
import javax.swing.JCheckBox;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.text.DefaultFormatter;
import java.beans.PropertyChangeSupport;
import java.text.DecimalFormat;
/**
 *
 * @author Flame
 */
public class QtyCellEditor extends DefaultCellEditor{
    private final PropertyChangeSupport pcs = new PropertyChangeSupport(this);
    public JSpinner input;
    private JTable table;
    private int row;
    private int column;
    private ItemCell item;
    
    public QtyCellEditor() {
        super(new JCheckBox());
        input = new JSpinner();
        SpinnerNumberModel numberModel = (SpinnerNumberModel)input.getModel();
        numberModel.setMinimum(0);
        numberModel.setMaximum(item.getBarang().getStok());
        JSpinner.NumberEditor editor = (JSpinner.NumberEditor)input.getEditor();
        DefaultFormatter formatter = (DefaultFormatter)editor.getTextField().getFormatter();
        formatter.setCommitsOnValidEdit(true);
        editor.getTextField().setHorizontalAlignment(SwingConstants.CENTER);
        input.addChangeListener(new ChangeListener(){
            @Override
            public void stateChanged(ChangeEvent e) {
                inputChanged();
                pcs.firePropertyChange("quantity", null, new Object[]{
                    row,
                    column,
                    Integer.parseInt(input.getValue().toString())
                });
            }
            
        });
    }
    
    public void addPropertyChangeListener(java.beans.PropertyChangeListener listener) {
        pcs.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(java.beans.PropertyChangeListener listener) {
        pcs.removePropertyChangeListener(listener);
    }
    private void inputChanged(){
        DecimalFormat df = new DecimalFormat("#,##0.##");
        int qty = Integer.parseInt(input.getValue().toString());
        if(qty != item.getQty()){

            item.setQty(qty);
            item.setTotal(item.getBarang().getHarga()*qty);
            table.setValueAt("RP " + df.format(item.getTotal()), row, 6);
        }
    }
    
    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        super.getTableCellEditorComponent(table, value, isSelected, row, column);
        this.table = table;
        this.row = row;
        this.item = (ItemCell)table.getValueAt(row, 0);
        int qty = Integer.parseInt(value.toString());
        input.setValue(qty);
        return input;
    }

    @Override
    public Object getCellEditorValue() {
        return input.getValue();
    }
    
    


    
}
