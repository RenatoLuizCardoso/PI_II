package com.projeto_integrador.projeto_integrador.modules.schedule.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.RequiredMode;
import lombok.Data;

@Data
public class ScheduleDTO {
    
    @Schema(example = "1", requiredMode = RequiredMode.REQUIRED, description = "Professor do horário")
    private Long teacher;

    @Schema(example = "1", requiredMode = RequiredMode.REQUIRED, description = "Matéria do horário")
    private Long subject;

    @Schema(example = "1", requiredMode = RequiredMode.REQUIRED, description = "Hora do horário")
    private Long time;

    @Schema(example = "1", requiredMode = RequiredMode.REQUIRED, description = "Sala do horário")
    private Long room;

    @Schema(example = "1", requiredMode = RequiredMode.REQUIRED, description = "Curso do horário")
    private Long course;

}
