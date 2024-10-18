package com.projeto_integrador.projeto_integrador.modules.rooms.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.RequiredMode;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoomTypeDTO {
    
     @Schema(example = "Sala", requiredMode = RequiredMode.REQUIRED, description = "Descrições do tipo de sala")
    private String roomTypeDescription;
}
