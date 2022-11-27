package com.example.SWP_1631.Utill;

import lombok.Data;

import java.io.PrintWriter;
import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;

@Data
public class SendMail {
    boolean status;
    String defaupass;

    public SendMail() {
//        status : false thì sẽ ko gửi maill
//        code xác minh là defaupass
        this.status = false;
        this.defaupass = "101010";
    }


    public static void SendMail(String to, String sub,
                                String msg, final String user, final String pass) {

        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.ssl.protocols", "TLSv1.2");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        Session session = Session.getInstance(props, new javax.mail.Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(user, pass);
            }
        });

        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(user));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
            message.setSubject(sub);
            message.setContent(msg, "text/html");
            Transport.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    public void sendFuncition(String to, String sub, String msg) {
        SendMail.SendMail(to, sub, msg, "phong.ept.edu@gmail.com", "vupmhnxwexgzvcsw");
    }


    public static void main(String[] args) {
        String subject = "Confirm Your email Test!! !";
        String message = "This is your Code:" + 123;
        SendMail sen = new SendMail();
        sen.sendFuncition("levietaqbangbang1@gmail.com", subject, "<!DOCTYPE html>\n"
                + "viet");

//            SendMail.SendMail("bsd03rd@gmail.com", subject, message, "bachjavaweb@gmail.com", "Songduc&*78");
    }

}
