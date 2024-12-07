package com.projeto_integrador.projeto_integrador.modules.student.usecases;

import com.projeto_integrador.projeto_integrador.modules.student.entity.StudentEntity;
import com.projeto_integrador.projeto_integrador.modules.student.repository.StudentRepository;
import com.projeto_integrador.projeto_integrador.modules.student.usecases.GetAllStudents;

import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class GetAllStudentsTest {

    @Mock
    private StudentRepository studentRepository;

    @InjectMocks
    private GetAllStudents getAllStudents;

    private StudentEntity student1;
    private StudentEntity student2;

    @BeforeEach
    void setUp() {
        // Criando entidades de exemplo
        student1 = new StudentEntity();
        student1.setStudentId(1L);
        student1.setInstitutionalEmail("student1@example.com");
        student1.setStudentPassword("password123");

        student2 = new StudentEntity();
        student2.setStudentId(2L);
        student2.setInstitutionalEmail("student2@example.com");
        student2.setStudentPassword("password456");
    }

    @Test
    void testExecute_ShouldReturnAllStudents_WhenStudentsExist() {
        // Arrange: mock do repositório para retornar uma lista de estudantes
        List<StudentEntity> students = Arrays.asList(student1, student2);
        when(studentRepository.findAll()).thenReturn(students);

        // Act: invocar o método
        List<StudentEntity> result = getAllStudents.execute();

        // Assert: Verificar se a lista de estudantes foi retornada corretamente
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(student1.getStudentId(), result.get(0).getStudentId());
        assertEquals(student2.getStudentId(), result.get(1).getStudentId());

        // Verificar que o repositório foi chamado uma vez
        verify(studentRepository, times(1)).findAll();
    }

    @Test
    void testExecute_ShouldThrowException_WhenNoStudentsExist() {
        // Arrange: mock do repositório para retornar uma lista vazia
        when(studentRepository.findAll()).thenReturn(List.of());

        // Act & Assert: verificar se a exceção é lançada
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> {
            getAllStudents.execute();
        });

        assertEquals("Student not Register", exception.getMessage());

        // Verificar que o repositório foi chamado uma vez
        verify(studentRepository, times(1)).findAll();
    }
}
