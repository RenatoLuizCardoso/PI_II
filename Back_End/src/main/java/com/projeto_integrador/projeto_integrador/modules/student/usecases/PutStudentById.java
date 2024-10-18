package com.projeto_integrador.projeto_integrador.modules.student.usecases;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.projeto_integrador.projeto_integrador.modules.student.entity.StudentEntity;
import com.projeto_integrador.projeto_integrador.modules.student.repository.StudentRepository;


import jakarta.persistence.EntityNotFoundException;

@Service
public class PutStudentById {

    @Autowired
    StudentRepository repository;
    
    public StudentEntity execute(Long id, StudentEntity studentEntity) {
        StudentEntity updateStudent = this.repository.findById(id).orElseThrow(
            () -> new EntityNotFoundException("Student not found")
        );
        updateStudent.setStudentName(studentEntity.getStudentName());
        updateStudent.setInstitutionalEmail(studentEntity.getInstitutionalEmail());
        updateStudent.setStudentPassword(studentEntity.getStudentPassword());

        return this.repository.save(updateStudent);
    }
}
