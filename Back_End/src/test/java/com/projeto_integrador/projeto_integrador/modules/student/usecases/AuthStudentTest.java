package com.projeto_integrador.projeto_integrador.modules.student.usecases;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.TestPropertySource;

import com.projeto_integrador.projeto_integrador.modules.student.dto.AuthStudentRequestDTO;
import com.projeto_integrador.projeto_integrador.modules.student.dto.AuthStudentResponseDTO;
import com.projeto_integrador.projeto_integrador.modules.student.entity.StudentEntity;
import com.projeto_integrador.projeto_integrador.modules.student.repository.StudentRepository;
import com.projeto_integrador.projeto_integrador.modules.student.usecases.AuthStudent;



import javax.security.sasl.AuthenticationException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
@TestPropertySource(properties = "security.token.secret.student=dummyTestSecretKey") // Usar chave secreta de teste
public class AuthStudentTest {

    @InjectMocks
    private AuthStudent authStudent;

    @Mock
    private StudentRepository studentRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    public void setUp() {
        // Não é necessário usar MockitoAnnotations.openMocks(this) com @ExtendWith(MockitoExtension.class)
    }

    @Test
    public void testExecute_ShouldReturnAuthStudentResponseDTO_WhenCredentialsAreValid() throws AuthenticationException {
        // Mock de um estudante válido
        String institutionalEmail = "student@university.com";
        String studentPassword = "password123";
        String encodedPassword = "encodedPassword123";
        Long studentId = 1L;

        StudentEntity student = new StudentEntity();
        student.setStudentId(studentId);
        student.setInstitutionalEmail(institutionalEmail);
        student.setStudentPassword(encodedPassword);

        when(studentRepository.findByInstitutionalEmail(institutionalEmail)).thenReturn(Optional.of(student));
        when(passwordEncoder.matches(studentPassword, encodedPassword)).thenReturn(true);

        // Configurar o DTO de requisição
        var requestDTO = new AuthStudentRequestDTO(institutionalEmail, studentPassword);

        // Executar a ação
        AuthStudentResponseDTO responseDTO = authStudent.execute(requestDTO);

        // Verificar as asserções
        assertNotNull(responseDTO);
        assertNotNull(responseDTO.getAccess_token());
        assertTrue(responseDTO.getExpires_in() > 0);
    }
}

