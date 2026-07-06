/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.mycompany.store_app.view;

import com.formdev.flatlaf.FlatClientProperties;
import com.mycompany.store_app.controller.BarangController;
import com.mycompany.store_app.controller.DetailPenjualanController;
import com.mycompany.store_app.controller.PenjualanController;
import com.mycompany.store_app.controller.VoucherController;
import com.mycompany.store_app.util.ItemCell;
import com.mycompany.store_app.model.entity.Barang;
import com.mycompany.store_app.model.entity.Detail_penjualan;
import com.mycompany.store_app.util.ItemChangeListener;
import com.mycompany.store_app.model.entity.Penjualan;
import com.mycompany.store_app.util.QtyCellEditor;
import com.mycompany.store_app.model.entity.Voucher;
import java.awt.Color;
import java.awt.Component;
import java.awt.event.ItemEvent;
import java.text.DecimalFormat;
import java.util.ArrayList;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

/**
 *
 * @author Flame
 */
public class MainPanel extends javax.swing.JFrame {
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(MainPanel.class.getName());
    private final BarangController barangcontroller = new BarangController();
    private final VoucherController vouchercontroller = new VoucherController();
    private final DetailPenjualanController dpcontroller = new DetailPenjualanController();
    private final PenjualanController pcontroller = new PenjualanController();
    /**
     * Creates new form MainPanel
     */
    final private String SEARCHPLACEHOLDER = "Type to search for item...";
    private int EntryPerPage = 25;
    private int CurPage = 1;
    private int TotalPage;
    private double CurFilterHarga = 0;
    private DecimalFormat df = new DecimalFormat("#,##0.##");
    private List<Detail_penjualan> ListBarangTemp = new ArrayList();
    private List<Barang> TableDataList = new ArrayList();
    private Voucher appliedlimited, appliedPublic;
    private MainPanel.listenerMainPanel listener;
    
    public interface listenerMainPanel{
        void onLogOut();
    }
    
    private void resetDetailPenjualan(){
        SubtotalLabelRightnum.setText("RP 0");
        SubtotalUndernum.setText("RP 0");
        TotalLabelNum.setText("RP 0");
        VoucherLimitedLabel.setText("RP -0");
        VoucherPublicLabel.setText("RP -0");
        VoucherLimitedPercentLabel.setText("");
        VoucherPublicPercentLabel.setText("");
        VoucherField.setText("...");
        
        if (VoucherComboBox.getItemCount() > 0) {
            VoucherComboBox.setSelectedIndex(-1);
        }
    }
    
    private void reset(){
//        reset table checkout

        ListBarangTemp.clear();
        DefaultTableModel model = (DefaultTableModel) TabelCheckout.getModel();
        model.setRowCount(0);
        
        if (TableBarang.isEditing()) {
            TableBarang.getCellEditor().stopCellEditing();
        }
        TableBarang.clearSelection();
        
        CartButton.setVisible(true);
        ShoppingPanel.setVisible(true);
        BackButton.setVisible(false);
        KasirPanel.setVisible(false);
        
        CurFilterHarga = 0;
        CurPage = 1;
        
        resetDetailPenjualan();
        appliedlimited = null;
        appliedPublic = null;
        
        getTableData();
        loadPage();
    }
    
    private void processTransaction(){
        if (ListBarangTemp.size() < 1){
            System.out.println("Tidak boleh");
            return;
        }
        double total = refreshCalc();
        int notaID = pcontroller.insert(new Penjualan(total));
        Penjualan penjualan = pcontroller.getDatabyID(notaID);
        
        if (appliedPublic != null){
            vouchercontroller.kurangiStokVoucher(appliedPublic.getId(), vouchercontroller.getDataById(appliedPublic.getId()).getStok()-1);
        }
        
        if (appliedlimited != null){
            vouchercontroller.kurangiStokVoucher(appliedlimited.getId(), vouchercontroller.getDataById(appliedlimited.getId()).getStok()-1);
        }
        
        for (Detail_penjualan dp : ListBarangTemp) {
            dp.setPenjualan(penjualan);
            if (barangcontroller.ambilBarangById(dp.getBarang().getId()).getStok() < dp.getJumlah()) {
                JOptionPane.showMessageDialog(
                        null,
                        "Insufficient stock for item: " + dp.getBarang().getNama()
                        + "\nAvailable: " + barangcontroller.ambilBarangById(dp.getBarang().getId()).getStok()
                        + "\nRequested: " + dp.getJumlah(),
                        "Transaction Error",
                        JOptionPane.ERROR_MESSAGE
                );
                return;
            }
            barangcontroller.perbaruiStok(dp.getBarang().getId(), barangcontroller.ambilBarangById(dp.getBarang().getId()).getStok()-dp.getJumlah());
            dpcontroller.insert(dp);
        }
        reset();
    }
    
