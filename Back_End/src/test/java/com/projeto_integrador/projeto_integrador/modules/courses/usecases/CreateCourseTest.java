package com.projeto_integrador.projeto_integrador.modules.courses.usecases;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.anyList;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;

import com.projeto_integrador.projeto_integrador.modules.courses.entity.CourseEntity;
import com.projeto_integrador.projeto_integrador.modules.courses.repository.CourseRepository;
import com.projeto_integrador.projeto_integrador.modules.courses.usecases.CreateCourse;
import com.projeto_integrador.projeto_integrador.modules.subjects.entity.SubjectEntity;
import com.projeto_integrador.projeto_integrador.modules.subjects.repository.SubjectRepository;

class CreateCourseTest {

    @InjectMocks
    private CreateCourse createCourse;

    @Mock
    private CourseRepository courseRepository;

    @Mock
    private SubjectRepository subjectRepository; // Mock do repositório de Subject

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testExecute_ValidCourse_Success() {
        // Arrange
        CourseEntity courseEntity = new CourseEntity();
        
        // Criando subjects de exemplo
        SubjectEntity subject1 = new SubjectEntity(1L, "Mathematics", null, null, null);
        SubjectEntity subject2 = new SubjectEntity(2L, "Physics", null, null, null);

        // Definindo os subjects válidos para o curso
        List<SubjectEntity> subjects = Arrays.asList(subject1, subject2);
        courseEntity.setCourseSubjects(subjects);

        // Mock do repositório de subject para retornar os subjects válidos
        when(subjectRepository.findAllById(anyList())).thenReturn(subjects);

        // Mock do repositório para salvar o curso
        when(courseRepository.save(courseEntity)).thenReturn(courseEntity);

        // Act
        CourseEntity result = createCourse.execute(courseEntity);

        // Assert
        verify(subjectRepository).findAllById(courseEntity.getCourseSubjects().stream().map(SubjectEntity::getSubjectId).toList());
        verify(courseRepository).save(courseEntity);
        assertEquals(courseEntity, result); // Verifica se o resultado é igual ao curso enviado
    }

    @Test
    void testExecute_InvalidSubjects_ThrowsException() {
        // Arrange
        CourseEntity courseEntity = new CourseEntity();
        SubjectEntity subject1 = new SubjectEntity(1L, "Mathematics", null, null, null);

        // Tentando adicionar um subject inválido (não encontrado no repositório)
        List<SubjectEntity> subjects = Arrays.asList(subject1);
        courseEntity.setCourseSubjects(subjects);

        // Mock do repositório de subject para retornar uma lista vazia (nenhum subject encontrado)
        when(subjectRepository.findAllById(anyList())).thenReturn(Arrays.asList());

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> createCourse.execute(courseEntity));
        verify(courseRepository, never()).save(courseEntity); // O repositório não deve ser chamado
    }
}
