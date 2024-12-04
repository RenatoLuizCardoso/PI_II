package com.projeto_integrador.projeto_integrador.modules.teacher.usecases;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.projeto_integrador.projeto_integrador.modules.teacher.entity.TeacherEntity;
import com.projeto_integrador.projeto_integrador.modules.teacher.repository.TeacherRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class GetTeacherById {
    
    @Autowired
    private TeacherRepository repository;

    public Map<String, Object> execute(Long id) {
        TeacherEntity teacher = repository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Teacher not found"));

        return convertTeacherToMap(teacher);
    }

    private Map<String, Object> convertTeacherToMap(TeacherEntity teacher) {
        Map<String, Object> result = new HashMap<>();
        result.put("teacherId", teacher.getTeacherId());
        result.put("teacherName", teacher.getTeacherName());
        result.put("institutionalEmail", teacher.getInstitutionalEmail());
        result.put("personalEmail", teacher.getPersonalEmail());
        result.put("teacherPassword", teacher.getTeacherPassword());
        result.put("personalPhone", teacher.getPersonalPhone());
        result.put("businessPhone", teacher.getBusinessPhone());
        result.put("researchLine", teacher.getResearchLine());
        result.put("teacherArea", teacher.getTeacherArea());
        result.put("profilePhoto", teacher.getProfilePhoto());
        result.put("profilePhoto", teacher.getProfilePhoto());


        // Transformar IDs em nomes
        List<String> subjectNames = teacher.getTeacherSubjects().stream()
                                           .map(subject -> subject.getSubjectName())
                                           .collect(Collectors.toList());

        result.put("subjects", subjectNames);
        return result;
    }
}
