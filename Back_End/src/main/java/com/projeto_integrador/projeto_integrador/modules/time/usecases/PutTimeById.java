package com.projeto_integrador.projeto_integrador.modules.time.usecases;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.projeto_integrador.projeto_integrador.modules.time.entity.TimeEntity;
import com.projeto_integrador.projeto_integrador.modules.time.repository.TimeRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class PutTimeById {

    @Autowired
    TimeRepository timeRepository;
    
    public TimeEntity execute(Long id, TimeEntity timeEntity) {
        TimeEntity updateTime = this.timeRepository.findById(id).orElseThrow(
            () -> new EntityNotFoundException("Time not found")
        );
        updateTime.setStartTime(timeEntity.getStartTime());
        updateTime.setEndTime(timeEntity.getEndTime());


        return this.timeRepository.save(updateTime);
    }
}
