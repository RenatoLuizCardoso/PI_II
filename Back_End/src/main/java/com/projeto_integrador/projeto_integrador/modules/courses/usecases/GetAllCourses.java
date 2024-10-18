package com.projeto_integrador.projeto_integrador.modules.courses.usecases;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.projeto_integrador.projeto_integrador.modules.courses.entity.CourseEntity;
import com.projeto_integrador.projeto_integrador.modules.courses.repository.CourseRepository;
import com.projeto_integrador.projeto_integrador.modules.subjects.entity.SubjectEntity;
import com.projeto_integrador.projeto_integrador.modules.subjects.repository.SubjectRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class GetAllCourses {
    
    @Autowired
    CourseRepository courseRepository;

    @Autowired
    SubjectRepository subjectRepository;

    public List<Map<String, Object>> execute() {
        var allCourses = courseRepository.findAll();
        if (allCourses.isEmpty()) {
            throw new EntityNotFoundException("Course not Registered");
        }

        return allCourses.stream()
                          .map(this::convertCourseToMap)
                          .collect(Collectors.toList());
    }

    private Map<String, Object> convertCourseToMap(CourseEntity course) {
        Map<String, Object> result = new HashMap<>();
        result.put("courseId", course.getCourseId());
        result.put("courseName", course.getCourseName());
        result.put("courseSemester", course.getCourseSemester());
        result.put("coursePeriod", course.getCoursePeriod());

    
        // Transformar IDs em nomes
        List<String> subjectNames = new ArrayList<>();
        if(course.getCourseSubjects() != null) {
            subjectNames = course.getCourseSubjects().stream()
                .map(subjectId -> {
                    Optional<SubjectEntity> subject = subjectRepository.findById(subjectId);
                    return subject.map(SubjectEntity::getSubjectName).orElse("Unknown Subject");
                })
                .collect(Collectors.toList());
        }
        
        result.put("subjects", subjectNames);
        return result;

    }
}

