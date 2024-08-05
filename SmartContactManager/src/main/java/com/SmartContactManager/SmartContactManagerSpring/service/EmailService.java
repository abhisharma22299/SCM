package com.SmartContactManager.SmartContactManagerSpring.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;



@Service
public class EmailService {

    @Autowired
    JavaMailSender javaMailSender;

    @Value("$(spring.mail.username)")
    private String fromEmailId;

    public boolean sendEmail(String subject ,String message,String to) {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();

try {
    simpleMailMessage.setFrom(fromEmailId);
    simpleMailMessage.setTo(to);
    simpleMailMessage.setText(message);
    simpleMailMessage.setSubject(subject);

    javaMailSender.send(simpleMailMessage);
    System.out.println("email send ");
    return true;
}catch (Exception e){

    System.out.println("EMAil not send");
    return false;
}
}
}
