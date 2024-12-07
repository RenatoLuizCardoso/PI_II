package com.projeto_integrador.projeto_integrador.modules.teacher.dto;

import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.RequiredMode;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TeacherDTO {

    @Schema(description = "Nome completo do professor", example = "João Silva", requiredMode = RequiredMode.REQUIRED)
    private String teacherName;

    @Schema(description = "Email institucional do professor", example = "joao.silva@universidade.com", requiredMode = RequiredMode.REQUIRED)
    private String institutionalEmail;

    @Schema(description = "Email pessoal do professor", example = "joao.silva@gmail.com", requiredMode = RequiredMode.REQUIRED)
    private String personalEmail;

    @Schema(description = "Telefone pessoal do professor", example = "(11) 98765-4321", requiredMode = RequiredMode.REQUIRED)
    private String personalPhone;

    @Schema(description = "Telefone comercial do professor", example = "(11) 91234-5678", requiredMode = RequiredMode.REQUIRED)
    private String businessPhone;

    @Schema(description = "Linha de pesquisa do professor", example = "Inteligência Artificial", requiredMode = RequiredMode.REQUIRED)
    private String researchLine;

    @Schema(description = "Área de atuação do professor", example = "Engenharia de Software", requiredMode = RequiredMode.REQUIRED)
    private String teacherArea;

    @Schema(description = "Lista de IDs das disciplinas associadas ao professor", example = "[{\\\"subjectId\\\": 1}, {\\\"subjectId\\\": 2}]")
    private List<Long> teacherSubjects;

    @Schema(description = "Caminho da foto de perfil do professor", example = "/uploads/teacher_photos/1_profile.jpg")
    private String profilePhoto;
}
