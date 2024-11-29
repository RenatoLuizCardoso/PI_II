package com.projeto_integrador.projeto_integrador.modules.student.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
import org.springframework.web.bind.annotation.CrossOrigin;

import com.projeto_integrador.projeto_integrador.modules.student.dto.ResetPasswordRequest;
import com.projeto_integrador.projeto_integrador.modules.student.entity.StudentEntity;
import com.projeto_integrador.projeto_integrador.modules.student.usecases.CreateStudentUseCase;
import com.projeto_integrador.projeto_integrador.modules.student.usecases.DeleteStudentUseCase;
import com.projeto_integrador.projeto_integrador.modules.student.usecases.ForgotPasswordStudentService;
import com.projeto_integrador.projeto_integrador.modules.student.usecases.GetAllStudents;
import com.projeto_integrador.projeto_integrador.modules.student.usecases.GetStudentById;
import com.projeto_integrador.projeto_integrador.modules.student.usecases.ProfileStudentUseCase;
import com.projeto_integrador.projeto_integrador.modules.student.usecases.PutStudentById;
import com.projeto_integrador.projeto_integrador.modules.student.usecases.ResetPasswordStudentService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/student")
@CrossOrigin
@Tag(name = "Estudante", description = "Gerenciamento de estudantes no sistema")
public class StudentController {

    @Autowired
    private CreateStudentUseCase createStudent;

    @Autowired
    private GetAllStudents getAllStudents;

    @Autowired
    private GetStudentById getStudentById;

    @Autowired
    private PutStudentById putStudentById;

    @Autowired
    private DeleteStudentUseCase deleteStudentById;

    @Autowired
    private ForgotPasswordStudentService forgotPasswordService;

    @Autowired
    private ResetPasswordStudentService resetPasswordService;

    @Autowired
    private ProfileStudentUseCase profileStudentUseCase;

    @Operation(summary = "Criar um novo estudante", description = "Endpoint para criação de estudantes no sistema")
    @PostMapping("/")
    @SecurityRequirement(name = "jwt_auth")
    public ResponseEntity<Object> create(@Valid @RequestBody StudentEntity studentEntity) {
        try {
            var result = this.createStudent.execute(studentEntity);
            return ResponseEntity.ok().body(result);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @Operation(summary = "Listar todos os estudantes", description = "Retorna todos os estudantes registrados (somente administradores)")
    @GetMapping("/")
    @SecurityRequirement(name = "jwt_auth")
    @PreAuthorize("hasRole('STUDENT') or hasRole('ADMIN')")
    public ResponseEntity<Object> getAllStudents(HttpServletRequest request) {
        try {
            if (request.isUserInRole("ADMIN")) {
                var allStudents = this.getAllStudents.execute();
                return ResponseEntity.ok().body(allStudents);
            } else if (request.isUserInRole("STUDENT")) {
                var idStudent = request.getAttribute("student_id");
                if (idStudent != null) {
                    var profile = this.profileStudentUseCase.execute(Long.parseLong(idStudent.toString()));
                    return ResponseEntity.ok().body(profile);
                } else {
                    return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Student ID not found in request.");
                }
            } else {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Access denied.");
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }

    @Operation(summary = "Buscar estudante por ID", description = "Retorna os dados de um estudante específico pelo ID")
    @GetMapping("/{id}")
    @SecurityRequirement(name = "jwt_auth")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Object> getById(@Valid @PathVariable Long id) {
        try {
            var student = this.getStudentById.execute(id);
            return ResponseEntity.ok().body(student);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @Operation(summary = "Atualizar dados do estudante", description = "Atualiza informações de um estudante pelo ID")
    @PutMapping("/{id}")
    @SecurityRequirement(name = "jwt_auth")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Object> putStudent(@Valid @RequestBody StudentEntity studentEntity, @PathVariable Long id) {
        try {
            var updatedStudent = this.putStudentById.execute(id, studentEntity);
            return ResponseEntity.ok().body(updatedStudent);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @Operation(summary = "Deletar estudante", description = "Remove um estudante do sistema pelo ID")
    @DeleteMapping("/{id}")
    @SecurityRequirement(name = "jwt_auth")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Object> deleteStudent(@Valid @PathVariable Long id) {
        try {
            this.deleteStudentById.execute(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @Operation(summary = "Solicitar redefinição de senha", description = "Gera um token para redefinição de senha do estudante")
    @PostMapping("/forgot-password")
    public ResponseEntity<String> forgotPassword(@RequestBody Map<String, String> request) {
        String email = request.get("institutionalEmail");
        try {
            forgotPasswordService.generateResetToken(email);
            return ResponseEntity.ok("Reset token sent to email");
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Student not found");
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
