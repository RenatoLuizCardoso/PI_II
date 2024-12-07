package com.projeto_integrador.projeto_integrador.modules.student.usercase;

import com.projeto_integrador.projeto_integrador.exceptions.UserFoundException;
import com.projeto_integrador.projeto_integrador.modules.student.entity.StudentEntity;
import com.projeto_integrador.projeto_integrador.modules.student.repository.StudentRepository;
import com.projeto_integrador.projeto_integrador.modules.student.usecases.CreateStudentUseCase;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import static org.junit.jupiter.api.Assertions.*;

class CreateStudentUseCaseTest {

    @InjectMocks
    private CreateStudentUseCase createStudentUseCase;

    @Mock
    private StudentRepository studentRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    private StudentEntity studentEntity;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Criando um exemplo de StudentEntity
        studentEntity = new StudentEntity();
        studentEntity.setInstitutionalEmail("student@university.com");
        studentEntity.setStudentPassword("password123");
    }

    @Test
    void testExecute_ShouldThrowUserFoundException_WhenStudentWithEmailAlreadyExists() {
        // Mockando o comportamento do studentRepository
        when(studentRepository.findByInstitutionalEmail(studentEntity.getInstitutionalEmail()))
                .thenReturn(Optional.of(new StudentEntity()));

        // Verificando se a exceção é lançada quando o email já existe
        UserFoundException thrown = assertThrows(UserFoundException.class, () -> {
            createStudentUseCase.execute(studentEntity);
        });

        assertNotNull(thrown);
        verify(studentRepository, never()).save(any()); // Garantir que o save nunca foi chamado
    }

    @Test
    void testExecute_ShouldEncryptPasswordAndSaveStudent_WhenStudentEmailDoesNotExist() {
        // Mockando o comportamento do studentRepository
        when(studentRepository.findByInstitutionalEmail(studentEntity.getInstitutionalEmail()))
                .thenReturn(Optional.empty());

        // Mockando o comportamento do PasswordEncoder
        String encodedPassword = "encodedPassword123";
        when(passwordEncoder.encode(studentEntity.getStudentPassword())).thenReturn(encodedPassword);

        // Mockando o save do studentRepository
        when(studentRepository.save(any(StudentEntity.class))).thenReturn(studentEntity);

        // Executando o método
        StudentEntity savedStudent = createStudentUseCase.execute(studentEntity);

        // Verificando se o comportamento está correto
        assertNotNull(savedStudent);
        assertEquals(encodedPassword, savedStudent.getStudentPassword()); // Verificando se a senha foi criptografada
        verify(studentRepository, times(1)).save(studentEntity); // Garantir que o save foi chamado exatamente uma vez
    }
}
