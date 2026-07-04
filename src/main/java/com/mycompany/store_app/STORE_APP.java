/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.store_app;
import com.mycompany.store_app.model.entity.*;
import com.mycompany.store_app.model.DAO.*;
import java.util.List;
import com.formdev.flatlaf.FlatLightLaf;
import com.mycompany.store_app.controller.UserController;
import com.mycompany.store_app.view.*;
import javax.swing.*;
import java.beans.PropertyChangeEvent;

/**
 *
 * @author karis
 */
public class STORE_APP {
    
    public static void main(String[] args) {
        FlatLightLaf.setup();
        MainPanel mainpanel = new MainPanel();
        AdminPanel adminpanel = new AdminPanel();
        UserController form = new UserController();
        PromptLogin promptlogin = new PromptLogin(form);
        promptlogin.setVisible(true);
        
        SwingUtilities.invokeLater(() -> {
            form.addPropertyChangeListener((PropertyChangeEvent evt) -> {
                System.out.println("com.mycompany.store_app.STORE_APP.main()");
                if ("signedUser".equals(evt.getPropertyName())) {
                    User user = (User) evt.getNewValue();
                    if (user != null) {
                        promptlogin.setVisible(false);
                        if (user.getNama().equals("admin")){
                            adminpanel.setVisible(true);
                        } else {
                            mainpanel.setVisible(true);
                        }
                    }
                }
            });
        });
    }
    
    
   
    
}
