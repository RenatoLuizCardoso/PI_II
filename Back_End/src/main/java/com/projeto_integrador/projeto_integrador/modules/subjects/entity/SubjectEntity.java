package com.projeto_integrador.projeto_integrador.modules.subjects.entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.validator.constraints.Length;

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
@EqualsAndHashCode(of = "subjectId")
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "subjects")
public class SubjectEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "subject_id")
    private Long subjectId;

    @NotBlank
    @Length(max = 100, message = "the field [subjectName] must have 100 characters")
    @Column(name = "subject_name")
    private String subjectName;

    @NotBlank
    @Length(max = 15, message = "the field [subjectHours] must have 15 characters")
    @Column(name = "subject_hours")
    private String subjectHours;

    @CreationTimestamp
    private LocalDateTime create_at;
    
    @UpdateTimestamp
    private LocalDateTime update_at;
    

}
