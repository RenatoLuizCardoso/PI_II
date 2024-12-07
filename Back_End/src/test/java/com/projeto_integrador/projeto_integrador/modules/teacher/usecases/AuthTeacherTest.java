package com.projeto_integrador.projeto_integrador.modules.teacher.usecases;

import java.lang.reflect.Field;

import com.projeto_integrador.projeto_integrador.modules.teacher.dto.AuthTeacherRequestDTO;
import com.projeto_integrador.projeto_integrador.modules.teacher.dto.AuthTeacherResponseDTO;
import com.projeto_integrador.projeto_integrador.modules.teacher.entity.TeacherEntity;
import com.projeto_integrador.projeto_integrador.modules.teacher.repository.TeacherRepository;
import com.projeto_integrador.projeto_integrador.modules.teacher.usecases.AuthTeacher;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.security.sasl.AuthenticationException;

import java.time.Instant;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class AuthTeacherTest {

    @InjectMocks
    private AuthTeacher authTeacher;

    @Mock
    private TeacherRepository teacherRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    private AuthTeacherRequestDTO requestDTO;
    private TeacherEntity teacher;

    @BeforeEach
    public void setUp() throws NoSuchFieldException, IllegalAccessException {
        MockitoAnnotations.openMocks(this);

        // Acessando o campo secretKey e configurando-o diretamente via reflexão
        Field secretKeyField = AuthTeacher.class.getDeclaredField("secretKey");
        secretKeyField.setAccessible(true); // Torna o campo acessível
        secretKeyField.set(authTeacher, "secret_key"); // Configura o valor do secretKey

        requestDTO = new AuthTeacherRequestDTO("teacher@example.com", "password123");
        teacher = new TeacherEntity();
        teacher.setTeacherId(1L);
        teacher.setInstitutionalEmail("teacher@example.com");
        teacher.setTeacherPassword("encodedPassword");
    }

    @Test
    public void shouldAuthenticateAndReturnToken() throws Exception {
        when(teacherRepository.findByInstitutionalEmail(requestDTO.institutionalEmail()))
                .thenReturn(Optional.of(teacher));
        when(passwordEncoder.matches(requestDTO.teacherPassword(), teacher.getTeacherPassword()))
                .thenReturn(true);

        AuthTeacherResponseDTO response = authTeacher.execute(requestDTO);

        assertNotNull(response);
        assertNotNull(response.getAccess_token());
        assertTrue(response.getExpires_in() > Instant.now().toEpochMilli());

        verify(teacherRepository, times(1)).findByInstitutionalEmail(requestDTO.institutionalEmail());
        verify(passwordEncoder, times(1)).matches(requestDTO.teacherPassword(), teacher.getTeacherPassword());
    }

    @Test
    public void shouldThrowExceptionWhenTeacherNotFound() {
        when(teacherRepository.findByInstitutionalEmail(requestDTO.institutionalEmail()))
                .thenReturn(Optional.empty());

        Exception exception = assertThrows(UsernameNotFoundException.class, () -> authTeacher.execute(requestDTO));

        assertEquals("Email/password incorrect", exception.getMessage());
        verify(teacherRepository, times(1)).findByInstitutionalEmail(requestDTO.institutionalEmail());
    }

    @Test
    public void shouldThrowExceptionWhenPasswordDoesNotMatch() {
        when(teacherRepository.findByInstitutionalEmail(requestDTO.institutionalEmail()))
                .thenReturn(Optional.of(teacher));
        when(passwordEncoder.matches(requestDTO.teacherPassword(), teacher.getTeacherPassword()))
                .thenReturn(false);

        Exception exception = assertThrows(AuthenticationException.class, () -> authTeacher.execute(requestDTO));

        assertEquals("Invalid password", exception.getMessage());
        verify(teacherRepository, times(1)).findByInstitutionalEmail(requestDTO.institutionalEmail());
        verify(passwordEncoder, times(1)).matches(requestDTO.teacherPassword(), teacher.getTeacherPassword());
    }
}
