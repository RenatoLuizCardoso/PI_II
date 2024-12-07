package com.projeto_integrador.projeto_integrador.modules.student.controller;

import javax.security.sasl.AuthenticationException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import com.projeto_integrador.projeto_integrador.modules.student.dto.AuthStudentRequestDTO;
import com.projeto_integrador.projeto_integrador.modules.student.dto.AuthStudentResponseDTO;
import com.projeto_integrador.projeto_integrador.modules.student.usecases.AuthStudent;

class AuthStudentControllerTest {

    @InjectMocks
    private AuthStudentController authStudentController;

    @Mock
    private AuthStudent authStudent;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @SuppressWarnings("deprecation")
    @Test
    void auth_ShouldReturnToken_WhenCredentialsAreValid() throws AuthenticationException {
        // Arrange
        AuthStudentRequestDTO requestDTO = new AuthStudentRequestDTO("test@domain.com", "testPassword");
        AuthStudentResponseDTO expectedResponse = new AuthStudentResponseDTO("mockedToken", null, null);

        when(authStudent.execute(requestDTO)).thenReturn(expectedResponse);

        // Act
        ResponseEntity<Object> response = authStudentController.auth(requestDTO);

        // Assert
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(expectedResponse, response.getBody());
        verify(authStudent, times(1)).execute(requestDTO);
    }

    @SuppressWarnings("deprecation")
    @Test
    void auth_ShouldReturnUnauthorized_WhenExceptionOccurs() throws AuthenticationException {
        // Arrange
        AuthStudentRequestDTO requestDTO = new AuthStudentRequestDTO("test@domain.com", "testPassword");
        String errorMessage = "Invalid credentials";

        // Lançando uma exceção não verificada (RuntimeException)
        when(authStudent.execute(requestDTO)).thenThrow(new RuntimeException(errorMessage));

        // Act
        ResponseEntity<Object> response = authStudentController.auth(requestDTO);

        // Assert
        assertEquals(401, response.getStatusCodeValue());
        assertEquals(errorMessage, response.getBody());
        verify(authStudent, times(1)).execute(requestDTO);
    }
}
