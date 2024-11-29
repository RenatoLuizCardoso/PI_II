package com.projeto_integrador.projeto_integrador.modules.teacher.usecases;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.projeto_integrador.projeto_integrador.modules.teacher.entity.PasswordResetTokenTeacher;
import com.projeto_integrador.projeto_integrador.modules.teacher.repository.PasswordResetTokenRepository;
import com.projeto_integrador.projeto_integrador.modules.teacher.repository.TeacherRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class ForgotPasswordTeacherService {
    
    @Autowired
    private TeacherRepository teacherRepository;

    @Autowired
    private EmailServiceTeacher emailService;

    @Autowired
    private PasswordResetTokenRepository passwordRepository;

    public void generateResetToken(String email) {
        var teacher = teacherRepository.findByInstitutionalEmail(email)
            .orElseThrow(() -> new EntityNotFoundException("Teacher not found"));

        String token = UUID.randomUUID().toString();

        LocalDateTime expiryDate = LocalDateTime.now().plusHours(1);

        PasswordResetTokenTeacher passwordResetToken = new PasswordResetTokenTeacher();
        
        passwordResetToken.setToken(token);
        passwordResetToken.setTeacher(teacher);
        passwordResetToken.setExpiryDate(expiryDate);

        passwordRepository.save(passwordResetToken);
        
        emailService.sendResetPasswordEmail(email, token);
    }
}
