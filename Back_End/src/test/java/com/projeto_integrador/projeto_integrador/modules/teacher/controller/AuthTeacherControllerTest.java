package com.projeto_integrador.projeto_integrador.modules.teacher.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.projeto_integrador.projeto_integrador.modules.teacher.dto.AuthTeacherRequestDTO;
import com.projeto_integrador.projeto_integrador.modules.teacher.dto.AuthTeacherResponseDTO;
import com.projeto_integrador.projeto_integrador.modules.teacher.usecases.AuthTeacher;

class AuthTeacherControllerTest {

    private MockMvc mockMvc;

    @InjectMocks
    private AuthTeacherController authTeacherController;

    @Mock
    private AuthTeacher authTeacher;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(authTeacherController).build();
    }

    @Test
    void testAuth_Success() throws Exception {
        AuthTeacherRequestDTO requestDTO = new AuthTeacherRequestDTO("teacher@example.com", "password123");
        AuthTeacherResponseDTO responseDTO = AuthTeacherResponseDTO.builder()
                .access_token("jwt_token_example")
                .expires_in(3600L)
                .build();
    
        when(authTeacher.execute(any(AuthTeacherRequestDTO.class))).thenReturn(responseDTO);
    
        mockMvc.perform(post("/teacher/auth")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.access_token").value("jwt_token_example"))
                .andExpect(jsonPath("$.expires_in").value(3600));
    }
}