package com.projeto_integrador.projeto_integrador.modules.teacher.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.projeto_integrador.projeto_integrador.modules.teacher.entity.TeacherEntity;

public interface TeacherRepository extends JpaRepository<TeacherEntity, Long>{
    Optional<TeacherEntity> findByInstitutionalEmail(String institutionalEmail);
    
}