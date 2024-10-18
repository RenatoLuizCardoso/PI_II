package com.projeto_integrador.projeto_integrador.modules.subjects.controller;

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

import com.projeto_integrador.projeto_integrador.modules.subjects.entity.SubjectEntity;
import com.projeto_integrador.projeto_integrador.modules.subjects.repository.SubjectRepository;
import com.projeto_integrador.projeto_integrador.modules.subjects.usecases.CreateSubject;
import com.projeto_integrador.projeto_integrador.modules.subjects.usecases.DeleteSubjectById;
import com.projeto_integrador.projeto_integrador.modules.subjects.usecases.GetAllSubjects;
import com.projeto_integrador.projeto_integrador.modules.subjects.usecases.GetSubjectById;
import com.projeto_integrador.projeto_integrador.modules.subjects.usecases.PutSubjectById;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;

@RestController
@RequestMapping("subject")
@CrossOrigin
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
    public ResponseEntity<Object> create(@Valid @RequestBody SubjectEntity subjectEntity) {
        try {
            var result = this.createSubject.execute(subjectEntity);
            return ResponseEntity.ok().body(result);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/")
    public ResponseEntity<List<SubjectEntity>> getAllSubjects() {
       try {
            var result = this.getAllSubjects.execute();
            return ResponseEntity.ok().body(result);
       } catch (Exception e) {
            throw new EntityNotFoundException("Subject not Register");
       }
    }

    @GetMapping("/{id}")
    public ResponseEntity<SubjectEntity> getById(@Valid @PathVariable long id){
       try {
        var subject = this.getSubjectById.execute(id);
        return ResponseEntity.ok().body(subject);
       } catch (Exception e) {
            throw new EntityNotFoundException("Subject not found");
       }
        
    }

    @PutMapping("/{id}")
    public ResponseEntity<SubjectEntity> putSubject(@Valid @RequestBody SubjectEntity subjectEntity, @PathVariable Long id) {
        try {
            var updatedSubject = this.putSubjectById.execute(id, subjectEntity);
            return ResponseEntity.ok().body(updatedSubject);
        } catch (Exception e) {
            throw new EntityNotFoundException("Subject not found");
        }
        
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSubject(@Valid @PathVariable Long id) {
        this.deleteSubjectById.execute(id);
        return ResponseEntity.ok().build();
    }


}
