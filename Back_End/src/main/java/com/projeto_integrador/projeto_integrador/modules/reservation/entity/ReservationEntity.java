package com.projeto_integrador.projeto_integrador.modules.reservation.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.projeto_integrador.projeto_integrador.modules.courses.entity.CourseEntity;
import com.projeto_integrador.projeto_integrador.modules.rooms.entity.RoomEntity;
import com.projeto_integrador.projeto_integrador.modules.subjects.entity.SubjectEntity;
import com.projeto_integrador.projeto_integrador.modules.teacher.entity.TeacherEntity;
import com.projeto_integrador.projeto_integrador.modules.time.entity.TimeEntity;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.RequiredMode;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
    @ManyToOne
    @JoinColumn(name = "teacher", referencedColumnName = "teacher_id", nullable = false)
    @Schema(example = "1", requiredMode = RequiredMode.REQUIRED, description = "Professor da reserva")
    private TeacherEntity teacher;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "subject", referencedColumnName = "subject_id", nullable = false)
    @Schema(example = "1", requiredMode = RequiredMode.REQUIRED, description = "Matéria da reserva")
    private SubjectEntity subject;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "time", referencedColumnName = "time_id", nullable = false)
    @Schema(example = "1", requiredMode = RequiredMode.REQUIRED, description = "Horario da reserva")
    private TimeEntity time;

    @NotNull
    @Column(name = "date")
    @Schema(example = "2024-10-17", requiredMode = RequiredMode.REQUIRED, description = "Data da reserva")
    private LocalDate date;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "room", referencedColumnName = "room_id", nullable = false)
    @Schema(example = "1", requiredMode = RequiredMode.REQUIRED, description = "Sala da reserva")
    private RoomEntity room;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "course", referencedColumnName = "course_id", nullable = false)
    @Schema(example = "1", requiredMode = RequiredMode.REQUIRED, description = "Curso da reserva")
    private CourseEntity course;

    @CreationTimestamp
    private LocalDateTime create_at;
    
    @UpdateTimestamp
    private LocalDateTime update_at;
}