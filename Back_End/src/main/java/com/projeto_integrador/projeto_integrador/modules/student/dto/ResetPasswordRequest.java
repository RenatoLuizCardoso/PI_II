package com.projeto_integrador.projeto_integrador.modules.student.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ResetPasswordRequest {

    @NotBlank(message = "Token is required")
    private String token;

    @NotBlank(message = "New password is required")
    private String newPassword;

    @NotBlank(message = "Password confirmation is required")
    private String confirmPassword;
}