/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.store_app.controller;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import com.mycompany.store_app.model.DAO.UserDAO;
import com.mycompany.store_app.model.entity.User;
import java.util.List;



public class userController {
    private final PropertyChangeSupport pcs = new PropertyChangeSupport(this);
    private boolean signInSignal = false;
    
    private  final UserDAO dao;
    
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        pcs.addPropertyChangeListener(listener);
    }
    
    public userController(){
        this.dao = new UserDAO();
    }
    
    
    public void fireSignal(){
        pcs.firePropertyChange("signedIn", false, true);
    }
    
    static public boolean signIn(){
        return true;
    }
    
    public boolean signUp(){
        return false;
    }
    
    public List<User> getAllData(){
        return dao.readAll();
    }
    
    
}
