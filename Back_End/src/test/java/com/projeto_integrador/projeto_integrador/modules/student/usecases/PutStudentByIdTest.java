package com.projeto_integrador.projeto_integrador.modules.student.usecases;

import com.projeto_integrador.projeto_integrador.modules.student.entity.StudentEntity;
import com.projeto_integrador.projeto_integrador.modules.student.repository.StudentRepository;
import com.projeto_integrador.projeto_integrador.modules.student.usecases.PutStudentById;

import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PutStudentByIdTest {

    @Mock
    private StudentRepository studentRepository;

    @InjectMocks
    private PutStudentById putStudentById;

    private StudentEntity existingStudent;
    private StudentEntity updatedStudent;

    @BeforeEach
    void setUp() {
        // Estudante existente no banco
        existingStudent = new StudentEntity();
        existingStudent.setStudentId(1L);
        existingStudent.setStudentName("John Doe");
        existingStudent.setInstitutionalEmail("john.doe@example.com");
        existingStudent.setStudentPassword("password123");

        // Estudante com novos dados
        updatedStudent = new StudentEntity();
        updatedStudent.setStudentName("John Updated");
        updatedStudent.setInstitutionalEmail("john.updated@example.com");
        updatedStudent.setStudentPassword("newPassword123");
    }

    @Test
    void testExecute_ShouldUpdateStudent_WhenStudentExists() {
        // Arrange: Simula que o repositório encontra o estudante existente
        when(studentRepository.findById(1L)).thenReturn(java.util.Optional.of(existingStudent));
        when(studentRepository.save(any(StudentEntity.class))).thenReturn(existingStudent);

        // Act: Chama o método execute para atualizar o estudante
        StudentEntity result = putStudentById.execute(1L, updatedStudent);

        // Assert: Verifica se os dados foram atualizados corretamente
        assertNotNull(result);
        assertEquals(existingStudent.getStudentId(), result.getStudentId());
        assertEquals("John Updated", result.getStudentName());
        assertEquals("john.updated@example.com", result.getInstitutionalEmail());
        assertEquals("newPassword123", result.getStudentPassword());

        // Verifica se o repositório foi chamado com os parâmetros corretos
        verify(studentRepository, times(1)).findById(1L);
        verify(studentRepository, times(1)).save(result);
    }

    @Test
    void testExecute_ShouldThrowException_WhenStudentDoesNotExist() {
        // Arrange: Simula que o repositório não encontra o estudante
        when(studentRepository.findById(1L)).thenReturn(java.util.Optional.empty());

        // Act & Assert: Verifica se a exceção é lançada
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> {
            putStudentById.execute(1L, updatedStudent);
        });

        assertEquals("Student not found", exception.getMessage());

        // Verifica se o repositório foi chamado para procurar o estudante
        verify(studentRepository, times(1)).findById(1L);
        verify(studentRepository, never()).save(any());
    }
}
