package com.projeto_integrador.projeto_integrador.modules.time.usecases;

import com.projeto_integrador.projeto_integrador.modules.time.entity.TimeEntity;
import com.projeto_integrador.projeto_integrador.modules.time.repository.TimeRepository;
import com.projeto_integrador.projeto_integrador.modules.time.usecases.PutTimeById;

import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PutTimeByIdTest {

    @Mock
    private TimeRepository timeRepository;

    @InjectMocks
    private PutTimeById putTimeById;

    private TimeEntity existingTimeEntity;
    private TimeEntity updatedTimeEntity;
    private Long validId;
    private Long invalidId;

    @BeforeEach
    public void setUp() {
        // Criar uma instância de TimeEntity para os testes
        existingTimeEntity = TimeEntity.builder()
                                       .timeId(1L)
                                       .startTime("08:00")
                                       .endTime("17:00")
                                       .build();

        updatedTimeEntity = TimeEntity.builder()
                                      .timeId(1L)
                                      .startTime("09:00")
                                      .endTime("18:00")
                                      .build();

        validId = 1L;   // ID válido
        invalidId = 999L;  // ID inválido
    }

    @Test
    public void testExecute_whenTimeExists() {
        // Configura o comportamento do repositório para retornar o TimeEntity existente
        when(timeRepository.findById(validId)).thenReturn(java.util.Optional.of(existingTimeEntity));
        when(timeRepository.save(any(TimeEntity.class))).thenReturn(updatedTimeEntity);

        // Chama o método a ser testado
        TimeEntity result = putTimeById.execute(validId, updatedTimeEntity);

        // Verifica se o resultado não é nulo e se o TimeEntity foi atualizado corretamente
        assertNotNull(result);
        assertEquals(updatedTimeEntity.getStartTime(), result.getStartTime());
        assertEquals(updatedTimeEntity.getEndTime(), result.getEndTime());

        // Verifica se o método findById foi chamado
        verify(timeRepository, times(1)).findById(validId);
        // Verifica se o método save foi chamado para salvar o TimeEntity atualizado
        verify(timeRepository, times(1)).save(updatedTimeEntity);
    }

    @Test
    public void testExecute_whenTimeDoesNotExist() {
        // Configura o comportamento do repositório para retornar um Optional vazio (não encontrado)
        when(timeRepository.findById(invalidId)).thenReturn(java.util.Optional.empty());

        // Executa e verifica se a exceção é lançada
        EntityNotFoundException thrown = assertThrows(EntityNotFoundException.class, () -> {
            putTimeById.execute(invalidId, updatedTimeEntity);
        });

        // Verifica a mensagem da exceção
        assertEquals("Time not found", thrown.getMessage());

        // Verifica se o método findById foi chamado
        verify(timeRepository, times(1)).findById(invalidId);
        // Não deve ter chamado o save, pois o TimeEntity não foi encontrado
        verify(timeRepository, times(0)).save(any(TimeEntity.class));
    }
}
