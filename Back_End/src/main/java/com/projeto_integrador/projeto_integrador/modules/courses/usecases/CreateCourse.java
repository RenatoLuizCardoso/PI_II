package com.projeto_integrador.projeto_integrador.modules.courses.usecases;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.projeto_integrador.projeto_integrador.modules.courses.repository.CourseRepository;
import com.projeto_integrador.projeto_integrador.modules.courses.entity.CourseEntity;

@Service
public class CreateCourse {
    
    @Autowired
    CourseRepository courseRepository;

    public CourseEntity execute(CourseEntity courseEntity){
        return this.courseRepository.save(courseEntity);
    }
}
