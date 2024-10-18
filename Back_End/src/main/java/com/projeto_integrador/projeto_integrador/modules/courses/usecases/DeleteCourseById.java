package com.projeto_integrador.projeto_integrador.modules.courses.usecases;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.projeto_integrador.projeto_integrador.modules.courses.repository.CourseRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class DeleteCourseById {

    @Autowired
    CourseRepository repository;
    
    public void execute(Long id) {
        if (this.repository.existsById(id)) {
            this.repository.deleteById(id);
        } else {
            throw new EntityNotFoundException("Course not found");
        }
    }
}
