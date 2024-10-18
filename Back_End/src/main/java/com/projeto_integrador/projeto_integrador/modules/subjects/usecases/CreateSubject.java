package com.projeto_integrador.projeto_integrador.modules.subjects.usecases;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.projeto_integrador.projeto_integrador.modules.subjects.entity.SubjectEntity;
import com.projeto_integrador.projeto_integrador.modules.subjects.repository.SubjectRepository;

@Service
public class CreateSubject {
    
    @Autowired
    SubjectRepository subjectRepository;

    public SubjectEntity execute(SubjectEntity subjectEntity){
        return this.subjectRepository.save(subjectEntity);
    }
}
