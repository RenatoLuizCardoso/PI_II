package com.projeto_integrador.projeto_integrador.modules.courses.usecases;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.projeto_integrador.projeto_integrador.modules.courses.repository.CourseRepository;
import com.projeto_integrador.projeto_integrador.modules.subjects.SubjectValidation;
import com.projeto_integrador.projeto_integrador.modules.courses.entity.CourseEntity;

@Service
public class CreateCourse {
    
    @Autowired
    CourseRepository courseRepository;

    @Autowired
    SubjectValidation subjectValidation;

    public CourseEntity execute(CourseEntity courseEntity){
        List<Long> subjectIds = courseEntity.getCourseSubjects();
        subjectValidation.validateSubjectsExist(subjectIds);
        return this.courseRepository.save(courseEntity);
    }
}
