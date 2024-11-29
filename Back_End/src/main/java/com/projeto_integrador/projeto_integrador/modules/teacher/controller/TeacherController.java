package com.projeto_integrador.projeto_integrador.modules.teacher.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import java.util.UUID;

import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.projeto_integrador.projeto_integrador.modules.student.dto.ResetPasswordRequest;
import com.projeto_integrador.projeto_integrador.modules.teacher.entity.TeacherEntity;
import com.projeto_integrador.projeto_integrador.modules.teacher.repository.TeacherRepository;
import com.projeto_integrador.projeto_integrador.modules.teacher.usecases.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@RestController
@RequestMapping("teacher")
@CrossOrigin
@Tag(name = "Professor", description = "Operações relacionadas aos professores")
public class TeacherController {

    @Autowired
    private CreateTeacher createTeacher;
    @Autowired
    private GetAllTeachers getAllTeachers;
    @Autowired
    private GetTeacherById getTeacherById;
    @Autowired
    private PutTeacherById putTeacherById;
    @Autowired
    private DeleteTeacherById deleteTeacherById;
    @Autowired
    private ProfileTeacherUseCase profileTeacherUseCase;
    @Autowired
    private FileStorageService fileStorageService;
    @Autowired
    private ForgotPasswordTeacherService forgotPasswordTeacherService;
    @Autowired
    private ResetPasswordService resetPasswordService;

    @Autowired
    private TeacherRepository teacherRepository;

    private final String uploadDir = "uploads/teachers/";

    @PostMapping("/")
    @SecurityRequirement(name = "jwt_auth")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Criar um novo professor", description = "Cria um novo professor no sistema. Apenas administradores podem acessar.", security = @SecurityRequirement(name = "jwt_auth"))
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Professor criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Erro ao criar professor", content = @Content)
    })
    public ResponseEntity<Object> create(@Valid @RequestBody TeacherEntity teacherEntity) {
        try {
            var result = this.createTeacher.execute(teacherEntity);
            return ResponseEntity.ok().body(result);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/")
    @SecurityRequirement(name = "jwt_auth")
    @PreAuthorize("hasRole('ADMIN') or hasRole('STUDENT') or hasRole('TEACHER')")
    @Operation(summary = "Listar professores", description = "Lista todos os professores ou o perfil do professor autenticado. Disponível para administradores e professores.", security = @SecurityRequirement(name = "jwt_auth"))
    public ResponseEntity<?> getAllTeachers(HttpServletRequest request) {
        var admin = request.isUserInRole("ADMIN");
        var student = request.isUserInRole("STUDENT");
        try {
            if (admin || student) {
                var allTeachers = this.getAllTeachers.execute();
                return ResponseEntity.ok().body(allTeachers);
            } else {
                var teacherId = request.getAttribute("teacher_id");
                var profile = this.profileTeacherUseCase.execute(Long.parseLong(teacherId.toString()));
                return ResponseEntity.ok().body(profile);
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    @SecurityRequirement(name = "jwt_auth")
    @PreAuthorize("hasRole('ADMIN') or hasRole('STUDENT') or hasRole('TEACHER')")
    @Operation(summary = "Obter professor por ID", description = "Recupera as informações de um professor pelo ID. Disponível apenas para administradores.", security = @SecurityRequirement(name = "jwt_auth"))
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Professor encontrado"),
            @ApiResponse(responseCode = "404", description = "Professor não encontrado", content = @Content)
    })
    public ResponseEntity<Map<String, Object>> getById(
            @Parameter(description = "ID do professor") @PathVariable long id) {
        try {
            var teacherMap = this.getTeacherById.execute(id);
            return ResponseEntity.ok().body(teacherMap);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", e.getMessage()));
        }
    }

    @PutMapping("/{id}")
    @SecurityRequirement(name = "jwt_auth")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Atualizar professor", description = "Atualiza as informações de um professor existente. Disponível apenas para administradores.", security = @SecurityRequirement(name = "jwt_auth"))
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Professor atualizado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Professor não encontrado", content = @Content)
    })
    public ResponseEntity<?> putTeacher(@Valid @RequestBody TeacherEntity teacherEntity, @PathVariable Long id) {
        try {
            var updatedTeacher = this.putTeacherById.execute(id, teacherEntity);
            return ResponseEntity.ok().body(updatedTeacher);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", e.getMessage()));
        }
    }

    @DeleteMapping("/{id}")
    @SecurityRequirement(name = "jwt_auth")
    @SecurityRequirement(name = "jwt_auth")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Deletar professor", description = "Remove um professor do sistema pelo ID. Disponível apenas para administradores.", security = @SecurityRequirement(name = "jwt_auth"))
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Professor deletado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Erro ao deletar professor", content = @Content)
    })
    public ResponseEntity<Object> deleteTeacher(@PathVariable Long id) {
        try {
            this.deleteTeacherById.execute(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/{id}/photo")
    @PreAuthorize("hasRole('ADMIN') or hasRole('TEACHER')")
    public ResponseEntity<Object> uploadPhoto(
        @PathVariable Long id,
        @RequestPart("profilePhoto") MultipartFile profilePhoto) {
        try {
            String filePath = fileStorageService.saveFile(profilePhoto);
    
            profileTeacherUseCase.updatePhoto(id, filePath);
    
            return ResponseEntity.ok(Map.of("message", "Foto de perfil atualizada com sucesso!", "photoPath", filePath));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", "Professor não encontrado."));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @Operation(summary = "Solicitar redefinição de senha", description = "Gera um token para redefinição de senha do estudante")
    @PostMapping("/forgot-password")
    public ResponseEntity<String> forgotPassword(@RequestBody Map<String, String> request) {
        String email = request.get("institutionalEmail");
        try {
            forgotPasswordTeacherService.generateResetToken(email);
            return ResponseEntity.ok("Reset token sent to email");
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Teacher not found");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error processing request");
        }
    }

    @Operation(summary = "Redefinir senha", description = "Atualiza a senha do estudante usando um token de redefinição")
    @PostMapping("/reset-password")
    public ResponseEntity<String> resetPassword(@RequestBody ResetPasswordRequest request) {
        try {
            resetPasswordService.resetPassword(request.getToken(), request.getNewPassword());
            return ResponseEntity.ok("Password successfully reset.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Invalid token or password.");
        }
    }
}
