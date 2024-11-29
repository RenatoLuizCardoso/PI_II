package com.projeto_integrador.projeto_integrador.modules.student.usecases;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class EmailServiceStudent {

    private static final Logger logger = LoggerFactory.getLogger(EmailServiceStudent.class);
    
    @Autowired
    private JavaMailSender mailSender;

    public void sendResetPasswordEmail(String toEmail, String token) {
        String subject = "Reset your password";
        String resetUrl = "http://localhost:8080/student/reset-password?token=" + token;
        String message = "To reset your password, click the link below:\n" + resetUrl;

        logger.info("Sending password reset email to: {}", toEmail);
        
        try {
            sendEmail(toEmail, subject, message);
            logger.info("Email enviado com sucesso para {}", toEmail);
        } catch (Exception e) {
            logger.error("Erro ao enviar o email para {}: {}", toEmail, e.getMessage());
            throw e;
        }
    }

    private void sendEmail(String toEmail, String subject, String body) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(toEmail);
        mailMessage.setSubject(subject);
        mailMessage.setText(body);
        
        try {
            mailSender.send(mailMessage);
        } catch (Exception e) {
            logger.error("Erro ao enviar email: {}", e.getMessage());
            throw e;
        }
    }
}
