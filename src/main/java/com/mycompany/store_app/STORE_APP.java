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
    private static MainPanel mainpanel;
    private static AdminPanel adminpanel;
    private static UserController form;
    private static PromptLogin promptlogin;
    
    private static String emailAdmin = "pass123@gmail.com";
    private static String usernameAdmin = "admin";
    
    public static void rebuild(){
        mainpanel = new MainPanel(new MainPanel.listenerMainPanel() {
            @Override
            public void onLogOut() {
                rebuild();
            }
        });
        
        adminpanel = new AdminPanel(new AdminPanel.ListenerAdminPanel(){
            @Override
            public void onLogOut() {
                rebuild();
            }
        });
        
        form = new UserController(new UserController.ListenerUserController(){
            @Override
            public void onLogin(User user) {
                promptlogin.dispose();
                if (user.getNama().equals(usernameAdmin) && (user.getEmail().equals(emailAdmin))){
                    adminpanel.setVisible(true);
                } else {
                    mainpanel.setVisible(true);
                }
            }
        });
        
        promptlogin = new PromptLogin(form);
        promptlogin.setVisible(true);
    }
    
    public static void main(String[] args) {
        FlatLightLaf.setup();

        rebuild();
    }
    
    
   
    
}
