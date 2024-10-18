package com.projeto_integrador.projeto_integrador.modules.teacher.usecases;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.projeto_integrador.projeto_integrador.modules.subjects.entity.SubjectEntity;
import com.projeto_integrador.projeto_integrador.modules.subjects.repository.SubjectRepository;
import com.projeto_integrador.projeto_integrador.modules.teacher.entity.TeacherEntity;
import com.projeto_integrador.projeto_integrador.modules.teacher.repository.TeacherRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class GetTeacherById {
    
    @Autowired
    private TeacherRepository repository;

    @Autowired
    private SubjectRepository subjectRepository;

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

        // Transformar IDs em nomes
        List<String> subjectNames = new ArrayList<>();
        if (teacher.getTeacherSubjects() != null) {
            subjectNames = teacher.getTeacherSubjects().stream()
                .map(subjectId -> {
                    Optional<SubjectEntity> subject = subjectRepository.findById(subjectId);
                    return subject.map(SubjectEntity::getSubjectName).orElse("Unknown Subject");
                })
                .collect(Collectors.toList());
        }

        result.put("subjects", subjectNames);
        return result;

        /*  Código para retornar o nome e id da disciplina
        List<String> subjectDescriptions = teacher.getTeacherSubjects().stream()
            .map(subjectId -> {
                Optional<SubjectEntity> subject = subjectRepository.findById(subjectId);
                return subject.map(sub -> String.format("%s (%d)", sub.getSubjectName(), sub.getSubjectId()))
                              .orElse("Unknown Subject");
            })
            .collect(Collectors.toList());

        result.put("subjects", subjectDescriptions);
        return result;
        */
    }
}
