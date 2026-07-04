/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.store_app.controller;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
/**
 *
 * @author Flame
 */
public class userController {
    private final PropertyChangeSupport pcs = new PropertyChangeSupport(this);
    private boolean signInSignal = false;
    
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        pcs.addPropertyChangeListener(listener);
    }
    
    public userController(){}
    
    public void fireSignal(){
        pcs.firePropertyChange("signedIn", false, true);
    }
    
    static public boolean signIn(){
        return true;
    }
    
    public boolean signUp(){
        return false;
    }
}
