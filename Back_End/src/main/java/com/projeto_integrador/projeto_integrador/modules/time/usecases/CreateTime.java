package com.projeto_integrador.projeto_integrador.modules.time.usecases;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.projeto_integrador.projeto_integrador.modules.time.entity.TimeEntity;
import com.projeto_integrador.projeto_integrador.modules.time.repository.TimeRepository;

@Service
public class CreateTime {
    
    @Autowired
    TimeRepository timeRepository;

    public TimeEntity execute(TimeEntity timeEntity){
        return this.timeRepository.save(timeEntity);
    }
}
