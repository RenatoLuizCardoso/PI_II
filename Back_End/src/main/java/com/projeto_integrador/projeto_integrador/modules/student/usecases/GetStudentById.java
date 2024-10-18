package com.projeto_integrador.projeto_integrador.modules.student.usecases;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import com.projeto_integrador.projeto_integrador.modules.student.entity.StudentEntity;
import com.projeto_integrador.projeto_integrador.modules.student.repository.StudentRepository;


import jakarta.persistence.EntityNotFoundException;

@Service
public class GetStudentById {
    
    @Autowired
    StudentRepository repository;
    
    public StudentEntity execute(@PathVariable Long id) {
        return this.repository.findById(id).orElseThrow(
            () -> new EntityNotFoundException("Student not found")
        );
    }
}