    public static void addTextListener(JTextField field, java.util.function.Consumer<String> consumer) {
        field.getDocument().addDocumentListener(new DocumentListener() {
            @Override public void insertUpdate(DocumentEvent e) { consumer.accept(field.getText()); }
            @Override public void removeUpdate(DocumentEvent e) { consumer.accept(field.getText()); }
            @Override public void changedUpdate(DocumentEvent e) { consumer.accept(field.getText()); }
        });
    }
    
    private void refreshVoucherCombo(){
        DefaultComboBoxModel combomodel = new DefaultComboBoxModel();
        List<Voucher> publicvoucher = vouchercontroller.ambilVoucherPublic();
        
        for (Voucher vc : publicvoucher) {
            combomodel.addElement(vc);
        }
        
        VoucherComboBox.setModel(combomodel);
        combomodel.setSelectedItem(combomodel.getElementAt(-1));
    }
    
    private double refreshCalc(){
        double subtotal = 0;
        for (Detail_penjualan Dp : ListBarangTemp) {
            subtotal += Dp.getSubtotal();
        }
        double LimitedDiscount = 0;
        double PublicDiscount = 0;
        

        if (appliedlimited != null){
            VoucherLimitedPercentLabel.setText("-"+appliedlimited.getDiskon()+"%");
            LimitedDiscount = appliedlimited.getDiskon()*subtotal/100;
            VoucherLimitedLabel.setText("RP -"+df.format(LimitedDiscount));
        } else {
            VoucherLimitedPercentLabel.setText("");
        }
        
        if (appliedPublic != null){
            VoucherPublicPercentLabel.setText("-"+appliedPublic.getDiskon()+"%");
            PublicDiscount = appliedPublic.getDiskon()*subtotal/100;
            VoucherPublicLabel.setText("RP -"+df.format(PublicDiscount));
        } else {
            VoucherPublicPercentLabel.setText("");
        }
        
        double total = subtotal - LimitedDiscount - PublicDiscount;
        if (total < 0 ){
            total = 0;
        }
        SubtotalLabelRightnum.setText("RP " + df.format(subtotal));
        SubtotalUndernum.setText("RP " + df.format(subtotal));
        TotalLabelNum.setText("RP " +df.format(total));
        return total;
    }
    public MainPanel() {};
    
    public MainPanel(listenerMainPanel listener) {
        this.listener = listener;
        initComponents();
        VoucherComboBox.addItemListener(new ItemChangeListener(){
            @Override
            public void itemStateChanged(ItemEvent event) {
                if (VoucherComboBox.getSelectedIndex() == -1){
                    refreshCalc();
                    return;
                }
                appliedPublic = (Voucher) event.getItem();
                refreshCalc();
            }   
        });
        
        addTextListener(SearchField, newtext -> {
            getTableData();
            loadPage();
        });
        getTableData();
        loadPage();
    }
    
    private String retrieveSearch(){
        if (SearchField.getText().equals("Type to search for item...")){
            return "";
        } else {
            return SearchField.getText();
        }
    }
    public void getTableData(){
        TotalPage = (int) Math.ceil((double) barangcontroller.countAvailable(CurFilterHarga, retrieveSearch()) / EntryPerPage);
        if (TotalPage < CurPage) {
            CurPage = TotalPage;
            if (CurPage <= 0) {
                CurPage = 1;
            }
        }
        TableDataList = barangcontroller.ambilSemuaBarangAvailable(EntryPerPage, CurPage, retrieveSearch(), CurFilterHarga);
        PageNumberLabel.setText(CurPage + " / " + TotalPage);
    }
    
