/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.store_app.util;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

/**
 *
 * @author Flame
 */
public class ItemChangeListener implements ItemListener {
    public ItemChangeListener() {
    }
    
    
    @Override
    public void itemStateChanged(ItemEvent event) {
       if (event.getStateChange() == ItemEvent.SELECTED) {
          Object item = event.getItem();
          // do something with object
       }
    }  
}
