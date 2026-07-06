/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.store_app.util;

import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import java.util.Properties;
/**
 *
 * @author karis
 */
public class EmailServices {
    private final String username = "";
    private final String password = "";
    
    public boolean sendEmailOTP(String dstEmail, String OTPcode) {
        Properties prop = new Properties();
        prop.put("mail.smtp.host", "smtp.gmail.com");
        prop.put("mail.smtp.port", "587");
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.starttls.enable", "true");

        Session session = Session.getInstance(prop, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        try {
            System.out.println("test");
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(dstEmail));

            message.setSubject("Kode Verifikasi OTP Aplikasi Kasir - Icikbos");

            String isiEmail = "<div style='font-family: Arial, sans-serif; padding: 20px;'>"
                    + "<h3>Halo Boss!</h3>"
                    + "<p>Berikut adalah kode OTP untuk memverifikasi akun pendaftaran Anda:</p>"
                    + "<h2 style='color: #2196F3; font-size: 32px; letter-spacing: 5px;'>" + OTPcode + "</h2>"
                    + "<p>Gunakan kode ini di aplikasi kasir. Jangan dibagikan ke siapa pun ya, Boss!</p>"
                    + "</div>";

            message.setContent(isiEmail, "text/html; charset=utf-8");

            Transport.send(message);
            System.out.println("Email OTP sukses dikirim ke: " + dstEmail);
            return true; // Success

        } catch (MessagingException e) {
            System.out.println("Gagal mengirim email: " + e.getMessage());
            return false; // Failure
        }
    }
}
