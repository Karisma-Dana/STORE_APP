/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.store_app.controller;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import com.mycompany.store_app.model.DAO.UserDAO;
import com.mycompany.store_app.model.entity.User;
import com.mycompany.store_app.util.EmailServices;
import com.mycompany.store_app.view.PromptLogin;
import java.util.List;
import java.util.Random;
import javax.swing.JOptionPane;
/**
 *
 * @author Flame
 */
public class UserController {
    private final UserDAO userdao = new UserDAO();
    private final PropertyChangeSupport pcs = new PropertyChangeSupport(this);
    private final EmailServices emailservice = new EmailServices();
    private UserController.ListenerUserController listener;
    private User signedUser;
    
    public interface ListenerUserController{
        void onLogin(User user);
    }
    
    private String generateOTP(){
        String OTP = "";
        Random randomNumber = new Random();
        
        for (int i = 0; i < 5; i++){
            int random = randomNumber.nextInt(9);
            OTP = OTP + random;
        }
        System.out.println(OTP);
        return OTP;
    }
    
    public UserController(){}
    
    public UserController(ListenerUserController listener){
        this.listener = listener;
    }
    public void signalUserSigned(){
        pcs.firePropertyChange("signedUser", null, signedUser);
    }
    
    public User signIn(User user){
        if(!userdao.checkEmail_unik(user.getEmail())){
            signedUser = userdao.login(user.getEmail(), user.getPassword());
            listener.onLogin(user);
        }
        return null;
    }
    
    public boolean signUp(PromptLogin promptlogin, User user) {
        String otp = generateOTP();

        if (userdao.checkEmail_unik(user.getEmail())) {
            emailservice.sendEmailOTP(user.getEmail(), otp);

            promptlogin.createOTPInput((String enteredOTP) -> {
                if (enteredOTP.equals(otp)) {
                    userdao.register(user);
                    promptlogin.onCompleteSignUp();
                    JOptionPane.showMessageDialog(null, "Registered, go ahead and sign in with your credentials.");
                } else {
                    // Handle wrong OTP
                    JOptionPane.showMessageDialog(null, "Invalid OTP. Please try again.");
                }
            });
            return true;
        }
        return false;
    }
    
    public List<User> getAllData(){
        return userdao.readAll();
    }
}
