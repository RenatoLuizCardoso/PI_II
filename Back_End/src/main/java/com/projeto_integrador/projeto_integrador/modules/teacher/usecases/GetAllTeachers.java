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
public class GetAllTeachers {

    @Autowired
    private TeacherRepository teacherRepository;

    public List<Map<String, Object>> execute() {
        var allTeachers = teacherRepository.findAll();
        if (allTeachers.isEmpty()) {
            throw new EntityNotFoundException("Teacher not Registered");
        }

        return allTeachers.stream()
                          .map(this::convertTeacherToMap)
                          .collect(Collectors.toList());
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

        List<String> subjectNames = teacher.getTeacherSubjects().stream()
                                           .map(subject -> subject.getSubjectName())
                                           .collect(Collectors.toList());

        result.put("subjects", subjectNames);
        return result;
    }
}
