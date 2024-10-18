package com.projeto_integrador.projeto_integrador.modules.courses.usecases;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.projeto_integrador.projeto_integrador.modules.courses.entity.CourseEntity;
import com.projeto_integrador.projeto_integrador.modules.courses.repository.CourseRepository;
import com.projeto_integrador.projeto_integrador.modules.subjects.SubjectValidation;

import jakarta.persistence.EntityNotFoundException;

@Service
public class PutCourseById {

    @Autowired
    CourseRepository courseRepository;

    @Autowired
    SubjectValidation subjectValidation;
    
    public CourseEntity execute(Long id, CourseEntity courseEntity) {
        CourseEntity updateCourse = this.courseRepository.findById(id).orElseThrow(
            () -> new EntityNotFoundException("Course not found")
        );

        List<Long> subjectIds = courseEntity.getCourseSubjects();
        subjectValidation.validateSubjectsExist(subjectIds);

        updateCourse.setCourseName(courseEntity.getCourseName());
        updateCourse.setCourseSemester(courseEntity.getCourseSemester());
        updateCourse.setCoursePeriod(courseEntity.getCoursePeriod());
        updateCourse.setCourseSubjects(courseEntity.getCourseSubjects());

        return this.courseRepository.save(updateCourse);
    }
}
