package com.projeto_integrador.projeto_integrador.modules.schedule.usecases;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.projeto_integrador.projeto_integrador.modules.schedule.entity.ScheduleEntity;
import com.projeto_integrador.projeto_integrador.modules.schedule.repository.ScheduleRepository;

@Service
public class CreateSchedule {
    
    @Autowired
    ScheduleRepository repository;

    public ScheduleEntity execute(ScheduleEntity scheduleEntity){

        ScheduleEntity scheduleEntity2 = new ScheduleEntity();

        scheduleEntity2.setTeacher(scheduleEntity.getTeacher());
        
        return this.repository.save(scheduleEntity);
    }
}
