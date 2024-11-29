package com.projeto_integrador.projeto_integrador.modules.teacher.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.projeto_integrador.projeto_integrador.modules.teacher.dto.AuthTeacherRequestDTO;
import com.projeto_integrador.projeto_integrador.modules.teacher.usecases.AuthTeacher;

@RestController
@RequestMapping("/teacher")
@Tag(name = "Login Professor", description = "API responsável para a entrada de professor")
public class AuthTeacherController {

    @Autowired
    private AuthTeacher authTeacher;

    @Operation(summary = "Login Professor", description = "Gera um token baseado no email e senha do estudante, no qual permite com que ele acesse suas ações autorizadas.", tags = {
            "Login Professor" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Authentication successful", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = String.class))
            }),
            @ApiResponse(responseCode = "401", description = "Authentication failed", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = String.class))
            })
    })
    @PostMapping("/auth")
    public ResponseEntity<Object> auth(@RequestBody AuthTeacherRequestDTO authTeacherRequestDTO) {
        try {
            var token = this.authTeacher.execute(authTeacherRequestDTO);
            return ResponseEntity.ok().body(token);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
    }
}
