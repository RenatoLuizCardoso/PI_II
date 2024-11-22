package com.projeto_integrador.projeto_integrador.modules.reservation.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.RequiredMode;
import lombok.Data;

@Data
public class ReservationDTO {
    
    @Schema(example = "1", requiredMode = RequiredMode.REQUIRED, description = "Professor da reserva")
    private Long teacher;

    @Schema(example = "1", requiredMode = RequiredMode.REQUIRED, description = "Matéria da reserva")
    private Long subject;

    @Schema(example = "1", requiredMode = RequiredMode.REQUIRED, description = "Hora da reserva")
    private Long time;

    @Schema(example = "10/12", requiredMode = RequiredMode.REQUIRED, description = "Data da reserva")
    private Long date;

    @Schema(example = "1", requiredMode = RequiredMode.REQUIRED, description = "Sala da reserva")
    private Long room;

    @Schema(example = "1", requiredMode = RequiredMode.REQUIRED, description = "Curso da reserva")
    private Long course;
}
