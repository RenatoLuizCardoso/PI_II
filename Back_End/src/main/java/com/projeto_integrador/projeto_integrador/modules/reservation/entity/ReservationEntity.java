package com.projeto_integrador.projeto_integrador.modules.reservation.entity;

import java.sql.Date;
import java.time.LocalDate;
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
@EqualsAndHashCode(of = "reservationId")
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "reservations")
public class ReservationEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reservation_id")
    private Long reservationId;

    @NotNull
    @Column(name = "teacher")
    @Schema(example = "1", requiredMode = RequiredMode.REQUIRED, description = "Professor da reserva")
    private Long teacher;

    @NotNull
    @Column(name = "subject")
    @Schema(example = "1", requiredMode = RequiredMode.REQUIRED, description = "Matéria da reserva")
    private Long subject;

    @NotNull
    @Column(name = "time")
    @Schema(example = "1", requiredMode = RequiredMode.REQUIRED, description = "Horario da reserva")
    private Long time;

    @NotNull
    @Column(name = "date")
    @Schema(example = "2024-10-17", requiredMode = RequiredMode.REQUIRED, description = "Data da reserva")
    private LocalDate date;

    @NotNull
    @Column(name = "room")
    @Schema(example = "1", requiredMode = RequiredMode.REQUIRED, description = "Sala da reserva")
    private Long room;

    @NotNull
    @Column(name = "course")
    @Schema(example = "1", requiredMode = RequiredMode.REQUIRED, description = "Curso da reserva")
    private Long course;

    @CreationTimestamp
    private LocalDateTime create_at;
    
    @UpdateTimestamp
    private LocalDateTime update_at;
}