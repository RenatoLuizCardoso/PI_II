package com.projeto_integrador.projeto_integrador.modules.teacher.entity;

import java.time.LocalDateTime;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.validator.constraints.Length;

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
@EqualsAndHashCode(of = "TeacherId")
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "teachers")
public class TeacherEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "teacher_id")
    private Long TeacherId;

    @NotBlank
    @Column(name = "teacher_name")
    private String teacherName;

    @NotBlank
    @Email(message = "The field (institutional) must have a valid institutional email")
    @Column(name = "institutional_email")
    private String institutionalEmail;

    @NotBlank
    @Email(message = "The field (personal_email) must have a valid personal email")
    @Column(name = "personal_email")
    private String personalEmail;

    @NotBlank
    @Length(min = 8, max = 255, message = "must have between 8 to 255 characters")
    @Column(name = "teacher_password")
    private String teacherPassword;

    @NotBlank
    @Length(max = 15, message = "the field [personalPhone] must have between 15 characters")
    @Column(name = "personal_phone")
    private String personalPhone;

    @NotBlank
    @Length(max = 15, message = "the field [businessPhone] must have between 15 characters")
    @Column(name = "business_phone")
    private String businessPhone;

    @NotBlank
    @Length(max = 50, message = "the field [researchLine] must have between 50 characters")
    @Column(name = "research_line")
    private String researchLine;

    @NotBlank
    @Length(max = 50, message = "the field [teacherArea] must have between 50 characters")
    @Column(name = "teacher_area")
    private String teacherArea;

    @Column(name="teacher_subjects")
    private List<Long> teacherSubjects;

    @CreationTimestamp
    private LocalDateTime create_at;
    
    @UpdateTimestamp
    private LocalDateTime update_at;
    

}
