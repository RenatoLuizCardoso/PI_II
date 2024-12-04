package com.projeto_integrador.projeto_integrador.modules.teacher.usecases;

import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.projeto_integrador.projeto_integrador.modules.teacher.dto.ProfileTeacherResponseDTO;
import com.projeto_integrador.projeto_integrador.modules.teacher.repository.TeacherRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class ProfileTeacherUseCase {
    
    @Autowired
    private TeacherRepository teacherRepository;

    public ProfileTeacherResponseDTO execute(Long teacherId) {
        var teacher = this.teacherRepository.findById(teacherId)
            .orElseThrow(() -> {
                throw new UsernameNotFoundException("User not found");
            });

        var teacherDTO = ProfileTeacherResponseDTO.builder()
            .teacherName(teacher.getTeacherName())
            .teacherArea(teacher.getTeacherArea())
            .teacherSubjects(teacher.getTeacherSubjects().stream()
                .map(subject -> subject.getSubjectId())
                .collect(Collectors.toList()))
            .teacherId(teacher.getTeacherId())
            .institutionalEmail(teacher.getInstitutionalEmail())
            .businessPhone(teacher.getBusinessPhone())
            .personalEmail(teacher.getPersonalEmail())
            .personalPhone(teacher.getPersonalPhone())
            .researchLine(teacher.getResearchLine())
            .profilePhoto(teacher.getProfilePhoto())
            .build();
        
        return teacherDTO;
    }

     public void updatePhoto(Long teacherId, String photoPath) {
        var teacher = teacherRepository.findById(teacherId)
            .orElseThrow(() -> new EntityNotFoundException("Professor não encontrado"));

        teacher.setProfilePhoto(photoPath); // Atualiza a foto de perfil
        teacherRepository.save(teacher); // Salva a atualização no banco
    }
}
