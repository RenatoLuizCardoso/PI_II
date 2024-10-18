package com.projeto_integrador.projeto_integrador.modules.schedule.usecases;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.projeto_integrador.projeto_integrador.modules.schedule.repository.ScheduleRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class DeleteScheduleById {

    @Autowired
    ScheduleRepository repository;
    
    public void execute(Long id) {
        if (this.repository.existsById(id)) {
            this.repository.deleteById(id);
        } else {
            throw new EntityNotFoundException("Schedule not found");
        }
    }
}
