package com.projeto_integrador.projeto_integrador.modules.schedule.dto;

import java.time.LocalDateTime;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.RequiredMode;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ScheduleDTO {

    @Schema(description = "ID do professor associado ao horário", example = "{ \"teacherId\": 1}", requiredMode = RequiredMode.REQUIRED)
    private Long teacher;

    @Schema(description = "ID da matéria associada ao horário", example = "{ \"subjectId\": 1}", requiredMode = RequiredMode.REQUIRED)
    private Long subject;

    @Schema(description = "ID do horário associado à disciplina", example = "{ \"timeId\": 1}", requiredMode = RequiredMode.REQUIRED)
    private Long time;

    @Schema(description = "ID da sala associada ao horário", example = "{ \"roomId\": 1}", requiredMode = RequiredMode.REQUIRED)
    private Long room;

    @Schema(description = "ID do curso associado ao horário", example = "{ \"courseId\": 1}", requiredMode = RequiredMode.REQUIRED)
    private Long course;

    @Schema(description = "Dia da semana do horário", example = "Segunda-Feira", requiredMode = RequiredMode.REQUIRED)
    private String weekDay;
}
