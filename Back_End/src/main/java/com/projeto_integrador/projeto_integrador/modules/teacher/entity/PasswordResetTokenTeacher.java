package com.projeto_integrador.projeto_integrador.modules.teacher.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.Data;

@Entity(name = "password_reset_token_teacher")
@Data
public class PasswordResetTokenTeacher {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String token;

    @OneToOne
    @JoinColumn(name = "teacher_id", referencedColumnName = "teacher_id")
    private TeacherEntity teacher;

    @Column(nullable = false)
    private LocalDateTime expiryDate;
}
