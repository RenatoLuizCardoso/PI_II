package com.projeto_integrador.projeto_integrador.modules.teacher.usecases;

import com.projeto_integrador.projeto_integrador.modules.teacher.entity.TeacherEntity;
import com.projeto_integrador.projeto_integrador.modules.teacher.repository.TeacherRepository;
import com.projeto_integrador.projeto_integrador.modules.subjects.entity.SubjectEntity;
import com.projeto_integrador.projeto_integrador.modules.subjects.repository.SubjectRepository;
import com.projeto_integrador.projeto_integrador.exceptions.UserFoundException;

import jakarta.persistence.EntityNotFoundException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.projeto_integrador.projeto_integrador.modules.teacher.usecases.CreateTeacher;

public class CreateTeacherTest {

    @InjectMocks
    private CreateTeacher createTeacher;

    @Mock
    private TeacherRepository teacherRepository;

    @Mock
    private SubjectRepository subjectRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void shouldCreateTeacherSuccessfully() {
        // Arrange
        TeacherEntity teacherEntity = new TeacherEntity();
        teacherEntity.setTeacherId(1L);
        teacherEntity.setTeacherName("João Silva");
        teacherEntity.setInstitutionalEmail("joao.silva@university.com");
        teacherEntity.setTeacherPassword("senha123");
        teacherEntity.setTeacherSubjects(Arrays.asList(new SubjectEntity(1L, "Math", null, null, null), new SubjectEntity()));

        // Mock do repositório para verificar se o e-mail institucional já existe
        when(teacherRepository.findByInstitutionalEmail(teacherEntity.getInstitutionalEmail()))
                .thenReturn(Optional.empty());

        // Mock do repositório de disciplinas para verificar se as disciplinas existem
        when(subjectRepository.findAllById(Arrays.asList(1L, 2L)))
                .thenReturn(Arrays.asList(new SubjectEntity(1L, "Math", null, null, null), new SubjectEntity()));

        // Mock do repositório de professores para salvar o professor
        when(teacherRepository.save(teacherEntity)).thenReturn(teacherEntity);

        // Act
        TeacherEntity result = createTeacher.execute(teacherEntity);

        // Assert
        verify(teacherRepository, times(1)).save(teacherEntity);
        assertEquals("João Silva", result.getTeacherName());
        assertEquals("joao.silva@university.com", result.getInstitutionalEmail());
    }

    @Test
    public void shouldThrowUserFoundExceptionWhenEmailAlreadyExists() {
        // Arrange
        TeacherEntity teacherEntity = new TeacherEntity();
        teacherEntity.setInstitutionalEmail("joao.silva@university.com");

        // Mock do repositório para verificar se o e-mail já existe
        when(teacherRepository.findByInstitutionalEmail(teacherEntity.getInstitutionalEmail()))
                .thenReturn(Optional.of(teacherEntity));

        // Act & Assert
        UserFoundException exception = assertThrows(UserFoundException.class, () -> {
            createTeacher.execute(teacherEntity);
        });

        assertEquals("User Found", exception.getMessage());
        verify(teacherRepository, never()).save(any());
    }

    @Test
    public void shouldThrowEntityNotFoundExceptionWhenSubjectsDoNotExist() {
        // Arrange
        TeacherEntity teacherEntity = new TeacherEntity();
        teacherEntity.setTeacherId(1L);
        teacherEntity.setTeacherName("João Silva");
        teacherEntity.setInstitutionalEmail("joao.silva@university.com");
        teacherEntity.setTeacherSubjects(Arrays.asList(new SubjectEntity(), new SubjectEntity(999L, "Invalid Subject", null, null, null)));

        // Mock do repositório para verificar se o e-mail institucional já existe
        when(teacherRepository.findByInstitutionalEmail(teacherEntity.getInstitutionalEmail()))
                .thenReturn(Optional.empty());

        // Mock do repositório de disciplinas para verificar que algumas disciplinas não existem
        when(subjectRepository.findAllById(Arrays.asList(1L, 999L)))
                .thenReturn(Arrays.asList(new SubjectEntity(1L, "Math", null, null, null))); // Não retornando a disciplina 999L

        // Act & Assert
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> {
            createTeacher.execute(teacherEntity);
        });

        assertEquals("Algumas disciplinas não foram encontradas.", exception.getMessage());
        verify(teacherRepository, never()).save(any());
    }
}
