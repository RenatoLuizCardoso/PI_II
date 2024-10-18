package com.projeto_integrador.projeto_integrador.modules.student.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;

import com.projeto_integrador.projeto_integrador.modules.student.dto.AuthStudentRequestDTO;
import com.projeto_integrador.projeto_integrador.modules.student.usecases.AuthStudent;



@RestController
@RequestMapping("/student")
public class AuthStudentController {

    @Autowired
    private AuthStudent authStudent;

    @PostMapping("/auth")
    public ResponseEntity<Object> auth(@RequestBody AuthStudentRequestDTO authStudentRequestDTO) {
        try {
            var token = this.authStudent.execute(authStudentRequestDTO);
            return ResponseEntity.ok().body(token);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
    }
}
