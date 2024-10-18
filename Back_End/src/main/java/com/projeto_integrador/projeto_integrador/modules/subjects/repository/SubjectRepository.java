package com.projeto_integrador.projeto_integrador.modules.subjects.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.projeto_integrador.projeto_integrador.modules.subjects.entity.SubjectEntity;

public interface SubjectRepository extends JpaRepository<SubjectEntity, Long>{
    Optional<SubjectEntity> findById(Long subjectId);
}