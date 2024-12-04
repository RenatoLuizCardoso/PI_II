package com.projeto_integrador.projeto_integrador.modules.schedule.usecases;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.projeto_integrador.projeto_integrador.modules.schedule.entity.ScheduleEntity;
import com.projeto_integrador.projeto_integrador.modules.schedule.repository.ScheduleRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class PutScheduleById {

    @Autowired
    ScheduleRepository repository;

    public ScheduleEntity execute(Long id, ScheduleEntity scheduleEntity){
        
        ScheduleEntity updateSchedule = this.repository.findById(id).orElseThrow(
            () -> new EntityNotFoundException("Schedule not found with id: " + id)
        );


        updateSchedule.setTime(scheduleEntity.getTime());
        updateSchedule.setTeacher(scheduleEntity.getTeacher());
        updateSchedule.setSubject(scheduleEntity.getSubject());
        updateSchedule.setRoom(scheduleEntity.getRoom());
        updateSchedule.setCourse(scheduleEntity.getCourse());


        return this.repository.save(updateSchedule);
    }
}
