package com.projeto_integrador.projeto_integrador.modules.time.entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.RequiredMode;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Data
@Builder
@EqualsAndHashCode(of = "timeId")
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "times")
public class TimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "time_id")
    @Schema(example = "1", requiredMode = RequiredMode.REQUIRED, description = "ID do horário")
    private Long timeId;

    @NotBlank
    @Column(name = "start_time")
    @Schema(example = "08:00", requiredMode = RequiredMode.REQUIRED, description = "Hora de início")
    private String startTime;

    @NotBlank
    @Column(name = "end_time")
    @Schema(example = "12:00", requiredMode = RequiredMode.REQUIRED, description = "Hora de término")
    private String endTime;

    @CreationTimestamp
    @Schema(example = "2024-01-01T08:00:00", description = "Data de criação")
    private LocalDateTime create_at;

    @UpdateTimestamp
    @Schema(example = "2024-01-02T12:00:00", description = "Data de última atualização")
    private LocalDateTime update_at;
}
