package com.projeto_integrador.projeto_integrador.modules.subjects.usecases;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.projeto_integrador.projeto_integrador.modules.subjects.entity.SubjectEntity;
import com.projeto_integrador.projeto_integrador.modules.subjects.repository.SubjectRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class GetAllSubjects {
    
    @Autowired
    SubjectRepository subjectRepository;

    public List<SubjectEntity> execute() {
        var allSubjects = subjectRepository.findAll();
        if (allSubjects.isEmpty()) {
            throw new EntityNotFoundException("Subject not Registered");
        }
        return allSubjects;
    }
}

