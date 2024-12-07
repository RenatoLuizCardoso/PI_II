package com.projeto_integrador.projeto_integrador.modules.courses.controller;


import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import com.projeto_integrador.projeto_integrador.modules.courses.entity.CourseEntity;
import com.projeto_integrador.projeto_integrador.modules.courses.usecases.CreateCourse;
import com.projeto_integrador.projeto_integrador.modules.courses.usecases.DeleteCourseById;
import com.projeto_integrador.projeto_integrador.modules.courses.usecases.GetAllCourses;
import com.projeto_integrador.projeto_integrador.modules.courses.usecases.GetCourseById;
import com.projeto_integrador.projeto_integrador.modules.courses.usecases.PutCourseById;

import jakarta.persistence.EntityNotFoundException;

public class CourseControllerTest {

    @InjectMocks
    private CourseController courseController;

    @Mock
    private CreateCourse createCourse;

    @Mock
    private GetAllCourses getAllCourses;

    @Mock
    private GetCourseById getCourseById;

    @Mock
    private PutCourseById putCourseById;

    @Mock
    private DeleteCourseById deleteCourseById;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @SuppressWarnings("deprecation")
    @Test
    public void shouldCreateCourseSuccessfully() {
        CourseEntity courseEntity = new CourseEntity();
        when(createCourse.execute(courseEntity)).thenReturn(courseEntity);

        ResponseEntity<Object> response = courseController.create(courseEntity);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(courseEntity, response.getBody());
    }

    @SuppressWarnings("deprecation")
    @Test
    public void shouldReturnAllCourses() {
        List<Map<String, Object>> courses = List.of(Map.of("id", 1L, "name", "Course 1"));
        when(getAllCourses.execute()).thenReturn(courses);

        ResponseEntity<?> response = courseController.getAllCourses();

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(courses, response.getBody());
    }

    @SuppressWarnings("deprecation")
    @Test
    public void shouldReturnCourseById() {
        Map<String, Object> courseMap = Map.of("id", 1L, "name", "Course 1");
        when(getCourseById.execute(1L)).thenReturn(courseMap);

        ResponseEntity<Map<String, Object>> response = courseController.getById(1L);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(courseMap, response.getBody());
    }

    @SuppressWarnings("deprecation")
    @Test
    public void shouldThrowExceptionWhenCourseNotFoundById() {
        when(getCourseById.execute(1L)).thenThrow(new EntityNotFoundException("Course not found"));

        ResponseEntity<Map<String, Object>> response = courseController.getById(1L);

        assertEquals(404, response.getStatusCodeValue());
        assertTrue(response.getBody().containsKey("error"));
    }

    @SuppressWarnings("deprecation")
    @Test
    public void shouldUpdateCourseSuccessfully() {
        CourseEntity courseEntity = new CourseEntity();
        when(putCourseById.execute(1L, courseEntity)).thenReturn(courseEntity);

        ResponseEntity<?> response = courseController.putCourse(courseEntity, 1L);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(courseEntity, response.getBody());
    }

    @SuppressWarnings("deprecation")
    @Test
    public void shouldDeleteCourseSuccessfully() {
        doNothing().when(deleteCourseById).execute(1L);

        ResponseEntity<?> response = courseController.deleteCourse(1L);

        assertEquals(200, response.getStatusCodeValue());
        verify(deleteCourseById, times(1)).execute(1L);
    }

    @SuppressWarnings("deprecation")
    @Test
    public void shouldHandleCourseNotFoundOnDelete() {
        doThrow(new EntityNotFoundException("Course not found")).when(deleteCourseById).execute(1L);

        ResponseEntity<?> response = courseController.deleteCourse(1L);

        assertEquals(400, response.getStatusCodeValue());
    }
}
