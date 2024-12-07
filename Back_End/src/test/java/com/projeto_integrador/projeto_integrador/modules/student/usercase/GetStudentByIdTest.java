package com.projeto_integrador.projeto_integrador.modules.student.usercase;

import com.projeto_integrador.projeto_integrador.modules.student.entity.StudentEntity;
import com.projeto_integrador.projeto_integrador.modules.student.repository.StudentRepository;
import com.projeto_integrador.projeto_integrador.modules.student.usecases.GetStudentById;

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
public class GetStudentByIdTest {

    @Mock
    private StudentRepository studentRepository;

    @InjectMocks
    private GetStudentById getStudentById;

    private StudentEntity student;

    @BeforeEach
    void setUp() {
        // Criando uma entidade de estudante
        student = new StudentEntity();
        student.setStudentId(1L);
        student.setInstitutionalEmail("student@example.com");
        student.setStudentPassword("password123");
    }

    @Test
    void testExecute_ShouldReturnStudent_WhenStudentExists() {
        // Arrange: mock do repositório para retornar um estudante
        when(studentRepository.findById(1L)).thenReturn(java.util.Optional.of(student));

        // Act: invocar o método
        StudentEntity result = getStudentById.execute(1L);

        // Assert: Verificar se o estudante retornado é o esperado
        assertNotNull(result);
        assertEquals(student.getStudentId(), result.getStudentId());
        assertEquals(student.getInstitutionalEmail(), result.getInstitutionalEmail());
        assertEquals(student.getStudentPassword(), result.getStudentPassword());

        // Verificar que o repositório foi chamado uma vez
        verify(studentRepository, times(1)).findById(1L);
    }

    @Test
    void testExecute_ShouldThrowException_WhenStudentDoesNotExist() {
        // Arrange: mock do repositório para retornar Optional.empty()
        when(studentRepository.findById(1L)).thenReturn(java.util.Optional.empty());

        // Act & Assert: verificar se a exceção é lançada
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> {
            getStudentById.execute(1L);
        });

        assertEquals("Student not found", exception.getMessage());

        // Verificar que o repositório foi chamado uma vez
        verify(studentRepository, times(1)).findById(1L);
    }
}
