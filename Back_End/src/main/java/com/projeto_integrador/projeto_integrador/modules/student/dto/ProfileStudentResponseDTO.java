package com.projeto_integrador.projeto_integrador.modules.student.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProfileStudentResponseDTO {
    private Long studentId;
    private String studentName;
    private String institutionalEmail;
}
