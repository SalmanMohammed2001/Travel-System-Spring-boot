package com.travelserviceapi.travelserviceapi.service.process.impl;

import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;




@Service
public class EmailServiceImpl implements EmailService {


    @Autowired
    private  JavaMailSender emailSender;

    @Value("${spring.mail.username}")
    private String fromEmail;



    @Override
    public boolean createEmails(String to, String subject, String body) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(fromEmail);
        message.setTo(to);
        message.setSubject(subject);
        message.setText(body);
        emailSender.send(message);
    //    return true;
        try {
            MimeMessage mimeMessage = emailSender.createMimeMessage();

            MimeMessageHelper mimeMessageHelper;
            mimeMessageHelper
                    = new MimeMessageHelper(mimeMessage, true);

            mimeMessageHelper.setFrom(fromEmail);
            mimeMessageHelper.setTo(to);
            mimeMessageHelper.setText(body, true);
            mimeMessageHelper.setSubject(subject);
            emailSender.send(mimeMessage);
            return true;

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


}
