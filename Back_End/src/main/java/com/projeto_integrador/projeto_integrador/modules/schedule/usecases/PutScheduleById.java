package com.projeto_integrador.projeto_integrador.modules.schedule.usecases;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import java.util.Optional;
import org.springframework.stereotype.Service;

import com.projeto_integrador.projeto_integrador.modules.schedule.entity.ScheduleEntity;
import com.projeto_integrador.projeto_integrador.modules.schedule.repository.ScheduleRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class PutScheduleById {

    @Autowired
    ScheduleRepository repository;

    @Autowired
    private FKValidation validation;
    
    public ScheduleEntity execute(Long id, ScheduleEntity scheduleEntity){
        
        ScheduleEntity updateSchedule = this.repository.findById(id).orElseThrow(
            () -> new EntityNotFoundException("Schedule not found with id: " + id)
        );

        Long subjectId = scheduleEntity.getSubject();
        validation.validateSubjectExist(subjectId);

        Long timeId = scheduleEntity.getTime();
        validation.validateTimeExist(timeId);

        Long teacherId = scheduleEntity.getTeacher();
        validation.validateTeacherExist(teacherId);

        Long roomId = scheduleEntity.getRoom();
        validation.validateRoomExist(roomId);

        Long courseId = scheduleEntity.getCourse();
        validation.validateCourseExist(courseId);

        updateSchedule.setTime(scheduleEntity.getTime());
        updateSchedule.setTeacher(scheduleEntity.getTeacher());
        updateSchedule.setSubject(scheduleEntity.getSubject());
        updateSchedule.setRoom(scheduleEntity.getRoom());
        updateSchedule.setCourse(scheduleEntity.getCourse());


        return this.repository.save(updateSchedule);
    }
}
