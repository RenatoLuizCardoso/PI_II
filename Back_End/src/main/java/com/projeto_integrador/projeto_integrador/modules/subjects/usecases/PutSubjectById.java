package com.projeto_integrador.projeto_integrador.modules.subjects.usecases;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.projeto_integrador.projeto_integrador.modules.subjects.entity.SubjectEntity;
import com.projeto_integrador.projeto_integrador.modules.subjects.repository.SubjectRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class PutSubjectById {

    @Autowired
    SubjectRepository subjectRepository;
    
    public SubjectEntity execute(Long id, SubjectEntity subjectEntity) {
        SubjectEntity updateSubject = this.subjectRepository.findById(id).orElseThrow(
            () -> new EntityNotFoundException("Subject not found")
        );
        updateSubject.setSubjectName(subjectEntity.getSubjectName());
        updateSubject.setSubjectHours(subjectEntity.getSubjectHours());


        return this.subjectRepository.save(updateSubject);
    }
}
