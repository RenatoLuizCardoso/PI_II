package com.projeto_integrador.projeto_integrador.modules.teacher.usecases;


import com.projeto_integrador.projeto_integrador.modules.teacher.repository.TeacherRepository;
import com.projeto_integrador.projeto_integrador.modules.teacher.usecases.DeleteTeacherById;

import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class DeleteTeacherByIdTest {

    @InjectMocks
    private DeleteTeacherById deleteTeacherById;

    @Mock
    private TeacherRepository repository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void shouldDeleteTeacherWhenExists() {
        Long teacherId = 1L;

        when(repository.existsById(teacherId)).thenReturn(true);

        deleteTeacherById.execute(teacherId);

        verify(repository, times(1)).deleteById(teacherId);
    }

    @Test
    public void shouldThrowExceptionWhenTeacherNotFound() {
        Long teacherId = 1L;

        when(repository.existsById(teacherId)).thenReturn(false);

        Exception exception = assertThrows(EntityNotFoundException.class, () -> deleteTeacherById.execute(teacherId));

        assertEquals("Teacher not found", exception.getMessage());
        verify(repository, never()).deleteById(anyLong());
    }
}
