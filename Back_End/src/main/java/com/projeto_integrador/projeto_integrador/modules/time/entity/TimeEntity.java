package com.projeto_integrador.projeto_integrador.modules.time.entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

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
@EqualsAndHashCode(of = "TimeId")
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "times")
public class TimeEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "time_id")
    private Long TimeId;

    @NotBlank
    @Column(name = "start_time")
    private String startTime;

    @NotBlank
    @Column(name = "end_time")
    private String endTime;

    @NotBlank
    @Column(name = "week_day")
    private String weekDay;

    @CreationTimestamp
    private LocalDateTime create_at;
    
    @UpdateTimestamp
    private LocalDateTime update_at;

}
