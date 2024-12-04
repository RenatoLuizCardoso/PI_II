package com.projeto_integrador.projeto_integrador.modules.schedule.entity;

import java.time.LocalDateTime;

import javax.swing.plaf.TreeUI;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.projeto_integrador.projeto_integrador.modules.courses.entity.CourseEntity;
import com.projeto_integrador.projeto_integrador.modules.rooms.entity.RoomEntity;
import com.projeto_integrador.projeto_integrador.modules.subjects.entity.SubjectEntity;
import com.projeto_integrador.projeto_integrador.modules.teacher.entity.TeacherEntity;
import com.projeto_integrador.projeto_integrador.modules.time.entity.TimeEntity;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.RequiredMode;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@Builder
@EqualsAndHashCode(of = "scheduleId")
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "schedules")
public class ScheduleEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "schedule_id")
    private Long scheduleId;

    @ManyToOne
    @JoinColumn(name = "teacher_id", nullable = false)
    @Schema(example = "1", requiredMode = RequiredMode.REQUIRED, description = "Professor do horário")
    private TeacherEntity teacher;

    @ManyToOne
    @JoinColumn(name = "subject_id", nullable = false)
    @Schema(example = "1", requiredMode = RequiredMode.REQUIRED, description = "Matéria do horário")
    private SubjectEntity subject;

    @ManyToOne
    @JoinColumn(name = "time_id", nullable = false)
    @Schema(example = "1", requiredMode = RequiredMode.REQUIRED, description = "Hora do horário")
    private TimeEntity time;

    @ManyToOne
    @JoinColumn(name = "room_id", nullable = false)
    @Schema(example = "1", requiredMode = RequiredMode.REQUIRED, description = "Sala do horário")
    private RoomEntity room;

    @ManyToOne
    @JoinColumn(name = "course_id", nullable = true)
    @Schema(example = "1", requiredMode = RequiredMode.REQUIRED, description = "Curso do horário")
    private CourseEntity course;

    @Column(name = "weekday", nullable = false)
    @Schema(example = "Segunda-Feira", requiredMode = RequiredMode.REQUIRED, description = "Dia da semana")
    private String weekDay;

    @CreationTimestamp
    private LocalDateTime createAt;

    @UpdateTimestamp
    private LocalDateTime updateAt;
}
