package com.projeto_integrador.projeto_integrador.modules.student.usecases;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.projeto_integrador.projeto_integrador.modules.student.repository.StudentRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class DeleteStudentUseCase {
    
    @Autowired
    StudentRepository repository;
    
    public void execute(Long id) {
        if (this.repository.existsById(id)) {
            this.repository.deleteById(id);
        } else {
            throw new EntityNotFoundException("Student not found");
        }
    }
}
