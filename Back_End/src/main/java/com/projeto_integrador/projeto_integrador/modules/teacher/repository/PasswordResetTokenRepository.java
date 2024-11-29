package com.projeto_integrador.projeto_integrador.modules.teacher.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.projeto_integrador.projeto_integrador.modules.teacher.entity.PasswordResetTokenTeacher;

public interface PasswordResetTokenRepository extends JpaRepository<PasswordResetTokenTeacher, Long>{
    Optional<PasswordResetTokenTeacher> findByToken(String token);
}
