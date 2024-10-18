package com.projeto_integrador.projeto_integrador.modules.admin.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.RequiredMode;

public class AdminDTO {
    
    @Schema(example = "Tadeu", requiredMode = RequiredMode.REQUIRED, description = "Nome do admin")
    private String adminName;

    @Schema(example = "tadeumaffeis@gmail.com", requiredMode = RequiredMode.REQUIRED,  description = "Email do admin")
    private String email;

    @Schema(example = "12345678", requiredMode = RequiredMode.REQUIRED,  description = "Senha do admin")
    private String adminPassword;
}
