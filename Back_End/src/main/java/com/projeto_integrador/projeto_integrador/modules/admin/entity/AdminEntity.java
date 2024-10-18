package com.projeto_integrador.projeto_integrador.modules.admin.entity;

import org.hibernate.validator.constraints.Length;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.RequiredMode;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@Builder
@EqualsAndHashCode(of = "adminId")
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "admin")
public class AdminEntity {
    
    @Id
    @Column(name = "admin_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long adminId;

    @NotBlank
    @Length(max = 100, message = "must have 100 characters")
    @Column(name = "admin_name")
    @Schema(example = "Tadeu", requiredMode = RequiredMode.REQUIRED, description = "Nome do admin")
    private String adminName;
   
    @NotBlank
    @Email
    @Column(name = "admin_email")
    @Schema(example = "tadeumaffeis@gmail.com", requiredMode = RequiredMode.REQUIRED,  description = "Email do admin")
    private String adminEmail;

    @NotBlank
    @Length(min = 8, max = 100, message = "must have between 8 to 100 characters")
    @Column(name = "admin_password")
    @Schema(example = "12345678", requiredMode = RequiredMode.REQUIRED,  description = "Senha do admin")
    private String adminPassword;
}
