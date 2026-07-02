/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.store_app;
import com.mycompany.store_app.model.entity.*;
import com.mycompany.store_app.model.DAO.*;
import java.util.List;
import com.formdev.flatlaf.FlatLightLaf;
import com.mycompany.store_app.controller.userController;
import com.mycompany.store_app.view.*;
import javax.swing.*;
import java.beans.PropertyChangeEvent;

/**
 *
 * @author karis
 */
public class STORE_APP {
    

    public void main(String[] args) {
//    file java untuk pengecekan setiap function yang diperlukan. 
//    NOTE : DILARANG MENAMBAHKAN FUNCTION LAIN SELAIN UNTUK TESTING PRODUCT. 
        
        
    public static void main(String[] args) {
        FlatLightLaf.setup();
        MainPanel mainpanel = new MainPanel();
        PromptLogin promptlogin = new PromptLogin();
        promptlogin.setVisible(true);
        
        SwingUtilities.invokeLater(() -> {
            userController form = new userController();

            // **** SUBSCRIBE to the event system ****
            form.addPropertyChangeListener((PropertyChangeEvent evt) -> {
                // This method runs automatically whenever the boolean changes!
                if ("signedIn".equals(evt.getPropertyName())) {
                    boolean receivedBool = (boolean) evt.getNewValue();

                    // **** DO YOUR MAIN LOGIC HERE ****
                    System.out.println("Main function received signal: " + receivedBool);
                    if (receivedBool) {
                        System.out.println("-> Executing TRUE logic");
                        mainpanel.setVisible(true);
                        promptlogin.setVisible(false);
                    } else {
                        System.out.println("-> Executing FALSE logic");
                    }
                }
            });
            form.fireSignal();
        });
    }
    
    
   
    
}
