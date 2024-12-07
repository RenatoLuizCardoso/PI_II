package com.projeto_integrador.projeto_integrador.modules.subjects.controller;

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

import com.projeto_integrador.projeto_integrador.modules.subjects.dto.SubjectDTO;
import com.projeto_integrador.projeto_integrador.modules.subjects.entity.SubjectEntity;
import com.projeto_integrador.projeto_integrador.modules.subjects.repository.SubjectRepository;
import com.projeto_integrador.projeto_integrador.modules.subjects.usecases.CreateSubject;
import com.projeto_integrador.projeto_integrador.modules.subjects.usecases.DeleteSubjectById;
import com.projeto_integrador.projeto_integrador.modules.subjects.usecases.GetAllSubjects;
import com.projeto_integrador.projeto_integrador.modules.subjects.usecases.GetSubjectById;
import com.projeto_integrador.projeto_integrador.modules.subjects.usecases.PutSubjectById;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("subject")
@CrossOrigin
@Tag(name = "Subjects", description = "Gerenciamento de disciplinas")
public class SubjectController {

    @Autowired
    SubjectRepository repository;

    @Autowired
    CreateSubject createSubject;

    @Autowired
    GetAllSubjects getAllSubjects;

    @Autowired
    GetSubjectById getSubjectById;

    @Autowired
    PutSubjectById putSubjectById;

    @Autowired
    DeleteSubjectById deleteSubjectById;

    @PostMapping("/")
    @SecurityRequirement(name = "jwt_auth")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Criar uma nova disciplina", description = "Permite que o ADMIN crie uma nova disciplina.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", content = {
            @Content(schema = @Schema(implementation = SubjectDTO.class))
        }),
    })
    public ResponseEntity<Object> create(@Valid @RequestBody SubjectEntity subjectEntity) {
        try {
            var result = this.createSubject.execute(subjectEntity);
            return ResponseEntity.ok().body(result);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/")
    @SecurityRequirement(name = "jwt_auth")
    @PreAuthorize("hasRole('ADMIN') or hasRole('STUDENT') or hasRole('TEACHER')")
    @Operation(summary = "Listar todas as disciplinas", description = "Permite que ADMIN e STUDENT visualizem todas as disciplinas.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", content = {
            @Content(schema = @Schema(implementation = SubjectEntity.class))
        }),
    })
    public ResponseEntity<Object> getAllSubjects() {
        try {
            var result = this.getAllSubjects.execute();
            return ResponseEntity.ok().body(result);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    @SecurityRequirement(name = "jwt_auth")
    @PreAuthorize("hasRole('ADMIN') or hasRole('STUDENT')")
    @Operation(summary = "Obter uma disciplina pelo ID", description = "Permite que ADMIN e STUDENT busquem uma disciplina específica pelo ID.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", content = {
            @Content(schema = @Schema(implementation = SubjectEntity.class))
        }),
    })
    public ResponseEntity<Object> getById(@Valid @PathVariable long id) {
        try {
            var subject = this.getSubjectById.execute(id);
            return ResponseEntity.ok().body(subject);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    @SecurityRequirement(name = "jwt_auth")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Atualizar uma disciplina pelo ID", description = "Permite que o ADMIN atualize as informações de uma disciplina específica.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", content = {
            @Content(schema = @Schema(implementation = SubjectDTO.class))
        }),
    })
    public ResponseEntity<Object> putSubject(@Valid @RequestBody SubjectEntity subjectEntity, @PathVariable Long id) {
        try {
            var updatedSubject = this.putSubjectById.execute(id, subjectEntity);
            return ResponseEntity.ok().body(updatedSubject);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    @SecurityRequirement(name = "jwt_auth")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Deletar uma disciplina pelo ID", description = "Permite que o ADMIN exclua uma disciplina específica pelo ID.")
    public ResponseEntity<Object> deleteSubject(@Valid @PathVariable Long id) {
        try {
            this.deleteSubjectById.execute(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
