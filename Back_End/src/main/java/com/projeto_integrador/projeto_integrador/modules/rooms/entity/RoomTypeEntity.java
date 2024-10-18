package com.projeto_integrador.projeto_integrador.modules.rooms.entity;

import java.time.LocalDateTime;

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

@Entity(name = "roomType")
@Data
@Builder
@EqualsAndHashCode(of = "roomTypeId")
@AllArgsConstructor
@NoArgsConstructor
public class RoomTypeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "room_type_id")
    private Long roomTypeId;

    @NotBlank
    @Length(max = 200, message = "o máximo de caracteres do campo [roomTypeDescription] são 200")
    @Column(name = "room_type_description")
    @Schema(example = "Sala", requiredMode = RequiredMode.REQUIRED, description = "Descrições do tipo de sala")
    private String roomTypeDescription;

    @CreationTimestamp
    private LocalDateTime create_at;
    
    @UpdateTimestamp
    private LocalDateTime update_at;
    
}
