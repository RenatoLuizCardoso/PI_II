package com.projeto_integrador.projeto_integrador.modules.student.usecases;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.projeto_integrador.projeto_integrador.modules.student.dto.ProfileStudentResponseDTO;
import com.projeto_integrador.projeto_integrador.modules.student.repository.StudentRepository;

@Service
public class ProfileStudentUseCase {
    
    @Autowired
    private StudentRepository studentRepository;

    public ProfileStudentResponseDTO execute(Long idStudent) {
        var student = this.studentRepository.findById(idStudent)
            .orElseThrow(() -> {
                throw new UsernameNotFoundException("User not found");
            });

        var studentDTO = ProfileStudentResponseDTO.builder()
            .studentName(student.getStudentName())
            .institutionalEmail(student.getInstitutionalEmail())
            .studentId(student.getStudentId())
            .build();
        
        return studentDTO;
    }
}
