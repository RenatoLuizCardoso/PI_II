package com.projeto_integrador.projeto_integrador.modules.student.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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

import com.projeto_integrador.projeto_integrador.modules.student.entity.StudentEntity;
import com.projeto_integrador.projeto_integrador.modules.student.repository.StudentRepository;
import com.projeto_integrador.projeto_integrador.modules.student.usecases.CreateStudentUseCase;
import com.projeto_integrador.projeto_integrador.modules.student.usecases.DeleteStudentUseCase;
import com.projeto_integrador.projeto_integrador.modules.student.usecases.GetAllStudents;
import com.projeto_integrador.projeto_integrador.modules.student.usecases.GetStudentById;
import com.projeto_integrador.projeto_integrador.modules.student.usecases.PutStudentById;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/student") // Ajuste aqui, adicione barra inicial
@CrossOrigin
public class StudentController {
    
    @Autowired
    StudentRepository repository;

    @Autowired
    CreateStudentUseCase createStudent;

    @Autowired
    GetAllStudents getAllStudents;

    @Autowired
    GetStudentById getStudentById;

    @Autowired
    PutStudentById putStudentById;

    @Autowired
    DeleteStudentUseCase deleteStudentById;

    

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

    
}
