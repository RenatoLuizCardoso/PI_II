package com.projeto_integrador.projeto_integrador.modules.admin.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.projeto_integrador.projeto_integrador.modules.admin.dto.AdminDTO;
import com.projeto_integrador.projeto_integrador.modules.admin.entity.AdminEntity;
import com.projeto_integrador.projeto_integrador.modules.admin.repository.AdminRepository;
import com.projeto_integrador.projeto_integrador.modules.admin.usecases.CreateAdminUseCase;
import com.projeto_integrador.projeto_integrador.modules.admin.usecases.DeleteAdminUseCase;
import com.projeto_integrador.projeto_integrador.modules.admin.usecases.GetAdminById;
import com.projeto_integrador.projeto_integrador.modules.admin.usecases.GetAllAdmins;
import com.projeto_integrador.projeto_integrador.modules.admin.usecases.PutAdminById;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/admin") 
@Tag(name = "Administrador", description = "Informações do Administrador")
public class AdminController {
    
    @Autowired
    AdminRepository repository;

    @Autowired
    CreateAdminUseCase createAdmin;

    @Autowired
    GetAllAdmins getAllAdmins;

    @Autowired
    GetAdminById getAdminById;

    @Autowired
    PutAdminById putAdminById;

    @Autowired
    DeleteAdminUseCase deleteAdminById;

    

    @PostMapping("/")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Cadastro de administrador", description = "Essa função é responsável por cadastrar um administrador")
    @ApiResponses({
      @ApiResponse(responseCode = "200", content = {
          @Content(schema = @Schema(implementation = AdminDTO.class))
      }),
      @ApiResponse(responseCode = "400", description = "Administrador já existe")
    })
    @SecurityRequirement(name = "jwt_auth")
    public ResponseEntity<Object> create(@Valid @RequestBody AdminEntity adminEntity) {
        try {
            var result = this.createAdmin.execute(adminEntity);
            return ResponseEntity.ok().body(result);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Perfil do Administrador", description = "Essa função é responsável por buscar as informações do perfil do administrador")
    @ApiResponses({
      @ApiResponse(responseCode = "200", content = {
          @Content(schema = @Schema(implementation = AdminEntity.class))
      }),
      @ApiResponse(responseCode = "400", description = "Admin not Found")
    })
    @SecurityRequirement(name = "jwt_auth")
    public ResponseEntity<Object> getAllAdmins() {
       try {
            var result = this.getAllAdmins.execute();
            return ResponseEntity.ok().body(result);
       } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
       }
    }

    
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Perfil do Administrador por ID", description = "Essa função é responsável por buscar as informações do perfil do administrador filtrado por ID")
    @ApiResponses({
      @ApiResponse(responseCode = "200", content = {
          @Content(schema = @Schema(implementation = AdminEntity.class))
      }),
      @ApiResponse(responseCode = "400", description = "Admin not found")
    })
    @SecurityRequirement(name = "jwt_auth")
    public ResponseEntity<Object> getById(@Valid @PathVariable Long id){
       try {
        var admin = this.getAdminById.execute(id);
        return ResponseEntity.ok().body(admin);
       } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
       }
        
    }

    
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Alteração do Administrador", description = "Essa função é responsável por alterar/editar as informações de um administrador por ID")
    @ApiResponses({
      @ApiResponse(responseCode = "200", content = {
          @Content(schema = @Schema(implementation = AdminDTO.class))
      }),
      @ApiResponse(responseCode = "400", description = "Admin not found")
    })
    @SecurityRequirement(name = "jwt_auth")
    public ResponseEntity<Object> putAdmin(@Valid @RequestBody AdminEntity adminEntity, @PathVariable Long id) {
        try {
            var updatedAdmin = this.putAdminById.execute(id, adminEntity);
            return ResponseEntity.ok().body(updatedAdmin);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Exclusão do Administrador", description = "Essa função é responsável por excluir um administrador por ID")
    @ApiResponses({
      @ApiResponse(responseCode = "200", content = {
      }),
      @ApiResponse(responseCode = "400", description = "Admin not found")
    })
    @SecurityRequirement(name = "jwt_auth")
    public ResponseEntity<Object> deleteAdmin(@Valid @PathVariable Long id) {
        try {
            this.deleteAdminById.execute(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    
}
