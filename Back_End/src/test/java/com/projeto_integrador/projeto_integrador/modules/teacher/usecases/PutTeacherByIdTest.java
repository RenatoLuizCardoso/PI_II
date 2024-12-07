package com.projeto_integrador.projeto_integrador.modules.teacher.usecases;

import com.projeto_integrador.projeto_integrador.modules.teacher.entity.TeacherEntity;
import com.projeto_integrador.projeto_integrador.modules.teacher.repository.TeacherRepository;
import com.projeto_integrador.projeto_integrador.modules.teacher.usecases.PutTeacherById;
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

public class PutTeacherByIdTest {

    @InjectMocks
    private PutTeacherById putTeacherById;

    @Mock
    private TeacherRepository teacherRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void shouldUpdateTeacherSuccessfully() {
        TeacherEntity existingTeacher = new TeacherEntity();
        existingTeacher.setTeacherId(1L);
        existingTeacher.setTeacherName("Old Name");

        TeacherEntity updatedTeacher = new TeacherEntity();
        updatedTeacher.setTeacherName("New Name");
        updatedTeacher.setTeacherSubjects(Arrays.asList());  // Exemplo de subject IDs

        // Mock para encontrar o professor existente no repositório
        when(teacherRepository.findById(1L)).thenReturn(Optional.of(existingTeacher));

        // Mock para salvar o professor atualizado
        when(teacherRepository.save(existingTeacher)).thenReturn(existingTeacher);

        // Atualizando o professor
        putTeacherById.execute(1L, updatedTeacher);

        // Verificando se o repositório foi chamado corretamente
        verify(teacherRepository, times(1)).save(existingTeacher);

        // Verificando se as propriedades foram atualizadas corretamente
        assertEquals("New Name", existingTeacher.getTeacherName());
        assertEquals(Arrays.asList(1L, 2L), existingTeacher.getTeacherSubjects());
    }

    @Test
    public void shouldThrowExceptionWhenTeacherNotFound() {
        TeacherEntity updatedTeacher = new TeacherEntity();
        updatedTeacher.setTeacherName("New Name");

        // Mock para simular que o professor não foi encontrado
        when(teacherRepository.findById(1L)).thenReturn(Optional.empty());

        // Verificando se a exceção correta é lançada
        Exception exception = assertThrows(EntityNotFoundException.class, 
                                           () -> putTeacherById.execute(1L, updatedTeacher));

        // Verificando a mensagem da exceção
        assertEquals("Teacher not found", exception.getMessage());

        // Verificando que o repositório não foi chamado para salvar
        verify(teacherRepository, times(1)).findById(1L);
        verify(teacherRepository, never()).save(any());
    }

    @Test
    public void shouldThrowExceptionWhenSubjectsAreInvalid() {
        TeacherEntity existingTeacher = new TeacherEntity();
        existingTeacher.setTeacherId(1L);
        existingTeacher.setTeacherName("Old Name");

        TeacherEntity updatedTeacher = new TeacherEntity();
        updatedTeacher.setTeacherName("New Name");
        updatedTeacher.setTeacherSubjects(Arrays.asList());  // Exemplo de subject IDs

      
        when(teacherRepository.findById(1L)).thenReturn(Optional.of(existingTeacher));

     
        if (updatedTeacher.getTeacherSubjects().contains(999L)) {
            throw new RuntimeException("Invalid subjects");
        }

        // Verificando se a exceção foi corretamente lançada
        Exception exception = assertThrows(RuntimeException.class, 
                                           () -> putTeacherById.execute(1L, updatedTeacher));

        // Verificando a mensagem de erro
        assertEquals("Invalid subjects", exception.getMessage());

        // Verificando que o repositório de professores não foi chamado para salvar
        verify(teacherRepository, never()).save(any());
    }
}
