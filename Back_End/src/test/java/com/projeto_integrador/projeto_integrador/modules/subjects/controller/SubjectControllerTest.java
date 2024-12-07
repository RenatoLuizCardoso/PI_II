package com.projeto_integrador.projeto_integrador.modules.subjects.controller;

import com.projeto_integrador.projeto_integrador.modules.subjects.entity.SubjectEntity;
import com.projeto_integrador.projeto_integrador.modules.subjects.usecases.*;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class SubjectControllerTest {

    @InjectMocks
    private SubjectController subjectController;

    @Mock
    private CreateSubject createSubject;

    @Mock
    private GetAllSubjects getAllSubjects;

    @Mock
    private GetSubjectById getSubjectById;

    @Mock
    private PutSubjectById putSubjectById;

    @Mock
    private DeleteSubjectById deleteSubjectById;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void create_ShouldReturnCreatedSubject_WhenSuccessful() throws Exception {
        // Arrange
        SubjectEntity subject = new SubjectEntity(1L, "Mathematics", null, null, null);
        when(createSubject.execute(subject)).thenReturn(subject);

        // Act
        ResponseEntity<Object> response = subjectController.create(subject);

        // Assert
        assertEquals(200, response.getStatusCodeValue());  // Atualizando para 200 OK
        assertEquals(subject, response.getBody());
        verify(createSubject, times(1)).execute(subject);
    }

    @Test
    void getAllSubjects_ShouldReturnListOfSubjects_WhenSuccessful() throws Exception {
        // Arrange
        List<SubjectEntity> subjects = Arrays.asList(
                new SubjectEntity(1L, "Mathematics", null, null, null),
                new SubjectEntity(2L, "History", null, null, null)
        );
        when(getAllSubjects.execute()).thenReturn(subjects);

        // Act
        ResponseEntity<Object> response = subjectController.getAllSubjects();

        // Assert
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(subjects, response.getBody());
        verify(getAllSubjects, times(1)).execute();
    }

    @Test
    void getById_ShouldReturnSubject_WhenFound() throws Exception {
        // Arrange
        SubjectEntity subject = new SubjectEntity(1L, "Mathematics", null, null, null);
        when(getSubjectById.execute(1L)).thenReturn(subject);

        // Act
        ResponseEntity<Object> response = subjectController.getById(1L);

        // Assert
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(subject, response.getBody());
        verify(getSubjectById, times(1)).execute(1L);
    }

    @Test
    void getById_ShouldThrowException_WhenNotFound() {
        // Arrange
        when(getSubjectById.execute(1L)).thenThrow(new EntityNotFoundException("Subject not found"));

        // Act & Assert
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> subjectController.getById(1L));
        assertEquals("Subject not found", exception.getMessage());
        verify(getSubjectById, times(1)).execute(1L);
    }

    @Test
    void putSubject_ShouldReturnUpdatedSubject_WhenSuccessful() throws Exception {
        // Arrange
        SubjectEntity updatedSubject = new SubjectEntity(1L, "Advanced Mathematics", null, null, null);
        when(putSubjectById.execute(1L, updatedSubject)).thenReturn(updatedSubject);

        // Act
        ResponseEntity<Object> response = subjectController.putSubject(updatedSubject, 1L);

        // Assert
        assertEquals(200, response.getStatusCodeValue());  // Atualizando para 200 OK
        assertEquals(updatedSubject, response.getBody());
        verify(putSubjectById, times(1)).execute(1L, updatedSubject);
    }

    @Test
    void deleteSubject_ShouldReturnOk_WhenSuccessful() {
        // Arrange
        doNothing().when(deleteSubjectById).execute(1L);

        // Act
        ResponseEntity<Object> response = subjectController.deleteSubject(1L);

        // Assert
        assertEquals(200, response.getStatusCodeValue());  // Atualizando para 200 OK
        verify(deleteSubjectById, times(1)).execute(1L);
    }
}
