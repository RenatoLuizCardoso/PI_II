package com.projeto_integrador.projeto_integrador.modules.student.usecases;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.projeto_integrador.projeto_integrador.modules.student.entity.PasswordResetTokenStudent;
import com.projeto_integrador.projeto_integrador.modules.student.repository.PasswordResetTokenStudentRepository;
import com.projeto_integrador.projeto_integrador.modules.student.repository.StudentRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class ForgotPasswordStudentService {
    
    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private EmailServiceStudent emailService;

    @Autowired
    private PasswordResetTokenStudentRepository passwordRepository;

    public void generateResetToken(String email) {
        var student = studentRepository.findByInstitutionalEmail(email)
            .orElseThrow(() -> new EntityNotFoundException("Student not found"));

        String token = UUID.randomUUID().toString();

        LocalDateTime expiryDate = LocalDateTime.now().plusHours(1);

        PasswordResetTokenStudent passwordResetToken = new PasswordResetTokenStudent();
        
        passwordResetToken.setToken(token);
        passwordResetToken.setStudent(student);
        passwordResetToken.setExpiryDate(expiryDate);

        passwordRepository.save(passwordResetToken);
        
        emailService.sendResetPasswordEmail(email, token);
    }
}
