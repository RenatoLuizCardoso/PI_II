package com.projeto_integrador.projeto_integrador.modules.teacher.usecases;

import com.projeto_integrador.projeto_integrador.modules.subjects.entity.SubjectEntity;
import com.projeto_integrador.projeto_integrador.modules.subjects.repository.SubjectRepository;
import com.projeto_integrador.projeto_integrador.modules.teacher.entity.TeacherEntity;
import com.projeto_integrador.projeto_integrador.modules.teacher.repository.TeacherRepository;
import com.projeto_integrador.projeto_integrador.modules.teacher.usecases.GetAllTeachers;

import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class GetAllTeachersTest {

    @InjectMocks
    private GetAllTeachers getAllTeachers;

    @Mock
    private TeacherRepository teacherRepository;

    @Mock
    private SubjectRepository subjectRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void shouldReturnListOfTeachers() {
        // Creating teachers
        TeacherEntity teacher1 = new TeacherEntity();
        teacher1.setTeacherId(1L);
        teacher1.setTeacherName("Teacher One");
        teacher1.setInstitutionalEmail("teacher1@example.com");

        TeacherEntity teacher2 = new TeacherEntity();
        teacher2.setTeacherId(2L);
        teacher2.setTeacherName("Teacher Two");
        teacher2.setInstitutionalEmail("teacher2@example.com");

        // Creating subjects
        SubjectEntity subject1 = new SubjectEntity();
        subject1.setSubjectId(1L);
        subject1.setSubjectName("Mathematics");

        SubjectEntity subject2 = new SubjectEntity();
        subject2.setSubjectId(2L);
        subject2.setSubjectName("Physics");

        // Associating subjects to teachers
        teacher1.setTeacherSubjects(Arrays.asList(subject1, subject2));
        teacher2.setTeacherSubjects(Arrays.asList(subject1));

        // Mocking the repositories
        when(teacherRepository.findAll()).thenReturn(Arrays.asList(teacher1, teacher2));
        when(subjectRepository.findById(1L)).thenReturn(Optional.of(subject1));
        when(subjectRepository.findById(2L)).thenReturn(Optional.of(subject2));

        // Executing the use case
        List<Map<String, Object>> result = getAllTeachers.execute();

        // Asserts
        assertNotNull(result);
        assertEquals(2, result.size());

        Map<String, Object> firstTeacher = result.get(0);
        assertEquals("Teacher One", firstTeacher.get("teacherName"));
        assertEquals(Arrays.asList("Mathematics", "Physics"), firstTeacher.get("subjects"));

        Map<String, Object> secondTeacher = result.get(1);
        assertEquals("Teacher Two", secondTeacher.get("teacherName"));
        assertEquals(Arrays.asList("Mathematics"), secondTeacher.get("subjects"));
    }

    @Test
    public void shouldThrowExceptionWhenNoTeachersFound() {
        when(teacherRepository.findAll()).thenReturn(List.of()); // Empty list of teachers

        Exception exception = assertThrows(EntityNotFoundException.class, () -> getAllTeachers.execute());

        assertEquals("Teacher not Registered", exception.getMessage());
        verify(teacherRepository, times(1)).findAll(); // Ensure findAll was called once
    }
}
