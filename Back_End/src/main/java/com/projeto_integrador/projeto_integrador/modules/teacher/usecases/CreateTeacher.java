package com.projeto_integrador.projeto_integrador.modules.teacher.usecases;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import com.projeto_integrador.projeto_integrador.exceptions.UserFoundException;
import com.projeto_integrador.projeto_integrador.modules.teacher.entity.TeacherEntity;
import com.projeto_integrador.projeto_integrador.modules.teacher.repository.TeacherRepository;

@Service
public class CreateTeacher {
    
    @Autowired
    TeacherRepository teacherRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public TeacherEntity execute(TeacherEntity teacherEntity){
        this.teacherRepository.findByInstitutionalEmail(teacherEntity.getInstitutionalEmail())
                                .ifPresent(user -> {
                                    throw new UserFoundException();
        });
        
        var password = passwordEncoder.encode(teacherEntity.getTeacherPassword());
        teacherEntity.setTeacherPassword(password);

        return this.teacherRepository.save(teacherEntity);
    }

    
        
}
