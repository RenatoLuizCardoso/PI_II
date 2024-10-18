package com.projeto_integrador.projeto_integrador.modules.time.usecases;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.projeto_integrador.projeto_integrador.modules.time.repository.TimeRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class DeleteTimeById {

    @Autowired
    TimeRepository repository;
    
    public void execute(Long id) {
        if (this.repository.existsById(id)) {
            this.repository.deleteById(id);
        } else {
            throw new EntityNotFoundException("Time not found");
        }
    }
}