    public void newRow(DefaultTableModel model, QtyCellEditor qtyEditor, Barang barang){
        model.addRow(new ItemCell(barang).toTableRow(TableBarang.getRowCount()+1));
        qtyEditor.addPropertyChangeListener(evt -> {
            if ("quantity".equals(evt.getPropertyName())) {
                    Object[] data = (Object[]) evt.getNewValue();
                    int row = (int) data[0];
                    int col = (int) data[1];
                    int newQty = (int) data[2];
                    
                    if (newQty > 0) {
                        Barang newBarang = ((ItemCell) TableBarang.getValueAt(row, 0)).getBarang();
                        for (int i = 0; i < ListBarangTemp.size(); i++){
                            if(ListBarangTemp.get(i).getBarang().equals(newBarang)){
                                ListBarangTemp.get(i).setJumlah(newQty);
                                return;
                            }
                        }
                        
                        ListBarangTemp.add(new Detail_penjualan(newBarang, newQty));
                    }
            }
        });
    }
    
    public void loadPage(){
        QtyCellEditor qtyEditor = new QtyCellEditor();
        TableBarang.getColumnModel().getColumn(5).setCellEditor(qtyEditor);
        TableBarang.getColumnModel().getColumn(5).setCellRenderer(new DefaultTableCellRenderer(){
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
                setHorizontalAlignment(SwingConstants.CENTER);
                return this;
            }
            
        });
        DefaultTableModel model = (DefaultTableModel)TableBarang.getModel();
        model.setRowCount(0);
        for (int i = 0; i < TableDataList.size(); i++){
            newRow(model, qtyEditor, TableDataList.get(i));
        }
        LabelResult.setText("Showing " + TableDataList.size() +" result of " + EntryPerPage + " possible");
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        TopPanel = new javax.swing.JPanel();
        CartButton = new javax.swing.JButton();
        BackButton = new javax.swing.JButton();
        LogOutButton = new javax.swing.JButton();
        CardMainPanel = new javax.swing.JPanel();
        ShoppingPanel = new javax.swing.JPanel();
        FilterPanelMenu = new javax.swing.JPanel();
        SpinnerNumberModel doubleModel = new SpinnerNumberModel(
            0.0,    // initial value (Double)
            0.0,    // minimum (Double)
            Double.MAX_VALUE,  // maximum (Double)
            1     // step size (Double)
        );
        FilterHargaSpinner = new javax.swing.JSpinner();
        jLabel2 = new javax.swing.JLabel();
        ShoppingTablePanel = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        TableBarang = new javax.swing.JTable();
        PageNumberLabel = new javax.swing.JLabel();
        NextButton = new javax.swing.JButton();
        PreviousButton = new javax.swing.JButton();
        AddToCartButton = new javax.swing.JButton();
        SearchField = new javax.swing.JTextField();
        LabelResult = new javax.swing.JLabel();
        FilterButton = new javax.swing.JButton();
        KasirPanel = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        TabelCheckout = new javax.swing.JTable();
        TGLHeaderLabel = new javax.swing.JLabel();
        SubtotalLabelUnder = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        SubtotalLabelRightnum = new javax.swing.JLabel();
        VoucherComboBox = new javax.swing.JComboBox<>();
        VoucherField = new javax.swing.JTextField();
        TotalLabel = new javax.swing.JLabel();
        VoucherPublicLabel = new javax.swing.JLabel();
        TotalLabelNum = new javax.swing.JLabel();
        SubtotalLabelRight = new javax.swing.JLabel();
        VoucherLimitedLabel = new javax.swing.JLabel();
        CheckoutButton = new javax.swing.JButton();
        CancelButton = new javax.swing.JButton();
        VoucherPublicPercentLabel = new javax.swing.JLabel();
        VoucherLimitedPercentLabel = new javax.swing.JLabel();
        TransaksiHeader = new javax.swing.JLabel();
        SubtotalUndernum = new javax.swing.JLabel();
        TGLLabel = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMinimumSize(new java.awt.Dimension(1280, 720));
        setPreferredSize(new java.awt.Dimension(1300, 760));

        TopPanel.setBackground(new java.awt.Color(12, 185, 168));
        TopPanel.setPreferredSize(new java.awt.Dimension(1280, 65));
        TopPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        CartButton.setBackground(new java.awt.Color(12, 185, 168));
        CartButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/shopping-cart.png"))); // NOI18N
        CartButton.setFocusPainted(false);
        CartButton.setFocusable(false);
        CartButton.addActionListener(this::CartButtonActionPerformed);
        TopPanel.add(CartButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(1212, 12, 41, 41));
        CartButton.putClientProperty(FlatClientProperties.STYLE, "borderWidth: 0; focusColor: #0cb9a8");

