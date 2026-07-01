/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.store_app;
import com.formdev.flatlaf.FlatLightLaf;
import com.mycompany.store_app.view.PromptLogin;
/**
 *
 * @author karis
 */
public class STORE_APP {
    public static void main(String[] args) {
        FlatLightLaf.setup();
        
        PromptLogin promptlogin = new PromptLogin();
        promptlogin.setVisible(true);
    }
}
