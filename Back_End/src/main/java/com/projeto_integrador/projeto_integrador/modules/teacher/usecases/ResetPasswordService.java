package com.projeto_integrador.projeto_integrador.modules.teacher.usecases;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.projeto_integrador.projeto_integrador.modules.teacher.repository.PasswordResetTokenRepository;
import com.projeto_integrador.projeto_integrador.modules.teacher.repository.TeacherRepository;

@Service
public class ResetPasswordService {

    @Autowired
    private PasswordResetTokenRepository tokenRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private TeacherRepository teacherRepository;

    public void resetPassword(String token, String newPassword) {
        var resetToken = tokenRepository.findByToken(token)
            .orElseThrow(() -> new IllegalArgumentException("Invalid token"));

        if (resetToken.getExpiryDate().isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("Token has expired");
        }

        var teacher = resetToken.getTeacher();
        teacher.setTeacherPassword(passwordEncoder.encode(newPassword));
        teacherRepository.save(teacher);

        tokenRepository.delete(resetToken);
    }
}

