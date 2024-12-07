package com.projeto_integrador.projeto_integrador.modules.subjects.dto;

import java.time.LocalDateTime;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.RequiredMode;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SubjectDTO {
    @Schema(description = "Nome da disciplina", example = "Matemática", requiredMode = RequiredMode.REQUIRED)
    private String subjectName;

    @Schema(description = "Carga horária da disciplina", example = "60 horas", requiredMode = RequiredMode.REQUIRED)
    private String subjectHours;

}
