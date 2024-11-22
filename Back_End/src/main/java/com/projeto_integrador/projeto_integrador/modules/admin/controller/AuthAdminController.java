package com.projeto_integrador.projeto_integrador.modules.admin.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.projeto_integrador.projeto_integrador.modules.admin.dto.AdminDTO;
import com.projeto_integrador.projeto_integrador.modules.admin.dto.AuthAdminDTO;
import com.projeto_integrador.projeto_integrador.modules.admin.usecases.AuthAdminUseCase;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/admin")
@Tag(name = "Login Administrador", description = "Login do Administrador")
public class AuthAdminController {

    private static final Logger logger = LoggerFactory.getLogger(AuthAdminController.class);

    @Autowired
    private AuthAdminUseCase authAdminUseCase;

    @Operation(summary = "Login de administrador", description = "Essa função é responsável pela entrada do Administrador")
    @ApiResponses({
      @ApiResponse(responseCode = "200", content = {
          @Content(schema = @Schema(implementation = AdminDTO.class))
      }),
      @ApiResponse(responseCode = "400", description = "Administrador já existe")
    })
    @PostMapping("/auth")
    public ResponseEntity<Object> create(@RequestBody AuthAdminDTO authAdminDTO) {
        logger.debug("Received login request for email: {}", authAdminDTO.getAdminEmail());

        try {
            var result = authAdminUseCase.execute(authAdminDTO);
            logger.info("Authentication successful for email: {}", authAdminDTO.getAdminEmail());
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            logger.error("Authentication failed for email: {} with error: {}", authAdminDTO.getAdminEmail(), e.getMessage());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
    }
}
