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
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@Builder
@EqualsAndHashCode(of = "roomId")
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "room")
public class RoomEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "room_id")
    private Long roomId;

    @NotBlank
    @Length(max = 11, message = "o máximo de caracteres do campo [roomCapacity] são 11")
    @Column(name = "room_capacity")
    @Schema(example = "30", requiredMode = RequiredMode.REQUIRED, description = "Capacidade da sala/labóratorio")
    private String roomCapacity;

    @NotBlank
    @Length(max = 100, message = "o máximo de caracteres do campo [roomFloor] são 100")
    @Column(name = "room_floor")
    @Schema(example = "2", requiredMode = RequiredMode.REQUIRED, description = "Andar da sala/labóratorio")
    private String roomFloor;

    @NotBlank
    @Length(max = 200, message = "o máximo de caracteres do campo [roomResources] são 200")
    @Column(name = "room_resources")
    @Schema(example = "Computadores, Televisão, Ventiladores, Quadro", requiredMode = RequiredMode.REQUIRED, description = "Recursos da sala/labóratorio")
    private String roomResources;

    @NotBlank
    @Length(max = 30, message = "o máximo de caracteres do campo [roomAvailability] são 1")
    @Column(name = "room_availability")
    @Schema(example = "Livre", requiredMode = RequiredMode.REQUIRED, description = "Capacidade da sala/labóratorio")
    private String roomAvailability;

    @NotNull
    @Column
    @Schema(requiredMode = RequiredMode.REQUIRED, description = "Tipo da sala/laboratório")
    private Long roomType;

    @CreationTimestamp
    private LocalDateTime create_at;
    
    @UpdateTimestamp
    private LocalDateTime update_at;
    

}
