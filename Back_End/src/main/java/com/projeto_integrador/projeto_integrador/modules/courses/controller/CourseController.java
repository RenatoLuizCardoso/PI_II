package com.projeto_integrador.projeto_integrador.modules.courses.controller;

import java.util.List;
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

import com.projeto_integrador.projeto_integrador.modules.courses.dto.CourseDTO;
import com.projeto_integrador.projeto_integrador.modules.courses.entity.CourseEntity;
import com.projeto_integrador.projeto_integrador.modules.courses.usecases.CreateCourse;
import com.projeto_integrador.projeto_integrador.modules.courses.usecases.DeleteCourseById;
import com.projeto_integrador.projeto_integrador.modules.courses.usecases.GetAllCourses;
import com.projeto_integrador.projeto_integrador.modules.courses.usecases.GetCourseById;
import com.projeto_integrador.projeto_integrador.modules.courses.usecases.PutCourseById;

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
@RequestMapping("course")
@Tag(name = "Curso", description = "Informações de Curso")
@CrossOrigin
public class CourseController {
    
    @Autowired
    CreateCourse createCourse;

    @Autowired
    GetAllCourses getAllCourses;

    @Autowired
    GetCourseById getCourseById;

    @Autowired
    PutCourseById putCourseById;

    @Autowired
    DeleteCourseById deleteCourseById;

    @PostMapping("/")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Cadastro de curso", description = "Essa função é responsável por cadastrar um curso")
    @ApiResponses({
      @ApiResponse(responseCode = "200", content = {
          @Content(schema = @Schema(implementation = CourseDTO.class))
      }),
      @ApiResponse(responseCode = "400", description = "Curso já existe")
    })
    @SecurityRequirement(name = "jwt_auth")
    public ResponseEntity<Object> create(@Valid @RequestBody CourseEntity courseEntity) {
        try {
            var result = this.createCourse.execute(courseEntity);
            return ResponseEntity.ok().body(result);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/")
    @PreAuthorize("hasRole('ADMIN') or hasRole('STUDENT')")
    @Operation(summary = "Listar cursos", description = "Essa função é responsável por listar todos os cursos")
    @ApiResponses({
      @ApiResponse(responseCode = "200", content = {
          @Content(schema = @Schema(implementation = CourseEntity.class))
      }),
      @ApiResponse(responseCode = "400", description = "Curso não existe")
    })
    @SecurityRequirement(name = "jwt_auth")
    public ResponseEntity<?> getAllCourses() {
        try {
            List<Map<String, Object>> courses = getAllCourses.execute();
            return ResponseEntity.ok().body(courses);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", e.getMessage()));
        }
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('STUDENT')")
    @Operation(summary = "Listar curso por ID", description = "Essa função é responsável por listar um curso filtrado por ID")
    @ApiResponses({
      @ApiResponse(responseCode = "200", content = {
          @Content(schema = @Schema(implementation = CourseEntity.class))
      }),
      @ApiResponse(responseCode = "400", description = "Curso não existe")
    })
    @SecurityRequirement(name = "jwt_auth")
    public ResponseEntity<Map<String, Object>> getById(@PathVariable long id) {
        try {
            var courseMap = this.getCourseById.execute(id);
            return ResponseEntity.ok().body(courseMap);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", e.getMessage()));
        }
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Alterar um curso", description = "Essa função é responsável por alterar/editar um curso")
    @ApiResponses({
      @ApiResponse(responseCode = "200", content = {
          @Content(schema = @Schema(implementation = CourseEntity.class))
      }),
      @ApiResponse(responseCode = "400", description = "Curso não existe")
    })
    @SecurityRequirement(name = "jwt_auth")
    public ResponseEntity<?> putCourse(@Valid @RequestBody CourseEntity courseEntity, @PathVariable Long id) {
        try {
            var updatedCourse = this.putCourseById.execute(id, courseEntity);
            return ResponseEntity.ok().body(updatedCourse);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", e.getMessage()));
        }
    }


    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Exclusão de um curso", description = "Essa função é responsável pela exclusão de um curso")
    @ApiResponses({
      @ApiResponse(responseCode = "200", content = {
      }),
      @ApiResponse(responseCode = "400", description = "Curso não existe")
    })
    @SecurityRequirement(name = "jwt_auth")
    public ResponseEntity<Object> deleteCourse(@Valid @PathVariable Long id) {

        try {
            this.deleteCourseById.execute(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


}
