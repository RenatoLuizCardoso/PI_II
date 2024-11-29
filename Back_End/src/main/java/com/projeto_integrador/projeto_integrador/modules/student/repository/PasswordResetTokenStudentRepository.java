package com.projeto_integrador.projeto_integrador.modules.student.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.projeto_integrador.projeto_integrador.modules.student.entity.PasswordResetTokenStudent;

public interface PasswordResetTokenStudentRepository extends JpaRepository<PasswordResetTokenStudent, Long>{
    Optional<PasswordResetTokenStudent> findByToken(String token);
}
