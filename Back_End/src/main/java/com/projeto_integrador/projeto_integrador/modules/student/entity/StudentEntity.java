package com.projeto_integrador.projeto_integrador.modules.student.entity;


import org.hibernate.validator.constraints.Length;

import io.swagger.v3.oas.annotations.media.Schema;
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
@EqualsAndHashCode(of = "studentId")
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "students")
@Schema(description = "Entidade que representa um estudante no sistema")
public class StudentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "student_id")
    @Schema(description = "ID único do estudante", example = "1")
    private Long studentId;

    @NotBlank
    @Length(max = 100, message = "O nome do estudante deve ter no máximo 100 caracteres")
    @Column(name = "student_name")
    @Schema(description = "Nome completo do estudante", example = "Maria Oliveira")
    private String studentName;

    @NotBlank
    @Email
    @Column(name = "institutional_email")
    @Schema(description = "Email institucional do estudante", example = "maria.oliveira@universidade.com")
    private String institutionalEmail;

    @NotBlank
    @Length(min = 8, max = 255, message = "A senha deve ter entre 8 a 255 caracteres")
    @Column(name = "student_password")
    @Schema(description = "Senha do estudante", example = "senha12345")
    private String studentPassword;
}
