package com.projeto_integrador.projeto_integrador.modules.student.usecases;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.projeto_integrador.projeto_integrador.modules.student.entity.StudentEntity;
import com.projeto_integrador.projeto_integrador.modules.student.repository.StudentRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class GetAllStudents {
    
    @Autowired
    StudentRepository repository;

    public List<StudentEntity> execute() {
        var allStudents = repository.findAll();
        if (allStudents.isEmpty()) {
            throw new EntityNotFoundException("Student not Register");
        }
        return allStudents;
    }
}
