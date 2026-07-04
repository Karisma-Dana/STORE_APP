/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.store_app.util;
import java.util.Random;

/**
 *
 * @author karis
 */
public class testing {
    private EmailServices email = new EmailServices(); 
    public void main(){
        email.sendEmailOTP("gptfamily09@gmail.com", generateOTP());
        
    }
    
    
    public String generateOTP(){
        String OTP = "";
        Random randomNumber = new Random();
        
        for (int i = 0; i < 5; i++){
            int random = randomNumber.nextInt(9);
            OTP = OTP + random;
        }
        System.out.println(OTP);
        return OTP;
    }
    
}
