package com.projeto_integrador.projeto_integrador.modules.rooms.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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
import com.projeto_integrador.projeto_integrador.modules.rooms.dto.RoomTypeDTO;
import com.projeto_integrador.projeto_integrador.modules.rooms.entity.RoomEntity;
import com.projeto_integrador.projeto_integrador.modules.rooms.entity.RoomTypeEntity;
import com.projeto_integrador.projeto_integrador.modules.rooms.repository.RoomTypeRepository;
import com.projeto_integrador.projeto_integrador.modules.rooms.usecases.roomtype.CreateRoomType;
import com.projeto_integrador.projeto_integrador.modules.rooms.usecases.roomtype.DeleteRoomTypeById;
import com.projeto_integrador.projeto_integrador.modules.rooms.usecases.roomtype.GetAllRoomTypes;
import com.projeto_integrador.projeto_integrador.modules.rooms.usecases.roomtype.GetRoomTypeById;
import com.projeto_integrador.projeto_integrador.modules.rooms.usecases.roomtype.PutRoomTypeById;

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
@RequestMapping("roomType")
@Tag(name="Tipo de Sala", description="Informações de tipo de sala")
@CrossOrigin
public class RoomTypeController {
    @Autowired
    RoomTypeRepository repository;

    @Autowired
    CreateRoomType createRoomType;

    @Autowired
    GetAllRoomTypes getAllRoomTypes;

    @Autowired
    GetRoomTypeById getRoomTypeById;

    @Autowired
    PutRoomTypeById putRoomTypeById;

    @Autowired
    DeleteRoomTypeById deleteRoomTypeById;

    @PostMapping("/")
    @Operation(summary = "Cadastro de tipo sala", description = "Essa função é responsável por cadastrar um tipo de sala")
    @ApiResponses({
      @ApiResponse(responseCode = "200", content = {
          @Content(schema = @Schema(implementation = RoomTypeDTO.class))
      }),
      @ApiResponse(responseCode = "400", description = "Tipo de Sala já existe")
    })
    @PreAuthorize("hasRole('ADMIN')")
    @SecurityRequirement(name = "jwt_auth")
    public ResponseEntity<Object> create(@Valid @RequestBody RoomTypeEntity roomTypeEntity) {
        try {
            var result = this.createRoomType.execute(roomTypeEntity);
            return ResponseEntity.ok().body(result);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/")
    @Operation(summary = "Lista de tipo de sala", description = "Essa função é responsável por listar todos os tipos de sala")
    @ApiResponses({
      @ApiResponse(responseCode = "200", content = {
          @Content(schema = @Schema(implementation = RoomTypeEntity.class))
      }),
      @ApiResponse(responseCode = "400", description = "Sala/Lab já existe")
    })
    @PreAuthorize("hasRole('ADMIN')")
    @SecurityRequirement(name = "jwt_auth")
    public ResponseEntity<List<RoomTypeEntity>> getAllRoomTypes() {
       try {
            var result = this.getAllRoomTypes.execute();
            return ResponseEntity.ok().body(result);
       } catch (Exception e) {
            throw new EntityNotFoundException("RoomType not Registered");
       }
    }

    @GetMapping("/{id}")
    @Operation(summary = "Lista de tipo de sala por ID", description = "Essa função é responsável por listar um tipo de sala por ID")
    @ApiResponses({
      @ApiResponse(responseCode = "200", content = {
          @Content(schema = @Schema(implementation = RoomEntity.class))
      }),
      @ApiResponse(responseCode = "400", description = "Tipo de sala não existe")
    })
    @PreAuthorize("hasRole('ADMIN')")
    @SecurityRequirement(name = "jwt_auth")
    public ResponseEntity<RoomTypeEntity> getById(@Valid @PathVariable long id){
       try {
        var room = this.getRoomTypeById.execute(id);
        return ResponseEntity.ok().body(room);
       } catch (Exception e) {
            throw new EntityNotFoundException("RoomType not found");
       }
        
    }

    @PutMapping("/{id}")
    @Operation(summary = "Alterar um tipo de sala", description = "Essa função é responsável por alterar um tipo de sala")
    @ApiResponses({
      @ApiResponse(responseCode = "200", content = {
          @Content(schema = @Schema(implementation = RoomDTO.class))
      }),
      @ApiResponse(responseCode = "400", description = "Sala/Lab não existe")
    })
    @PreAuthorize("hasRole('ADMIN')")
    @SecurityRequirement(name = "jwt_auth")
    public ResponseEntity<RoomTypeEntity> putRoomType(@Valid @RequestBody RoomTypeEntity roomTypeEntity, @PathVariable Long id) {
        try {
            var updatedRoomType = this.putRoomTypeById.execute(id, roomTypeEntity);
            return ResponseEntity.ok().body(updatedRoomType);
        } catch (Exception e) {
            throw new EntityNotFoundException("RoomType not found");
        }
        
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Exclusão de tipo de sala", description = "Essa função é responsável por excluir um tipo de sala")
    @ApiResponses({
      @ApiResponse(responseCode = "200", content = {
          @Content(schema = @Schema(implementation = RoomDTO.class))
      }),
      @ApiResponse(responseCode = "400", description = "Sala/Lab não existe")
    })
    @PreAuthorize("hasRole('ADMIN')")
    @SecurityRequirement(name = "jwt_auth")
    public ResponseEntity<Void> deleteRoomType(@Valid @PathVariable Long id) {
        this.deleteRoomTypeById.execute(id);
        return ResponseEntity.ok().build();
    }


}