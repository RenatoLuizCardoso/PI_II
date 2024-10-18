package com.projeto_integrador.projeto_integrador.modules.time.usecases;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.projeto_integrador.projeto_integrador.modules.time.entity.TimeEntity;
import com.projeto_integrador.projeto_integrador.modules.time.repository.TimeRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class GetAllTimes {
    
    @Autowired
    TimeRepository timeRepository;

    public List<TimeEntity> execute() {
        var allTimes = timeRepository.findAll();
        if (allTimes.isEmpty()) {
            throw new EntityNotFoundException("Time not Registered");
        }
        return allTimes;
    }
}

