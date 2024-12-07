package com.projeto_integrador.projeto_integrador.modules.courses.usecases;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.projeto_integrador.projeto_integrador.modules.courses.repository.CourseRepository;
import com.projeto_integrador.projeto_integrador.modules.courses.usecases.DeleteCourseById;

import jakarta.persistence.EntityNotFoundException;

class DeleteCourseByIdTest {

    @InjectMocks
    private DeleteCourseById deleteCourseById;

    @Mock
    private CourseRepository repository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testExecute_CourseExists_DeletesCourse() {
        // Arrange
        Long courseId = 1L;
        when(repository.existsById(courseId)).thenReturn(true); // O curso existe

        // Act
        deleteCourseById.execute(courseId);

        // Assert
        verify(repository).deleteById(courseId); // Verifica se deleteById foi chamado
    }

    @Test
    void testExecute_CourseDoesNotExist_ThrowsException() {
        // Arrange
        Long courseId = 1L;
        when(repository.existsById(courseId)).thenReturn(false); // O curso não existe

        // Act & Assert
        assertThrows(EntityNotFoundException.class, () -> deleteCourseById.execute(courseId));
        verify(repository, never()).deleteById(courseId); // Verifica se deleteById não foi chamado
    }
}
