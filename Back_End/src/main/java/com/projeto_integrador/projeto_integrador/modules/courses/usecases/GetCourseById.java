package com.projeto_integrador.projeto_integrador.modules.courses.usecases;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.projeto_integrador.projeto_integrador.modules.courses.entity.CourseEntity;
import com.projeto_integrador.projeto_integrador.modules.courses.repository.CourseRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class GetCourseById {
    
    @Autowired
    CourseRepository courseRepository;

    public Map<String, Object> execute(Long id) {
        CourseEntity course = courseRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Course not found"));

        return convertCourseToMap(course);
    }

    private Map<String, Object> convertCourseToMap(CourseEntity course) {
        Map<String, Object> result = new HashMap<>();
        result.put("courseId", course.getCourseId());
        result.put("courseName", course.getCourseName());
        result.put("courseSemester", course.getCourseSemester());
        result.put("coursePeriod", course.getCoursePeriod());

    
        List<String> subjectNames = course.getCourseSubjects().stream()
                                           .map(subject -> subject.getSubjectName())
                                           .collect(Collectors.toList());
        
        result.put("subjects", subjectNames);
        return result;
    }
}
