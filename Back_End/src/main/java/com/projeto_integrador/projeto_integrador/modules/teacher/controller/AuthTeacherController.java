package com.projeto_integrador.projeto_integrador.modules.teacher.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;

import com.projeto_integrador.projeto_integrador.modules.teacher.dto.AuthTeacherRequestDTO;
import com.projeto_integrador.projeto_integrador.modules.teacher.usecases.AuthTeacher;


@RestController
@RequestMapping("/teacher")
public class AuthTeacherController {

    @Autowired
    private AuthTeacher authTeacher;

    @PostMapping("/auth")
    public ResponseEntity<Object> auth(@RequestBody AuthTeacherRequestDTO authTeacherRequestDTO) {
        try {
            var token = this.authTeacher.execute(authTeacherRequestDTO);
            return ResponseEntity.ok().body(token);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
    }
}
