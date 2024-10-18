package com.projeto_integrador.projeto_integrador.modules.admin.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.RequiredMode;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthAdminDTO {
    
    @Schema(example = "12345678", requiredMode = RequiredMode.REQUIRED, description = "Senha do Administrador")
    private String adminPassword;

    @Schema(example = "tadeu1234@gmail", requiredMode = RequiredMode.REQUIRED, description = "Email do Administrador")
    private String adminEmail;
}
