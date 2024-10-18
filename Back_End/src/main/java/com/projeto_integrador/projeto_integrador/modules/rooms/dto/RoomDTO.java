package com.projeto_integrador.projeto_integrador.modules.rooms.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.RequiredMode;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoomDTO {
    
    @Schema(example = "30", requiredMode = RequiredMode.REQUIRED, description = "Capacidade da sala/labóratorio")
    private String roomCapacity;

    @Schema(example = "2", requiredMode = RequiredMode.REQUIRED, description = "Andar da sala/labóratorio")
    private String roomFloor;

    @Schema(example = "Computadores, Televisão, Ventiladores, Quadro", requiredMode = RequiredMode.REQUIRED, description = "Recursos da sala/labóratorio")
    private String roomResources;

    @Schema(example = "Livre", requiredMode = RequiredMode.REQUIRED, description = "Capacidade da sala/labóratorio")
    private String roomAvailability;

    @Schema(requiredMode = RequiredMode.REQUIRED, description = "Tipo da sala/laboratório")
    private Long roomType;
}
