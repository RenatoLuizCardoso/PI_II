package com.projeto_integrador.projeto_integrador.modules.student.usecases;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.projeto_integrador.projeto_integrador.modules.student.entity.PasswordResetToken;
import com.projeto_integrador.projeto_integrador.modules.student.repository.PasswordResetTokenRepository;
import com.projeto_integrador.projeto_integrador.modules.student.repository.StudentRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class ForgotPasswordService {
    
    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private EmailService emailService;

    @Autowired
    private PasswordResetTokenRepository passwordRepository;

    public void generateResetToken(String email) {
        var student = studentRepository.findByInstitutionalEmail(email)
            .orElseThrow(() -> new EntityNotFoundException("Student not found"));

        String token = UUID.randomUUID().toString();

        LocalDateTime expiryDate = LocalDateTime.now().plusHours(1);

        PasswordResetToken passwordResetToken = new PasswordResetToken();
        
        passwordResetToken.setToken(token);
        passwordResetToken.setStudent(student);
        passwordResetToken.setExpiryDate(expiryDate);

        passwordRepository.save(passwordResetToken);
        
        emailService.sendResetPasswordEmail(email, token);
    }
}
