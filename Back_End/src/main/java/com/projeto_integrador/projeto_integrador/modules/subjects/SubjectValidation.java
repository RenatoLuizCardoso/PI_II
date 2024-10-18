package com.projeto_integrador.projeto_integrador.modules.subjects;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.projeto_integrador.projeto_integrador.modules.subjects.entity.SubjectEntity;
import com.projeto_integrador.projeto_integrador.modules.subjects.repository.SubjectRepository;

@Service
public class SubjectValidation {

    @Autowired
    private SubjectRepository subjectRepository;

    public void validateSubjectsExist(List<Long> subjectIds) {
        if (subjectIds != null && !subjectIds.isEmpty()) {
            for (Long subjectId : subjectIds) {
                Optional<SubjectEntity> subject = subjectRepository.findById(subjectId);
                if (subject.isEmpty()) {
                    throw new RuntimeException("Subject not found with ID: " + subjectId);
                }
            }
        }
    }
}
