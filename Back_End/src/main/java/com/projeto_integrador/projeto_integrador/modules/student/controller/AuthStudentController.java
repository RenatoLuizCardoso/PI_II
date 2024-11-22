package com.projeto_integrador.projeto_integrador.modules.student.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;

import com.projeto_integrador.projeto_integrador.modules.student.dto.AuthStudentRequestDTO;
import com.projeto_integrador.projeto_integrador.modules.student.usecases.AuthStudent;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;


@RestController
@RequestMapping("/student")
@Tag(name = "Login estudante", description = "Login para estudante")
public class AuthStudentController {

    @Autowired
    private AuthStudent authStudent;

    @Operation(
        summary = "Essa função é responsável pela entrada do Administrador",
        description = "Gera um token baseado no email e senha do estudante, no qual permite com que ele acesse suas ações autorizadas.",
        tags = { "Login estudante" }
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Authentication successful", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = String.class))
        }),
        @ApiResponse(responseCode = "401", description = "Authentication failed", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = String.class))
        })
    })
    @PostMapping("/auth")
    public ResponseEntity<Object> auth(@RequestBody AuthStudentRequestDTO authStudentRequestDTO) {
        try {
            var token = this.authStudent.execute(authStudentRequestDTO);
            return ResponseEntity.ok().body(token);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
    }
}