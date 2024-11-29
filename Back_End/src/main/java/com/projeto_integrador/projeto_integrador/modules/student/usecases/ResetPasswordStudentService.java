package com.projeto_integrador.projeto_integrador.modules.student.usecases;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.projeto_integrador.projeto_integrador.modules.student.repository.PasswordResetTokenStudentRepository;
import com.projeto_integrador.projeto_integrador.modules.student.repository.StudentRepository;

@Service
public class ResetPasswordStudentService {

    @Autowired
    private PasswordResetTokenStudentRepository tokenRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private StudentRepository studentRepository;

    public void resetPassword(String token, String newPassword) {
        var resetToken = tokenRepository.findByToken(token)
            .orElseThrow(() -> new IllegalArgumentException("Invalid token"));

        if (resetToken.getExpiryDate().isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("Token has expired");
        }

        var student = resetToken.getStudent();
        student.setStudentPassword(passwordEncoder.encode(newPassword));
        studentRepository.save(student);

        tokenRepository.delete(resetToken);
    }
}

