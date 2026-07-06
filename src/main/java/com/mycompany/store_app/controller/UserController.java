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
    private String emailAdmin = "pass123@gmail.com";
    private String usernameAdmin = "admin";
    
    public interface ListenerUserController{
        void onLogin(User user);
    }
    
    private static class OTPHolder {
        String value;

        OTPHolder(String value) {
            this.value = value;
        }
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
            listener.onLogin(signedUser);
        }
        
        return null;
    }
    
    private boolean resendOTP(User user, String otp){
        return emailservice.sendEmailOTP(user.getEmail(), otp);
    }
    
    public boolean signUp(PromptLogin promptlogin, User user) {
        OTPHolder otpHolder = new OTPHolder(generateOTP());

        if (userdao.checkEmail_unik(user.getEmail())) {
            emailservice.sendEmailOTP(user.getEmail(), otpHolder.value);

            promptlogin.createOTPInput(new PromptLogin.OTPCallback() {
                @Override
                public void onOTPEntered(String enteredOTP) {
                    if (enteredOTP.equals(otpHolder.value)) {
                        userdao.register(user);
                        promptlogin.onCompleteSignUp();
                        JOptionPane.showMessageDialog(null,
                                "Registration successful! Please sign in with your credentials.",
                                "Success",
                                JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(null,
                                "Invalid OTP. Please try again.",
                                "Error",
                                JOptionPane.ERROR_MESSAGE);
                    }
                }

                @Override
                public void onResendOTP() {
                    otpHolder.value = generateOTP();
                    emailservice.sendEmailOTP(user.getEmail(), otpHolder.value);
                    JOptionPane.showMessageDialog(null,
                            "New OTP has been sent to your email!",
                            "OTP Resent",
                            JOptionPane.INFORMATION_MESSAGE);
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
