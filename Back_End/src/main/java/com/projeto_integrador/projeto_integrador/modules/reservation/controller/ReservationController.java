package com.projeto_integrador.projeto_integrador.modules.reservation.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.projeto_integrador.projeto_integrador.modules.reservation.dto.ReservationDTO;
import com.projeto_integrador.projeto_integrador.modules.reservation.entity.ReservationEntity;
import com.projeto_integrador.projeto_integrador.modules.reservation.repository.ReservationRepository;
import com.projeto_integrador.projeto_integrador.modules.reservation.usecases.CreateReservation;
import com.projeto_integrador.projeto_integrador.modules.reservation.usecases.DeleteReservationById;
import com.projeto_integrador.projeto_integrador.modules.reservation.usecases.GetAllReservations;
import com.projeto_integrador.projeto_integrador.modules.reservation.usecases.GetReservationById;
import com.projeto_integrador.projeto_integrador.modules.reservation.usecases.PutReservationById;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;

@RestController
@RequestMapping("reservation")
@Tag(name = "Reserva", description = "Informações da reserva")
@CrossOrigin
public class ReservationController {
    
    @Autowired
    ReservationRepository repository;

    @Autowired
    CreateReservation createReservation;

    @Autowired
    GetAllReservations getAllReservations;

    @Autowired
    GetReservationById getReservationById;

    @Autowired
    PutReservationById putReservationById;

    @Autowired
    DeleteReservationById deleteReservationById;

    @PostMapping("/")
    @SecurityRequirement(name = "jwt_auth")
    @PreAuthorize("hasRole('ADMIN') or hasRole('TEACHER')")
    @Operation(summary = "Cadastro de reserva", description = "Essa função é responsável por cadastrar uma reserva")
    @ApiResponses({
      @ApiResponse(responseCode = "200", content = {
          @Content(schema = @Schema(implementation = ReservationDTO.class))
      }),
      @ApiResponse(responseCode = "400", description = "Reserva já existe")
    })
    public ResponseEntity<Object> create(@Valid @RequestBody ReservationEntity reservationEntity) {
        try {
            var result = this.createReservation.execute(reservationEntity);
            return ResponseEntity.ok().body(result);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/")
    @SecurityRequirement(name = "jwt_auth")
    @PreAuthorize("hasRole('ADMIN') or hasRole('TEACHER') or hasRole('STUDENT')")
    @Operation(summary = "Lista de reservas", description = "Essa função é responsável por listar todas as reservas")
    @ApiResponses({
      @ApiResponse(responseCode = "200", content = {
          @Content(schema = @Schema(implementation = ReservationEntity.class))
      }),
      @ApiResponse(responseCode = "400", description = "Reserva não existe")
    })
    public ResponseEntity<?> getAllReservations() {
        try {
            List<Map<String, Object>> reservations = getAllReservations.execute();
            return ResponseEntity.ok().body(reservations);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", e.getMessage()));
        }
    }

    @GetMapping("/{id}")
    @SecurityRequirement(name = "jwt_auth")
    @PreAuthorize("hasRole('ADMIN') or hasRole('TEACHER') or hasRole('STUDENT')")
    @Operation(summary = "Lista de um reserva por ID", description = "Essa função é responsável por listar uma reserva por ID")
    @ApiResponses({
      @ApiResponse(responseCode = "200", content = {
          @Content(schema = @Schema(implementation = ReservationEntity.class))
      }),
      @ApiResponse(responseCode = "400", description = "Reserva não existe")
    })
    public ResponseEntity<Map<String, Object>> getById(@PathVariable long id) {
        try {
            var reservationMap = this.getReservationById.execute(id);
            return ResponseEntity.ok().body(reservationMap);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", e.getMessage()));
        }
    }

    @PutMapping("/{id}")
    @SecurityRequirement(name = "jwt_auth")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Alterar uma reserva", description = "Essa função é responsável por alterar uma reserva")
    @ApiResponses({
      @ApiResponse(responseCode = "200", content = {
          @Content(schema = @Schema(implementation = ReservationDTO.class))
      }),
      @ApiResponse(responseCode = "400", description = "Reserva não existe")
    })
    public ResponseEntity<?> putReservation(@Valid @RequestBody ReservationEntity reservationEntity, @PathVariable Long id) {
        try {
            var updatedReservation = this.putReservationById.execute(id, reservationEntity);
            return ResponseEntity.ok().body(updatedReservation);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", e.getMessage()));
        }
        
    }

    @DeleteMapping("/{id}")
    @SecurityRequirement(name = "jwt_auth")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Exclusão de reserva", description = "Essa função é responsável por excluir uma reserva")
    @ApiResponses({
      @ApiResponse(responseCode = "200", content = {
          @Content(schema = @Schema())
      }),
      @ApiResponse(responseCode = "400", description = "Reserva não existe")
    })
    public ResponseEntity<Object> deleteReservation(@Valid @PathVariable Long id) {
        try {
            this.deleteReservationById.execute(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        
    }


}
