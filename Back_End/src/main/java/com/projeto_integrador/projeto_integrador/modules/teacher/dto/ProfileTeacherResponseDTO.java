package com.projeto_integrador.projeto_integrador.modules.teacher.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProfileTeacherResponseDTO {
    
    private Long teacherId;

    private String teacherName;

    private String institutionalEmail;

    private String personalEmail;

    private String personalPhone;

    private String businessPhone;

    private String researchLine;
    
    private String teacherArea;

    private List<Long> teacherSubjects;

    private String profilePhoto;
}
