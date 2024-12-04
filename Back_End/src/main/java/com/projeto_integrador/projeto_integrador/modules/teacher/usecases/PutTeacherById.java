package com.projeto_integrador.projeto_integrador.modules.teacher.usecases;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.projeto_integrador.projeto_integrador.modules.teacher.entity.TeacherEntity;
import com.projeto_integrador.projeto_integrador.modules.teacher.repository.TeacherRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class PutTeacherById {

    @Autowired
    TeacherRepository teacherRepository;

    public TeacherEntity execute(Long id, TeacherEntity teacherEntity) {

        TeacherEntity updateTeacher = this.teacherRepository.findById(id).orElseThrow(
            () -> new EntityNotFoundException("Teacher not found")
        );

        updateTeacher.setTeacherName(teacherEntity.getTeacherName());
        updateTeacher.setInstitutionalEmail(teacherEntity.getInstitutionalEmail());
        updateTeacher.setPersonalEmail(teacherEntity.getPersonalEmail());
        updateTeacher.setTeacherPassword(teacherEntity.getTeacherPassword());
        updateTeacher.setPersonalPhone(teacherEntity.getPersonalPhone());
        updateTeacher.setBusinessPhone(teacherEntity.getBusinessPhone());
        updateTeacher.setResearchLine(teacherEntity.getResearchLine());
        updateTeacher.setTeacherArea(teacherEntity.getTeacherArea());
        updateTeacher.setTeacherSubjects(teacherEntity.getTeacherSubjects());


        return this.teacherRepository.save(updateTeacher);
    }
}
