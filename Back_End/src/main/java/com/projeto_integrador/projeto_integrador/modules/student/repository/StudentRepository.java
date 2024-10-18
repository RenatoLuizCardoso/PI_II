package com.projeto_integrador.projeto_integrador.modules.student.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.projeto_integrador.projeto_integrador.modules.student.entity.StudentEntity;

public interface StudentRepository extends JpaRepository<StudentEntity, Long> {
    Optional<StudentEntity> findByInstitutionalEmailAndStudentPassword(String institutionalEmail, String studentPassword);
    Optional<StudentEntity> findByInstitutionalEmail(String institutionalEmail);
}
