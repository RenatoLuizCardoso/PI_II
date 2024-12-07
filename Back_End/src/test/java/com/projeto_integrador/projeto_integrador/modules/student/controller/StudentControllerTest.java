package com.projeto_integrador.projeto_integrador.modules.student.controller;

import com.projeto_integrador.projeto_integrador.modules.student.entity.StudentEntity;
import com.projeto_integrador.projeto_integrador.modules.student.usecases.*;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class StudentControllerTest {

    @InjectMocks
    private StudentController studentController;

    @Mock
    private CreateStudentUseCase createStudent;

    @Mock
    private GetAllStudents getAllStudents;

    @Mock
    private GetStudentById getStudentById;

    @Mock
    private PutStudentById putStudentById;

    @Mock
    private DeleteStudentUseCase deleteStudentById;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void create_ShouldReturnCreatedStudent_WhenSuccessful() throws Exception {
        // Arrange
        StudentEntity student = new StudentEntity(1L, "John Doe", "john.doe@example.com", "password123");
        when(createStudent.execute(student)).thenReturn(student);

        // Act
        ResponseEntity<Object> response = studentController.create(student);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(student, response.getBody());
        verify(createStudent, times(1)).execute(student);
    }

    @Test
    void getAllStudents_ShouldReturnListOfStudents_WhenSuccessful() throws Exception {
        // Arrange
        List<StudentEntity> students = Arrays.asList(
                new StudentEntity(1L, "John Doe", "john.doe@example.com", "password123"),
                new StudentEntity(2L, "Jane Doe", "jane.doe@example.com", "password456")
        );
        when(getAllStudents.execute()).thenReturn(students);

        // Act
        ResponseEntity<Object> response = studentController.getAllStudents(null);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(students, response.getBody());
        verify(getAllStudents, times(1)).execute();
    }

    @Test
    void getById_ShouldReturnStudent_WhenFound() throws Exception {
        // Arrange
        StudentEntity student = new StudentEntity(1L, "John Doe", "john.doe@example.com", "password123");
        when(getStudentById.execute(1L)).thenReturn(student);

        // Act
        ResponseEntity<Object> response = studentController.getById(1L);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(student, response.getBody());
        verify(getStudentById, times(1)).execute(1L);
    }

    @Test
    void getById_ShouldReturnNotFound_WhenNotFound() throws Exception {
        // Arrange
        when(getStudentById.execute(1L)).thenThrow(new EntityNotFoundException("Student not found"));

        // Act & Assert
        ResponseEntity<Object> response = studentController.getById(1L);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Student not found", response.getBody());
        verify(getStudentById, times(1)).execute(1L);
    }

    @Test
    void putStudent_ShouldReturnUpdatedStudent_WhenSuccessful() throws Exception {
        // Arrange
        StudentEntity updatedStudent = new StudentEntity(1L, "John Doe", "john.new@example.com", "newpassword123");
        when(putStudentById.execute(1L, updatedStudent)).thenReturn(updatedStudent);

        // Act
        ResponseEntity<Object> response = studentController.putStudent(updatedStudent, 1L);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(updatedStudent, response.getBody());
        verify(putStudentById, times(1)).execute(1L, updatedStudent);
    }

    @Test
    void deleteStudent_ShouldReturnOk_WhenSuccessful() {
        // Arrange
        doNothing().when(deleteStudentById).execute(1L);

        // Act
        ResponseEntity<Object> response = studentController.deleteStudent(1L);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(deleteStudentById, times(1)).execute(1L);
    }
}
