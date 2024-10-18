package com.projeto_integrador.projeto_integrador.modules.student.usecases;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.projeto_integrador.projeto_integrador.exceptions.UserFoundException;
import com.projeto_integrador.projeto_integrador.modules.student.entity.StudentEntity;
import com.projeto_integrador.projeto_integrador.modules.student.repository.StudentRepository;

@Service
public class CreateStudentUseCase {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public StudentEntity execute(StudentEntity studentEntity){
        this.studentRepository.findByInstitutionalEmail(studentEntity.getInstitutionalEmail())
                                .ifPresent(user -> {
                                    throw new UserFoundException();
        });

        var password = passwordEncoder.encode(studentEntity.getStudentPassword());
        studentEntity.setStudentPassword(password);

        return this.studentRepository.save(studentEntity);
    }
}