        BackButton.setBackground(new java.awt.Color(12, 185, 168));
        BackButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/arrow_left_fill.png"))); // NOI18N
        BackButton.setFocusPainted(false);
        BackButton.setFocusable(false);
        BackButton.addActionListener(this::BackButtonActionPerformed);
        TopPanel.add(BackButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(1212, 12, 41, 41));
        BackButton.putClientProperty(FlatClientProperties.STYLE, "borderWidth: 0; focusColor: #0cb9a8");
        BackButton.setVisible(false);

        LogOutButton.setBackground(new java.awt.Color(12, 185, 168));
        LogOutButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/log-out.png"))); // NOI18N
        LogOutButton.setFocusPainted(false);
        LogOutButton.setFocusable(false);
        LogOutButton.addActionListener(this::LogOutButtonActionPerformed);
        TopPanel.add(LogOutButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 12, 41, 41));
        CartButton.putClientProperty(FlatClientProperties.STYLE, "borderWidth: 0; focusColor: #0cb9a8");

        getContentPane().add(TopPanel, java.awt.BorderLayout.NORTH);

        CardMainPanel.setPreferredSize(new java.awt.Dimension(1255, 640));
        CardMainPanel.setLayout(new java.awt.CardLayout());

        ShoppingPanel.setBackground(new java.awt.Color(255, 255, 255));
        ShoppingPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        FilterPanelMenu.setBackground(new java.awt.Color(255, 255, 255));
        FilterPanelMenu.setPreferredSize(new java.awt.Dimension(150, 50));
        FilterPanelMenu.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        FilterHargaSpinner.setModel(doubleModel);
        FilterHargaSpinner.setPreferredSize(new java.awt.Dimension(100, 30));
        FilterHargaSpinner.addChangeListener(this::FilterHargaSpinnerStateChanged);
        FilterPanelMenu.add(FilterHargaSpinner, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 12, -1, 25));
        FilterHargaSpinner.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                Object value = ((JSpinner) e.getSource()).getValue();
                CurFilterHarga = Double.parseDouble(value.toString());
            }
        });

        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("Harga");
        jLabel2.setPreferredSize(new java.awt.Dimension(15, 10));
        FilterPanelMenu.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 12, 50, 25));

        ShoppingPanel.add(FilterPanelMenu, new org.netbeans.lib.awtextra.AbsoluteConstraints(25, 45, 150, 50));
        FilterPanelMenu.setVisible(false);

        ShoppingTablePanel.setBackground(new java.awt.Color(255, 255, 255));
        ShoppingTablePanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jScrollPane1.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        TableBarang.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Data", "No", "Nama", "Harga", "Stok", "Qty", "Total"
            }
        ));
        TableBarang.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        jScrollPane1.setViewportView(TableBarang);
        if (TableBarang.getColumnModel().getColumnCount() > 0) {
            TableBarang.getColumnModel().getColumn(0).setMinWidth(0);
            TableBarang.getColumnModel().getColumn(0).setPreferredWidth(0);
            TableBarang.getColumnModel().getColumn(0).setMaxWidth(0);
            TableBarang.getColumnModel().getColumn(1).setPreferredWidth(60);
            TableBarang.getColumnModel().getColumn(2).setPreferredWidth(620);
            TableBarang.getColumnModel().getColumn(3).setPreferredWidth(200);
            TableBarang.getColumnModel().getColumn(4).setPreferredWidth(80);
            TableBarang.getColumnModel().getColumn(5).setPreferredWidth(80);
            TableBarang.getColumnModel().getColumn(6).setPreferredWidth(200);
        }

        ShoppingTablePanel.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1230, 530));

        PageNumberLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        PageNumberLabel.setText("0 / 0");
        ShoppingTablePanel.add(PageNumberLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(565, 540, 100, 25));

        NextButton.setText("Next");
        NextButton.setPreferredSize(new java.awt.Dimension(100, 30));
        NextButton.addActionListener(this::NextButtonActionPerformed);
        ShoppingTablePanel.add(NextButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(665, 540, -1, -1));

        PreviousButton.setText("Previous");
        PreviousButton.setPreferredSize(new java.awt.Dimension(100, 30));
        PreviousButton.addActionListener(this::PreviousButtonActionPerformed);
        ShoppingTablePanel.add(PreviousButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(465, 540, -1, -1));

        AddToCartButton.setText("Add to Cart");
        AddToCartButton.addActionListener(this::AddToCartButtonActionPerformed);
        ShoppingTablePanel.add(AddToCartButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(1110, 540, 120, 40));

        ShoppingPanel.add(ShoppingTablePanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(25, 60, 1230, 580));

        SearchField.setForeground(new java.awt.Color(153, 153, 153));
        SearchField.setText("Type to search for item...");
        SearchField.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                SearchFieldFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                SearchFieldFocusLost(evt);
            }
        });
        ShoppingPanel.add(SearchField, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 10, 720, 30));

        LabelResult.setText("Showing n result of x possible");
        ShoppingPanel.add(LabelResult, new org.netbeans.lib.awtextra.AbsoluteConstraints(850, 20, -1, -1));

        FilterButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/filter.png"))); // NOI18N
        FilterButton.setMaximumSize(new java.awt.Dimension(55, 55));
        FilterButton.setMinimumSize(new java.awt.Dimension(55, 55));
        FilterButton.setPreferredSize(new java.awt.Dimension(35, 35));
        FilterButton.addActionListener(this::FilterButtonActionPerformed);
        ShoppingPanel.add(FilterButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(25, 10, 35, 35));
        FilterButton.putClientProperty(FlatClientProperties.STYLE, "borderWidth: 0");

        CardMainPanel.add(ShoppingPanel, "card2");

        KasirPanel.setBackground(new java.awt.Color(255, 255, 255));
        KasirPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jScrollPane3.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane3.setPreferredSize(new java.awt.Dimension(700, 500));

        TabelCheckout.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Nama", "Harga", "Kuantitas", "Subtotal"
            }
        ));
        TabelCheckout.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        TabelCheckout.setPreferredSize(new java.awt.Dimension(700, 500));
        TabelCheckout.setShowGrid(false);
        jScrollPane3.setViewportView(TabelCheckout);
        if (TabelCheckout.getColumnModel().getColumnCount() > 0) {
            TabelCheckout.getColumnModel().getColumn(0).setPreferredWidth(100);
            TabelCheckout.getColumnModel().getColumn(1).setPreferredWidth(40);
            TabelCheckout.getColumnModel().getColumn(2).setPreferredWidth(5);
            TabelCheckout.getColumnModel().getColumn(3).setPreferredWidth(40);
        }

        KasirPanel.add(jScrollPane3, new org.netbeans.lib.awtextra.AbsoluteConstraints(25, 65, 700, 500));

        TGLHeaderLabel.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        TGLHeaderLabel.setText("TGL:");
        KasirPanel.add(TGLHeaderLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 40, -1, -1));

        SubtotalLabelUnder.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        SubtotalLabelUnder.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        SubtotalLabelUnder.setText("Subtotal:");
        KasirPanel.add(SubtotalLabelUnder, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 580, -1, -1));

        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        SubtotalLabelRightnum.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        SubtotalLabelRightnum.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        SubtotalLabelRightnum.setText("RP 0.000.000,00");
        jPanel1.add(SubtotalLabelRightnum, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 30, 150, -1));

        VoucherComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jPanel1.add(VoucherComboBox, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 90, 120, 25));

        VoucherField.setForeground(new java.awt.Color(153, 153, 153));
        VoucherField.setText("...");
        VoucherField.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                VoucherFieldFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                VoucherFieldFocusLost(evt);
            }
        });
        VoucherField.addActionListener(this::VoucherFieldActionPerformed);
        jPanel1.add(VoucherField, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 150, 120, 25));

        TotalLabel.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        TotalLabel.setText("Total:");
        jPanel1.add(TotalLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 330, 120, -1));

        VoucherPublicLabel.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        VoucherPublicLabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        VoucherPublicLabel.setText("RP -0.000.000,00");
        jPanel1.add(VoucherPublicLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 90, 150, -1));

        TotalLabelNum.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        TotalLabelNum.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        TotalLabelNum.setText("RP 0.000.000,00");
        jPanel1.add(TotalLabelNum, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 330, 150, -1));

        SubtotalLabelRight.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        SubtotalLabelRight.setText("Subtotal:");
        jPanel1.add(SubtotalLabelRight, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 30, 120, -1));

        VoucherLimitedLabel.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        VoucherLimitedLabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        VoucherLimitedLabel.setText("RP -0.000.000,00");
        jPanel1.add(VoucherLimitedLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 150, 150, -1));

        CheckoutButton.setBackground(new java.awt.Color(12, 181, 80));
        CheckoutButton.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        CheckoutButton.setForeground(new java.awt.Color(255, 255, 255));
        CheckoutButton.setText("Checkout");
        CheckoutButton.addActionListener(this::CheckoutButtonActionPerformed);
        jPanel1.add(CheckoutButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 400, 350, 50));

        CancelButton.setBackground(new java.awt.Color(153, 0, 51));
        CancelButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/x.png"))); // NOI18N
        CancelButton.addActionListener(this::CancelButtonActionPerformed);
        jPanel1.add(CancelButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 400, 50, 50));

        VoucherPublicPercentLabel.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        VoucherPublicPercentLabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        VoucherPublicPercentLabel.setText("-n%");
        jPanel1.add(VoucherPublicPercentLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 90, 70, -1));

        VoucherLimitedPercentLabel.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        VoucherLimitedPercentLabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        VoucherLimitedPercentLabel.setText("-n%");
        jPanel1.add(VoucherLimitedPercentLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 150, 70, -1));

        KasirPanel.add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(800, 65, 450, 500));

        TransaksiHeader.setFont(new java.awt.Font("Segoe UI", 0, 36)); // NOI18N
        TransaksiHeader.setText("Transaksi");
        KasirPanel.add(TransaksiHeader, new org.netbeans.lib.awtextra.AbsoluteConstraints(800, 10, -1, -1));

        SubtotalUndernum.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        SubtotalUndernum.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        SubtotalUndernum.setText("RP 0.000.000,00");
        KasirPanel.add(SubtotalUndernum, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 580, -1, -1));

        TGLLabel.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        TGLLabel.setText("1/1/1900");
        KasirPanel.add(TGLLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 40, -1, -1));

        CardMainPanel.add(KasirPanel, "card2");

        getContentPane().add(CardMainPanel, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void SearchFieldFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_SearchFieldFocusLost
        if (SearchField.getText().isBlank()){
            SearchField.setForeground(new Color(153, 153, 153));
            SearchField.setText(SEARCHPLACEHOLDER);
        }
    }//GEN-LAST:event_SearchFieldFocusLost

    private void SearchFieldFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_SearchFieldFocusGained
        SearchField.setText("");
        SearchField.setForeground(new Color(0, 0, 0));
    }//GEN-LAST:event_SearchFieldFocusGained

    private void AddToCartButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AddToCartButtonActionPerformed
        DefaultTableModel model = (DefaultTableModel) TabelCheckout.getModel();
        model.setRowCount(0);
        double subtotal = 0;
        for (Detail_penjualan Dp : ListBarangTemp) {
            subtotal += Dp.getSubtotal();
            model.addRow(new Object[]{
                Dp.getBarang().getNama(), 
                "RP " + df.format(Dp.getBarang().getHarga()), 
                Dp.getJumlah(), 
                "RP " + df.format(Dp.getSubtotal())
            });
        }
        
        SubtotalLabelRightnum.setText("RP " + df.format(subtotal));
        SubtotalUndernum.setText("RP " + df.format(subtotal));
        refreshVoucherCombo();
    }//GEN-LAST:event_AddToCartButtonActionPerformed

    private void PreviousButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PreviousButtonActionPerformed
        if (1 < CurPage) {
            CurPage --;
        }
        getTableData();
        loadPage();
    }//GEN-LAST:event_PreviousButtonActionPerformed

    private void NextButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_NextButtonActionPerformed
        if (CurPage < TotalPage) {
            CurPage ++;
        }
        getTableData();
        loadPage();
    }//GEN-LAST:event_NextButtonActionPerformed

    private void FilterButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_FilterButtonActionPerformed
        if (FilterPanelMenu.isVisible()) {
            FilterPanelMenu.setVisible(false);
        } else {
            FilterPanelMenu.setVisible(true);
        }
    }//GEN-LAST:event_FilterButtonActionPerformed
    
    private void CartButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CartButtonActionPerformed
        refreshVoucherCombo();
        SimpleDateFormat Date_Format = new SimpleDateFormat("dd-MM-YYYY");
        TGLLabel.setText(Date_Format.format(new Date()));
        CartButton.setVisible(false);
        ShoppingPanel.setVisible(false);
        BackButton.setVisible(true);
        KasirPanel.setVisible(true);
    }//GEN-LAST:event_CartButtonActionPerformed

    private void BackButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BackButtonActionPerformed
        CartButton.setVisible(true);
        ShoppingPanel.setVisible(true);
        BackButton.setVisible(false);
        KasirPanel.setVisible(false);
    }//GEN-LAST:event_BackButtonActionPerformed

    private void CheckoutButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CheckoutButtonActionPerformed
        processTransaction();
    }//GEN-LAST:event_CheckoutButtonActionPerformed

    private void VoucherFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_VoucherFieldActionPerformed
        appliedlimited = vouchercontroller.cekValiditasVoucherLimited(VoucherField.getText());
        refreshCalc();
    }//GEN-LAST:event_VoucherFieldActionPerformed

    private void VoucherFieldFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_VoucherFieldFocusGained
        VoucherField.setText("");
        VoucherField.setForeground(new Color(0, 0, 0));
    }//GEN-LAST:event_VoucherFieldFocusGained

    private void VoucherFieldFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_VoucherFieldFocusLost
        if (VoucherField.getText().isBlank()){
            VoucherField.setForeground(new Color(153, 153, 153));
            VoucherField.setText("...");
        }
    }//GEN-LAST:event_VoucherFieldFocusLost

    private void LogOutButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_LogOutButtonActionPerformed
        listener.onLogOut();
        this.dispose();
    }//GEN-LAST:event_LogOutButtonActionPerformed

    private void CancelButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CancelButtonActionPerformed
        reset();
    }//GEN-LAST:event_CancelButtonActionPerformed

    private void FilterHargaSpinnerStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_FilterHargaSpinnerStateChanged
        CurFilterHarga = (double)FilterHargaSpinner.getValue();
        getTableData();
        loadPage();
    }//GEN-LAST:event_FilterHargaSpinnerStateChanged

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ReflectiveOperationException | javax.swing.UnsupportedLookAndFeelException ex) {
            logger.log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> new MainPanel().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton AddToCartButton;
    private javax.swing.JButton BackButton;
    private javax.swing.JButton CancelButton;
    private javax.swing.JPanel CardMainPanel;
    private javax.swing.JButton CartButton;
    private javax.swing.JButton CheckoutButton;
    private javax.swing.JButton FilterButton;
    private javax.swing.JSpinner FilterHargaSpinner;
    private javax.swing.JPanel FilterPanelMenu;
    private javax.swing.JPanel KasirPanel;
    private javax.swing.JLabel LabelResult;
    private javax.swing.JButton LogOutButton;
    private javax.swing.JButton NextButton;
    private javax.swing.JLabel PageNumberLabel;
    private javax.swing.JButton PreviousButton;
    private javax.swing.JTextField SearchField;
    private javax.swing.JPanel ShoppingPanel;
    private javax.swing.JPanel ShoppingTablePanel;
    private javax.swing.JLabel SubtotalLabelRight;
    private javax.swing.JLabel SubtotalLabelRightnum;
    private javax.swing.JLabel SubtotalLabelUnder;
    private javax.swing.JLabel SubtotalUndernum;
    private javax.swing.JLabel TGLHeaderLabel;
    private javax.swing.JLabel TGLLabel;
    private javax.swing.JTable TabelCheckout;
    private javax.swing.JTable TableBarang;
    private javax.swing.JPanel TopPanel;
    private javax.swing.JLabel TotalLabel;
    private javax.swing.JLabel TotalLabelNum;
    private javax.swing.JLabel TransaksiHeader;
    private javax.swing.JComboBox<String> VoucherComboBox;
    private javax.swing.JTextField VoucherField;
    private javax.swing.JLabel VoucherLimitedLabel;
    private javax.swing.JLabel VoucherLimitedPercentLabel;
    private javax.swing.JLabel VoucherPublicLabel;
    private javax.swing.JLabel VoucherPublicPercentLabel;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane3;
    // End of variables declaration//GEN-END:variables
}
