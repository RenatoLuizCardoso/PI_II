package com.projeto_integrador.projeto_integrador.modules.time.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import com.projeto_integrador.projeto_integrador.modules.time.entity.TimeEntity;
import com.projeto_integrador.projeto_integrador.modules.time.usecases.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("time")
@CrossOrigin
@Tag(name = "Hora", description = "Gerenciamento das horas")
public class TimeController {

    @Autowired
    CreateTime createTime;

    @Autowired
    GetAllTimes getAllTimes;

    @Autowired
    GetTimeById getTimeById;

    @Autowired
    PutTimeById putTimeById;

    @Autowired
    DeleteTimeById deleteTimeById;

    @PostMapping("/")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Cadastrar horário", description = "Essa função é responsável por cadastrar um novo horário")
    @ApiResponses({
        @ApiResponse(responseCode = "200", content = {
            @Content(schema = @Schema(implementation = TimeEntity.class))
        }),
        @ApiResponse(responseCode = "400", description = "Erro ao cadastrar horário")
    })
    @SecurityRequirement(name = "jwt_auth")
    public ResponseEntity<Object> create(@Valid @RequestBody TimeEntity timeEntity) {
        try {
            var result = this.createTime.execute(timeEntity);
            return ResponseEntity.ok().body(result);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Listar horários", description = "Essa função é responsável por listar todos os horários")
    @ApiResponses({
        @ApiResponse(responseCode = "200", content = {
            @Content(schema = @Schema(implementation = TimeEntity.class))
        }),
        @ApiResponse(responseCode = "400", description = "Erro ao listar horários")
    })
    @SecurityRequirement(name = "jwt_auth")
    public ResponseEntity<Object> getAllTimes() {
        try {
            var result = this.getAllTimes.execute();
            return ResponseEntity.ok().body(result);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Buscar horário por ID", description = "Essa função é responsável por buscar um horário específico pelo ID")
    @ApiResponses({
        @ApiResponse(responseCode = "200", content = {
            @Content(schema = @Schema(implementation = TimeEntity.class))
        }),
        @ApiResponse(responseCode = "404", description = "Horário não encontrado")
    })
    @SecurityRequirement(name = "jwt_auth")
    public ResponseEntity<Object> getById(@Valid @PathVariable long id) {
        try {
            var time = this.getTimeById.execute(id);
            return ResponseEntity.ok().body(time);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Atualizar horário", description = "Essa função é responsável por atualizar um horário existente")
    @ApiResponses({
        @ApiResponse(responseCode = "200", content = {
            @Content(schema = @Schema(implementation = TimeEntity.class))
        }),
        @ApiResponse(responseCode = "404", description = "Horário não encontrado")
    })
    @SecurityRequirement(name = "jwt_auth")
    public ResponseEntity<Object> putTime(@Valid @RequestBody TimeEntity timeEntity, @PathVariable Long id) {
        try {
            var updatedTime = this.putTimeById.execute(id, timeEntity);
            return ResponseEntity.ok().body(updatedTime);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Deletar horário", description = "Essa função é responsável por deletar um horário pelo ID")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Horário deletado com sucesso"),
        @ApiResponse(responseCode = "404", description = "Horário não encontrado")
    })
    @SecurityRequirement(name = "jwt_auth")
    public ResponseEntity<Object> deleteTime(@Valid @PathVariable Long id) {
        try {
            this.deleteTimeById.execute(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
