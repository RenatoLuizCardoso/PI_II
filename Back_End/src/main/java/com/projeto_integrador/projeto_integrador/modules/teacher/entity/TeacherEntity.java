package com.projeto_integrador.projeto_integrador.modules.teacher.entity;

import java.time.LocalDateTime;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.validator.constraints.Length;

import com.projeto_integrador.projeto_integrador.modules.subjects.entity.SubjectEntity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Data
@Builder
@EqualsAndHashCode(of = "teacherId")
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "teachers")
@Schema(description = "Entidade que representa um professor no sistema")
public class TeacherEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "teacher_id")
    @Schema(description = "ID único do professor", example = "1")
    private Long teacherId;

    @NotBlank
    @Column(name = "teacher_name")
    @Schema(description = "Nome completo do professor", example = "João Silva")
    private String teacherName;

    @NotBlank
    @Email(message = "O campo (institutional_email) deve ter um email institucional válido")
    @Column(name = "institutional_email")
    @Schema(description = "Email institucional do professor", example = "joao.silva@universidade.com")
    private String institutionalEmail;

    @NotBlank
    @Email(message = "O campo (personal_email) deve ter um email pessoal válido")
    @Column(name = "personal_email")
    @Schema(description = "Email pessoal do professor", example = "joao.silva@gmail.com")
    private String personalEmail;

    @NotBlank
    @Length(min = 8, max = 255, message = "Deve ter entre 8 a 255 caracteres")
    @Column(name = "teacher_password")
    @Schema(description = "Senha do professor (criptografada)", example = "senha12345")
    private String teacherPassword;

    @NotBlank
    @Length(max = 15, message = "O campo [personalPhone] deve ter até 15 caracteres")
    @Column(name = "personal_phone")
    @Schema(description = "Telefone pessoal do professor", example = "(11) 98765-4321")
    private String personalPhone;

    @NotBlank
    @Length(max = 15, message = "O campo [businessPhone] deve ter até 15 caracteres")
    @Column(name = "business_phone")
    @Schema(description = "Telefone comercial do professor", example = "(11) 91234-5678")
    private String businessPhone;

    @NotBlank
    @Length(max = 50, message = "O campo [researchLine] deve ter até 50 caracteres")
    @Column(name = "research_line")
    @Schema(description = "Linha de pesquisa do professor", example = "Inteligência Artificial")
    private String researchLine;

    @NotBlank
    @Length(max = 50, message = "O campo [teacherArea] deve ter até 50 caracteres")
    @Column(name = "teacher_area")
    @Schema(description = "Área de atuação do professor", example = "Engenharia de Software")
    private String teacherArea;

    @ManyToMany
    @JoinTable(
        name = "teacher_subjects",
        joinColumns = @JoinColumn(name = "teacher_id"), 
        inverseJoinColumns = @JoinColumn(name = "subject_id")
    )
    @Schema(example = "[{\"subjectId\": 1}, {\"subjectId\": 2}]", description = "Lista de disciplinas associadas ao professor")
    private List<SubjectEntity> teacherSubjects;

    @Column(name = "profile_photo")
    @Schema(description = "Caminho da foto de perfil do professor", example = "/uploads/teacher_photos/1_profile.jpg")
    private String profilePhoto;

    @CreationTimestamp
    @Schema(description = "Data e hora de criação do registro", example = "2024-01-01T12:00:00")
    private LocalDateTime create_at;
    
    @UpdateTimestamp
    @Schema(description = "Data e hora da última atualização do registro", example = "2024-01-02T15:00:00")
    private LocalDateTime update_at;
}
