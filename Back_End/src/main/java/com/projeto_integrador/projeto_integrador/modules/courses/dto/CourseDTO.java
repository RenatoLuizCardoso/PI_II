package com.projeto_integrador.projeto_integrador.modules.courses.dto;

import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.RequiredMode;
import lombok.Data;

@Data
public class CourseDTO {
    
    @Schema(example = "Desenvolvimento de Sistemas", requiredMode = RequiredMode.REQUIRED, description = "Nome do curso")
    private String courseName;

    @Schema(example = "2°", requiredMode = RequiredMode.REQUIRED, description = "Semestre ou Ano do curso")
    private String courseSemester;

    @Schema(example = "Tarde", requiredMode = RequiredMode.REQUIRED, description = "Período do curso")
    private String coursePeriod;

    @Schema(example = "Programação Multiplataforma", requiredMode = RequiredMode.REQUIRED, description = "Máterias do curso")
    private List<Long> courseSubjects;
}
