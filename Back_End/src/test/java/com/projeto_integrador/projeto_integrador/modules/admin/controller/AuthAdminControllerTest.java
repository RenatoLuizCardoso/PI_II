package com.projeto_integrador.projeto_integrador.modules.admin.controller;

import javax.naming.AuthenticationException;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.projeto_integrador.projeto_integrador.modules.admin.dto.AuthAdminDTO;
import com.projeto_integrador.projeto_integrador.modules.admin.dto.AuthAdminResponseDTO;
import com.projeto_integrador.projeto_integrador.modules.admin.usecases.AuthAdminUseCase;

@ExtendWith(MockitoExtension.class)
public class AuthAdminControllerTest {

    @Mock
    private AuthAdminUseCase authAdminUseCase;

    @InjectMocks
    private AuthAdminController authAdminController;

    @BeforeEach
    public void setUp() {
    }

    @Test
    @DisplayName("Should return token when authentication is successful")
    public void should_return_token_when_authentication_successful() throws Exception {
        String email = "admin@example.com";
        String password = "password123";
        AuthAdminDTO authAdminDTO = new AuthAdminDTO(password, email);

        AuthAdminResponseDTO responseDTO = new AuthAdminResponseDTO();
        responseDTO.setAccess_token("mockedToken");

        when(authAdminUseCase.execute(authAdminDTO)).thenReturn(responseDTO);

        ResponseEntity<Object> result = authAdminController.create(authAdminDTO);

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(result.getBody()).isNotNull(); 
        assertThat(((AuthAdminResponseDTO) result.getBody()).getAccess_token()).isEqualTo("mockedToken");
    }

    @Test
    @DisplayName("Should return unauthorized when authentication fails")
    public void should_return_unauthorized_when_authentication_fails() throws AuthenticationException {
        String email = "invalid@example.com";
        String password = "wrongPassword";
        AuthAdminDTO authAdminDTO = new AuthAdminDTO(password, email);

        when(authAdminUseCase.execute(authAdminDTO)).thenThrow(new AuthenticationException("Authentication failed"));

        ResponseEntity<Object> result = authAdminController.create(authAdminDTO);
        
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
        assertThat(result.getBody()).isEqualTo("Authentication failed");
    }
}
