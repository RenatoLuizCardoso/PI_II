package com.projeto_integrador.projeto_integrador.modules.courses.entity;

import java.time.LocalDateTime;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.validator.constraints.Length;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.RequiredMode;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@Builder
@EqualsAndHashCode(of = "courseId")
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "courses")
public class CourseEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "course_id")
    private Long courseId;

    @NotBlank
    @Length(max = 100, message = "o máximo de caracteres do campo [courseName] são 100")
    @Column(name = "course_name")
    @Schema(example = "Desenvolvimento de Sistemas", requiredMode = RequiredMode.REQUIRED, description = "Nome do curso")
    private String courseName;

    @NotBlank
    @Length(max = 10, message = "o máximo de caracteres do campo [courseSemester] são 10")
    @Column(name = "course_semester")
    @Schema(example = "1° Semestre", requiredMode = RequiredMode.REQUIRED, description = "Semestre/Ano do curso")
    private String courseSemester;

    @NotBlank
    @Length(max = 20, message = "o máximo de caracteres do campo [coursePeriod] são 20")
    @Column(name = "course_period")
    @Schema(example = "Tarde", requiredMode = RequiredMode.REQUIRED, description = "Período do curso")
    private String coursePeriod;

    @Column(name="course_subjects")
    @Schema(example = "Matérias", requiredMode = RequiredMode.REQUIRED, description = "Matérias do curso")
    private List<Long> courseSubjects;

    @CreationTimestamp
    private LocalDateTime create_at;
    
    @UpdateTimestamp
    private LocalDateTime update_at;
    

}
