package com.projeto_integrador.projeto_integrador.modules.student.controller;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
import com.projeto_integrador.projeto_integrador.modules.student.repository.StudentRepository;
import com.projeto_integrador.projeto_integrador.modules.student.usecases.CreateStudentUseCase;
import com.projeto_integrador.projeto_integrador.modules.student.usecases.DeleteStudentUseCase;
import com.projeto_integrador.projeto_integrador.modules.student.usecases.ForgotPasswordService;
import com.projeto_integrador.projeto_integrador.modules.student.usecases.GetAllStudents;
import com.projeto_integrador.projeto_integrador.modules.student.usecases.GetStudentById;
import com.projeto_integrador.projeto_integrador.modules.student.usecases.PutStudentById;
import com.projeto_integrador.projeto_integrador.modules.student.usecases.ResetPasswordService;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/student") // Ajuste aqui, adicione barra inicial
@CrossOrigin
public class StudentController {
    
    @Autowired
    private StudentRepository studentRepository;

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
    private ForgotPasswordService forgotPasswordService;

    @Autowired
    private ResetPasswordService resetPasswordService;

    @PostMapping("/")
    public ResponseEntity<Object> create(@Valid @RequestBody StudentEntity studentEntity) {
        try {
            var result = this.createStudent.execute(studentEntity);
            return ResponseEntity.ok().body(result);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/")
    public ResponseEntity<List<StudentEntity>> getAllStudents() {
       try {
            var result = this.getAllStudents.execute();
            return ResponseEntity.ok().body(result);
       } catch (Exception e) {
            throw new EntityNotFoundException("Student not Register");
       }
    }

    @GetMapping("/{id}")
    public ResponseEntity<StudentEntity> getById(@Valid @PathVariable Long id){
       try {
        var student = this.getStudentById.execute(id);
        return ResponseEntity.ok().body(student);
       } catch (Exception e) {
            throw new EntityNotFoundException("Student not found");
       }
        
    }

    @PutMapping("/{id}")
    public ResponseEntity<StudentEntity> putStudent(@Valid @RequestBody StudentEntity studentEntity, @PathVariable Long id) {
        try {
            var updatedStudent = this.putStudentById.execute(id, studentEntity);
            return ResponseEntity.ok().body(updatedStudent);
        } catch (Exception e) {
            throw new EntityNotFoundException("Student not found");
        }
        
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStudent(@Valid @PathVariable Long id) {
        this.deleteStudentById.execute(id);
        return ResponseEntity.ok().build();
    }

    private static final Logger logger = LoggerFactory.getLogger(StudentController.class);
    
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
