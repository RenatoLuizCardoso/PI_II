package com.projeto_integrador.projeto_integrador.modules.time.usecases;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import com.projeto_integrador.projeto_integrador.modules.time.entity.TimeEntity;
import com.projeto_integrador.projeto_integrador.modules.time.repository.TimeRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class GetTimeById {
    
    @Autowired
    TimeRepository repository;
    
    public TimeEntity execute(@PathVariable Long id) {
        return this.repository.findById(id).orElseThrow(
            () -> new EntityNotFoundException("Time not found")
        );
    }
}
