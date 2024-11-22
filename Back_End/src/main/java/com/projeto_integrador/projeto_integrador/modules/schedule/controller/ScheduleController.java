package com.projeto_integrador.projeto_integrador.modules.schedule.controller;

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

import com.projeto_integrador.projeto_integrador.modules.schedule.dto.ScheduleDTO;
import com.projeto_integrador.projeto_integrador.modules.schedule.entity.ScheduleEntity;
import com.projeto_integrador.projeto_integrador.modules.schedule.repository.ScheduleRepository;
import com.projeto_integrador.projeto_integrador.modules.schedule.usecases.CreateSchedule;
import com.projeto_integrador.projeto_integrador.modules.schedule.usecases.DeleteScheduleById;
import com.projeto_integrador.projeto_integrador.modules.schedule.usecases.GetAllSchedules;
import com.projeto_integrador.projeto_integrador.modules.schedule.usecases.GetScheduleById;
import com.projeto_integrador.projeto_integrador.modules.schedule.usecases.PutScheduleById;

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
@RequestMapping("schedule")
@Tag(name = "Horário", description = "Informações do Horário")
@CrossOrigin
public class ScheduleController {
    
    @Autowired
    ScheduleRepository repository;

    @Autowired
    CreateSchedule createSchedule;

    @Autowired
    GetAllSchedules getAllSchedules;

    @Autowired
    GetScheduleById getScheduleById;

    @Autowired
    PutScheduleById putScheduleById;

    @Autowired
    DeleteScheduleById deleteScheduleById;

    @PostMapping("/")
    @SecurityRequirement(name = "jwt_auth")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Cadastro de horário", description = "Essa função é responsável por cadastrar um horário")
    @ApiResponses({
      @ApiResponse(responseCode = "200", content = {
          @Content(schema = @Schema(implementation = ScheduleDTO.class))
      }),
      @ApiResponse(responseCode = "400", description = "Horário já existe")
    })
    public ResponseEntity<Object> create(@Valid @RequestBody ScheduleEntity scheduleEntity) {
        try {
            var result = this.createSchedule.execute(scheduleEntity);
            return ResponseEntity.ok().body(result);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/")
    @SecurityRequirement(name = "jwt_auth")
    @PreAuthorize("hasRole('ADMIN') or hasRole('TEACHER') or hasRole('STUDENT')")
    @Operation(summary = "Lista de horário", description = "Essa função é responsável por listar todos os horários")
    @ApiResponses({
      @ApiResponse(responseCode = "200", content = {
          @Content(schema = @Schema(implementation = ScheduleEntity.class))
      }),
      @ApiResponse(responseCode = "400", description = "Horário não existe")
    })
    public ResponseEntity<?> getAllSchedules() {
        try {
            List<Map<String, Object>> schedules = getAllSchedules.execute();
            return ResponseEntity.ok().body(schedules);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", e.getMessage()));
        }
    }

    @GetMapping("/{id}")
    @SecurityRequirement(name = "jwt_auth")
    @PreAuthorize("hasRole('ADMIN') or hasRole('TEACHER') or hasRole('STUDENT')")
    @Operation(summary = "Lista de um horário por ID", description = "Essa função é responsável por listar um horário por ID")
    @ApiResponses({
      @ApiResponse(responseCode = "200", content = {
          @Content(schema = @Schema(implementation = ScheduleEntity.class))
      }),
      @ApiResponse(responseCode = "400", description = "Horário não existe")
    })
    public ResponseEntity<Map<String, Object>> getById(@PathVariable long id) {
        try {
            var scheduleMap = this.getScheduleById.execute(id);
            return ResponseEntity.ok().body(scheduleMap);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", e.getMessage()));
        }
    }

    @PutMapping("/{id}")
    @SecurityRequirement(name = "jwt_auth")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Alterar um horário", description = "Essa função é responsável por alterar um horário")
    @ApiResponses({
      @ApiResponse(responseCode = "200", content = {
          @Content(schema = @Schema(implementation = ScheduleDTO.class))
      }),
      @ApiResponse(responseCode = "400", description = "Horário não existe")
    })
    public ResponseEntity<?> putSchedule(@Valid @RequestBody ScheduleEntity ScheduleEntity, @PathVariable Long id) {
        try {
            var updatedSchedule = this.putScheduleById.execute(id, ScheduleEntity);
            return ResponseEntity.ok().body(updatedSchedule);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", e.getMessage()));
        }
        
    }

    @DeleteMapping("/{id}")
    @SecurityRequirement(name = "jwt_auth")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Exclusão de horário", description = "Essa função é responsável por excluir um horário")
    @ApiResponses({
      @ApiResponse(responseCode = "200", content = {
          @Content(schema = @Schema())
      }),
      @ApiResponse(responseCode = "400", description = "Horário não existe")
    })
    public ResponseEntity<Object> deleteSchedule(@Valid @PathVariable Long id) {
        try {
            this.deleteScheduleById.execute(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


}
