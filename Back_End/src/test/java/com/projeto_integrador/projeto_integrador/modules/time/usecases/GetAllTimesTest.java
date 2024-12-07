package com.projeto_integrador.projeto_integrador.modules.time.usecases;

import com.projeto_integrador.projeto_integrador.modules.time.entity.TimeEntity;
import com.projeto_integrador.projeto_integrador.modules.time.repository.TimeRepository;
import com.projeto_integrador.projeto_integrador.modules.time.usecases.GetAllTimes;

import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class GetAllTimesTest {

    @Mock
    private TimeRepository timeRepository;

    @InjectMocks
    private GetAllTimes getAllTimes;

    private TimeEntity timeEntity;

    @BeforeEach
    public void setUp() {
        // Criar uma instância de TimeEntity para os testes
        timeEntity = TimeEntity.builder()
                               .timeId(1L)
                               .startTime("08:00")
                               .endTime("17:00")
                               .build();
    }

    @Test
    public void testExecute_whenTimesExist() {
        // Configura o comportamento do repositório para retornar uma lista com Times
        when(timeRepository.findAll()).thenReturn(List.of(timeEntity));

        // Chama o método a ser testado
        List<TimeEntity> result = getAllTimes.execute();

        // Verifica se o retorno contém a lista de times
        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        assertEquals(timeEntity, result.get(0));

        // Verifica se o método findAll foi chamado
        verify(timeRepository, times(1)).findAll();
    }

    @Test
    public void testExecute_whenNoTimesExist() {
        // Configura o comportamento do repositório para retornar uma lista vazia
        when(timeRepository.findAll()).thenReturn(Collections.emptyList());

        // Executa e verifica se a exceção é lançada
        EntityNotFoundException thrown = assertThrows(EntityNotFoundException.class, () -> {
            getAllTimes.execute();
        });

        // Verifica a mensagem da exceção
        assertEquals("Time not Registered", thrown.getMessage());

        // Verifica se o método findAll foi chamado
        verify(timeRepository, times(1)).findAll();
    }
}
