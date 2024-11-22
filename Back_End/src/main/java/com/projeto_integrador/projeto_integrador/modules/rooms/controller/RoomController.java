package com.projeto_integrador.projeto_integrador.modules.rooms.controller;

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

import com.projeto_integrador.projeto_integrador.modules.rooms.dto.RoomDTO;
import com.projeto_integrador.projeto_integrador.modules.rooms.entity.RoomEntity;
import com.projeto_integrador.projeto_integrador.modules.rooms.repository.RoomRepository;
import com.projeto_integrador.projeto_integrador.modules.rooms.usecases.room.CreateRoom;
import com.projeto_integrador.projeto_integrador.modules.rooms.usecases.room.DeleteRoomById;
import com.projeto_integrador.projeto_integrador.modules.rooms.usecases.room.GetAllRooms;
import com.projeto_integrador.projeto_integrador.modules.rooms.usecases.room.GetRoomById;
import com.projeto_integrador.projeto_integrador.modules.rooms.usecases.room.PutRoomById;

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
@RequestMapping("rooms")
@Tag(name = "Sala/Laborátorio", description = "Informações de Sala ou Laborátorio")
@CrossOrigin
public class RoomController {
    
    @Autowired
    RoomRepository repository;

    @Autowired
    CreateRoom createRoom;

    @Autowired
    GetAllRooms getAllRooms;

    @Autowired
    GetRoomById getRoomById;

    @Autowired
    PutRoomById putRoomById;

    @Autowired
    DeleteRoomById deleteRoomById;

    @PostMapping("/")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Cadastro de sala ou laborátorio", description = "Essa função é responsável por cadastrar uma sala ou laborátorio")
    @ApiResponses({
      @ApiResponse(responseCode = "200", content = {
          @Content(schema = @Schema(implementation = RoomDTO.class))
      }),
      @ApiResponse(responseCode = "400", description = "Sala/Lab já existe")
    })
    @SecurityRequirement(name = "jwt_auth")
    public ResponseEntity<Object> create(@Valid @RequestBody RoomEntity roomEntity) {
        try {
            var result = this.createRoom.execute(roomEntity);
            return ResponseEntity.ok().body(result);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Lista de sala e laborátorio", description = "Essa função é responsável por listar todas as salas e laborátorios")
    @ApiResponses({
      @ApiResponse(responseCode = "200", content = {
          @Content(schema = @Schema(implementation = RoomEntity.class))
      }),
      @ApiResponse(responseCode = "400", description = "Sala/Lab não existe")
    })
    @SecurityRequirement(name = "jwt_auth")
    public ResponseEntity<Object> getAllRooms() {
       try {
            var result = this.getAllRooms.execute();
            return ResponseEntity.ok().body(result);
       } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
       }
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Lista de sala ou laborátorio", description = "Essa função é responsável por listar uma sala ou laborátorio filtrada por ID")
    @ApiResponses({
      @ApiResponse(responseCode = "200", content = {
          @Content(schema = @Schema(implementation = RoomEntity.class))
      }),
      @ApiResponse(responseCode = "400", description = "Sala/Lab não existe")
    })
    @SecurityRequirement(name = "jwt_auth")
    public ResponseEntity<Map<String, Object>> getById(@PathVariable long id) {
        try {
            var roomMap = this.getRoomById.execute(id);
            return ResponseEntity.ok().body(roomMap);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", e.getMessage()));
        }
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Alterar uma sala ou laborátorio", description = "Essa função é responsável por alterar uma sala ou laborátorio")
    @ApiResponses({
      @ApiResponse(responseCode = "200", content = {
          @Content(schema = @Schema(implementation = RoomDTO.class))
      }),
      @ApiResponse(responseCode = "400", description = "Sala/Lab não existe")
    })
    @SecurityRequirement(name = "jwt_auth")
    public ResponseEntity<?> putRoom(@Valid @RequestBody RoomEntity roomEntity, @PathVariable Long id) {
        try {
            var updatedRoom = this.putRoomById.execute(id, roomEntity);
            return ResponseEntity.ok().body(updatedRoom);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", e.getMessage()));
        }
        
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Exclusão de sala ou laborátorio", description = "Essa função é responsável por excluir uma sala ou laborátorio")
    @ApiResponses({
      @ApiResponse(responseCode = "200", content = {
          @Content(schema = @Schema())
      }),
      @ApiResponse(responseCode = "400", description = "Sala/Lab não existe")
    })
    @SecurityRequirement(name = "jwt_auth")
    public ResponseEntity<Object> deleteRoom(@Valid @PathVariable Long id) {
        try {
            this.deleteRoomById.execute(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


}
