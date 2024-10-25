package com.projeto_integrador.projeto_integrador.modules.student.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.projeto_integrador.projeto_integrador.modules.student.entity.PasswordResetToken;

public interface PasswordResetTokenRepository extends JpaRepository<PasswordResetToken, Long>{
    Optional<PasswordResetToken> findByToken(String token);
}
