package com.projeto_integrador.projeto_integrador.modules.schedule.usecases;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;

import com.projeto_integrador.projeto_integrador.modules.schedule.repository.ScheduleRepository;
import com.projeto_integrador.projeto_integrador.modules.schedule.usecases.DeleteScheduleById;

import jakarta.persistence.EntityNotFoundException;

class DeleteScheduleByIdTest {

    @InjectMocks
    private DeleteScheduleById deleteScheduleById;

    @Mock
    private ScheduleRepository scheduleRepository;

    @BeforeEach
    void setUp() {
        // Inicializa os mocks
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testExecute_Success() {
        // Simula que o schedule existe no banco
        Long id = 1L;
        when(scheduleRepository.existsById(id)).thenReturn(true);

        // Chama o método de delete
        deleteScheduleById.execute(id);

        // Verifica se o método deleteById foi chamado
        verify(scheduleRepository, times(1)).deleteById(id);
    }

    @Test
    void testExecute_ScheduleNotFound() {
        // Simula que o schedule não existe no banco
        Long id = 1L;
        when(scheduleRepository.existsById(id)).thenReturn(false);

        // Verifica se a exceção EntityNotFoundException é lançada
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> {
            deleteScheduleById.execute(id);
        });

        // Verifica se a mensagem da exceção está correta
        assertEquals("Schedule not found", exception.getMessage());

        // Verifica se o método deleteById não foi chamado
        verify(scheduleRepository, never()).deleteById(id);
    }
}
