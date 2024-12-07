package com.projeto_integrador.projeto_integrador.modules.courses.usecases;

import com.projeto_integrador.projeto_integrador.modules.courses.entity.CourseEntity;
import com.projeto_integrador.projeto_integrador.modules.courses.repository.CourseRepository;
import com.projeto_integrador.projeto_integrador.modules.courses.usecases.PutCourseById;

import jakarta.persistence.EntityNotFoundException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

import com.projeto_integrador.projeto_integrador.modules.subjects.entity.SubjectEntity;

@ExtendWith(MockitoExtension.class)
public class PutCourseByIdTest {

    @Mock
    private CourseRepository courseRepository;

    @InjectMocks
    private PutCourseById putCourseById;

    @BeforeEach
    public void setUp() {
        // Mockito cuidará da injeção das dependências
    }

    @Test
    @DisplayName("Should update and return CourseEntity when ID exists")
    public void shouldUpdateCourseWhenIdExists() {
        // Arrange
        Long courseId = 1L;

        // Curso existente (antes da atualização)
        CourseEntity existingCourse = new CourseEntity();
        existingCourse.setCourseId(courseId);
        existingCourse.setCourseName("Old Course");
        existingCourse.setCourseSemester("1");
        existingCourse.setCoursePeriod("Morning");

        // Criando as matérias com o construtor padrão e definindo as propriedades
        SubjectEntity subject1 = new SubjectEntity();
        subject1.setSubjectId(1L);
        subject1.setSubjectName("Subject 1");

        SubjectEntity subject2 = new SubjectEntity();
        subject2.setSubjectId(2L);
        subject2.setSubjectName("Subject 2");

        // Definindo a lista de matérias no curso
        existingCourse.setCourseSubjects(List.of(subject1, subject2));

        // Novos dados para atualizar o curso
        CourseEntity updatedCourseData = new CourseEntity();
        updatedCourseData.setCourseName("Updated Course");
        updatedCourseData.setCourseSemester("2");
        updatedCourseData.setCoursePeriod("Afternoon");
        updatedCourseData.setCourseSubjects(List.of(subject1, subject2));  // Mantém as mesmas matérias

        // Simulando o comportamento do repositório
        when(courseRepository.findById(courseId)).thenReturn(Optional.of(existingCourse));
        when(courseRepository.save(any(CourseEntity.class))).thenReturn(updatedCourseData);

        // Act
        CourseEntity result = putCourseById.execute(courseId, updatedCourseData);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getCourseName()).isEqualTo("Updated Course");
        assertThat(result.getCourseSemester()).isEqualTo("2");
        assertThat(result.getCoursePeriod()).isEqualTo("Afternoon");
        assertThat(result.getCourseSubjects()).isEqualTo(List.of(subject1, subject2));

        // Verifica se os métodos foram chamados o número esperado de vezes
        verify(courseRepository, times(1)).findById(courseId); // findById foi chamado uma vez
        verify(courseRepository, times(1)).save(updatedCourseData); // save foi chamado uma vez
    }

    @Test
    @DisplayName("Should throw EntityNotFoundException when ID does not exist")
    public void shouldThrowExceptionWhenIdDoesNotExist() {
        // Arrange
        Long courseId = 1L;
        CourseEntity updatedCourseData = new CourseEntity();
        updatedCourseData.setCourseName("Updated Course");

        // Simula que o curso não foi encontrado
        when(courseRepository.findById(courseId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThatThrownBy(() -> putCourseById.execute(courseId, updatedCourseData))
            .isInstanceOf(EntityNotFoundException.class)
            .hasMessage("Course not found"); // Verifica a mensagem de erro

        // Verifica que o método save nunca foi chamado
        verify(courseRepository, times(1)).findById(courseId);  // findById foi chamado uma vez
        verify(courseRepository, never()).save(any(CourseEntity.class)); // save nunca foi chamado
    }
}
