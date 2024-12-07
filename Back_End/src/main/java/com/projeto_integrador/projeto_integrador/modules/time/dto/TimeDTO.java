package com.projeto_integrador.projeto_integrador.modules.time.dto;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.RequiredMode;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TimeDTO {

    @Schema(example = "08:00", requiredMode = RequiredMode.REQUIRED, description = "Hora de início")
    private String startTime;

    @Schema(example = "12:00", requiredMode = RequiredMode.REQUIRED, description = "Hora de término")
    private String endTime;
}
