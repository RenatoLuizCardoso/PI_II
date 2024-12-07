package com.projeto_integrador.projeto_integrador.modules.student.usecases;


import com.projeto_integrador.projeto_integrador.modules.student.repository.StudentRepository;
import com.projeto_integrador.projeto_integrador.modules.student.usecases.DeleteStudentUseCase;

import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*;

import static org.junit.jupiter.api.Assertions.*;

class DeleteStudentUseCaseTest {

    @InjectMocks
    private DeleteStudentUseCase deleteStudentUseCase;

    @Mock
    private StudentRepository studentRepository;

    private Long studentId;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Definindo um id de estudante para os testes
        studentId = 1L;
    }

    @Test
    void testExecute_ShouldDeleteStudent_WhenStudentExists() {
        // Mockando o comportamento do repositório para o caso de o estudante existir
        when(studentRepository.existsById(studentId)).thenReturn(true);

        // Executando o método
        deleteStudentUseCase.execute(studentId);

        // Verificando se o método deleteById foi chamado
        verify(studentRepository, times(1)).deleteById(studentId);
    }

    @Test
    void testExecute_ShouldThrowEntityNotFoundException_WhenStudentDoesNotExist() {
        // Mockando o comportamento do repositório para o caso de o estudante não existir
        when(studentRepository.existsById(studentId)).thenReturn(false);

        // Verificando se a exceção EntityNotFoundException é lançada
        EntityNotFoundException thrown = assertThrows(EntityNotFoundException.class, () -> {
            deleteStudentUseCase.execute(studentId);
        });

        assertNotNull(thrown);
        assertEquals("Student not found", thrown.getMessage()); // Verificando se a mensagem da exceção é a esperada
    }
}
