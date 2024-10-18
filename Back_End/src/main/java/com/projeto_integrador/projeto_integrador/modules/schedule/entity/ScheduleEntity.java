package com.projeto_integrador.projeto_integrador.modules.schedule.entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.RequiredMode;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
@Data
@Builder
@EqualsAndHashCode(of = "ScheduleId")
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "schedules")
public class ScheduleEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "schedule_id")
    private Long ScheduleId;

    @NotNull
    @Column(name = "teacher")
    @Schema(example = "1", requiredMode = RequiredMode.REQUIRED, description = "Professor do horário")
    private Long teacher;

    @NotNull
    @Column(name = "subject")
    @Schema(example = "1", requiredMode = RequiredMode.REQUIRED, description = "Matéria do horário")
    private Long subject;

    @NotNull
    @Column(name = "time")
    @Schema(example = "1", requiredMode = RequiredMode.REQUIRED, description = "Hora do horário")
    private Long time;

    @Column(name = "room")
    @Schema(example = "1", requiredMode = RequiredMode.REQUIRED, description = "Sala do horário")
    private Long room;

    @NotNull
    @Column(name = "course")
    @Schema(example = "1", requiredMode = RequiredMode.REQUIRED, description = "Curso do horário")
    private Long course;

    @CreationTimestamp
    private LocalDateTime create_at;
    
    @UpdateTimestamp
    private LocalDateTime update_at;
}