package com.projeto_integrador.projeto_integrador.modules.schedule.usecases;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.projeto_integrador.projeto_integrador.modules.schedule.entity.ScheduleEntity;
import com.projeto_integrador.projeto_integrador.modules.schedule.repository.ScheduleRepository;
import com.projeto_integrador.projeto_integrador.modules.student.repository.StudentRepository;

@Service
public class CreateSchedule {
    
    @Autowired
    ScheduleRepository repository;

    @Autowired
    StudentRepository studentRepository;

    @Autowired
    private FKValidation validation;

    public ScheduleEntity execute(ScheduleEntity scheduleEntity){
        
        
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

        ScheduleEntity scheduleEntity2 = new ScheduleEntity();

        scheduleEntity2.setTeacher(teacherId);
        
        return this.repository.save(scheduleEntity);
    }
}
