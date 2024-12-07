package com.projeto_integrador.projeto_integrador.modules.teacher.usecases;

import com.projeto_integrador.projeto_integrador.modules.subjects.entity.SubjectEntity;
import com.projeto_integrador.projeto_integrador.modules.subjects.repository.SubjectRepository;
import com.projeto_integrador.projeto_integrador.modules.teacher.entity.TeacherEntity;
import com.projeto_integrador.projeto_integrador.modules.teacher.repository.TeacherRepository;
import com.projeto_integrador.projeto_integrador.modules.teacher.usecases.GetTeacherById;

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

public class GetTeacherByIdTest {

    @InjectMocks
    private GetTeacherById getTeacherById;

    @Mock
    private TeacherRepository teacherRepository;

    @Mock
    private SubjectRepository subjectRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void shouldReturnTeacherWhenFound() {
        // Criando professores
        TeacherEntity teacher = new TeacherEntity();
        teacher.setTeacherId(1L);
        teacher.setTeacherName("Teacher One");
        teacher.setInstitutionalEmail("teacher1@example.com");

        // Criando disciplinas
        SubjectEntity subject1 = new SubjectEntity();
        subject1.setSubjectId(1L);
        subject1.setSubjectName("Mathematics");

        SubjectEntity subject2 = new SubjectEntity();
        subject2.setSubjectId(2L);
        subject2.setSubjectName("Physics");

        // Associando disciplinas ao professor
        teacher.setTeacherSubjects(Arrays.asList(subject1, subject2));

        // Mockando o repositório de professores
        when(teacherRepository.findById(1L)).thenReturn(Optional.of(teacher));

        // Executando o caso de uso
        Map<String, Object> result = getTeacherById.execute(1L);

        // Asserts
        assertNotNull(result);
        assertEquals("Teacher One", result.get("teacherName"));
        assertEquals(Arrays.asList("Mathematics", "Physics"), result.get("subjects"));
    }

    @Test
    public void shouldThrowExceptionWhenTeacherNotFound() {
        // Mockando o repositório de professores para retornar vazio
        when(teacherRepository.findById(1L)).thenReturn(Optional.empty());

        // Verificando se a exceção correta é lançada
        Exception exception = assertThrows(EntityNotFoundException.class, () -> getTeacherById.execute(1L));

        // Asserts
        assertEquals("Teacher not found", exception.getMessage());
        verify(teacherRepository, times(1)).findById(1L); // Verifica se o método foi chamado uma vez
    }
}
