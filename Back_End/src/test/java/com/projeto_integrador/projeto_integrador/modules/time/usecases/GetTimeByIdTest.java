package com.projeto_integrador.projeto_integrador.modules.time.usecases;

import com.projeto_integrador.projeto_integrador.modules.time.entity.TimeEntity;
import com.projeto_integrador.projeto_integrador.modules.time.repository.TimeRepository;
import com.projeto_integrador.projeto_integrador.modules.time.usecases.GetTimeById;

import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class GetTimeByIdTest {

    @Mock
    private TimeRepository timeRepository;

    @InjectMocks
    private GetTimeById getTimeById;

    private TimeEntity timeEntity;
    private Long validId;
    private Long invalidId;

    @BeforeEach
    public void setUp() {
        // Criar uma instância de TimeEntity para os testes
        timeEntity = TimeEntity.builder()
                               .timeId(1L)
                               .startTime("08:00")
                               .endTime("17:00")
                               .build();

        validId = 1L;   // ID válido
        invalidId = 999L;  // ID inválido
    }

    @Test
    public void testExecute_whenTimeExists() {
        // Configura o comportamento do repositório para retornar o TimeEntity com o ID válido
        when(timeRepository.findById(validId)).thenReturn(Optional.of(timeEntity));

        // Chama o método a ser testado
        TimeEntity result = getTimeById.execute(validId);

        // Verifica se o resultado não é nulo e é o mesmo que o timeEntity configurado
        assertNotNull(result);
        assertEquals(timeEntity, result);

        // Verifica se o método findById foi chamado com o ID válido
        verify(timeRepository, times(1)).findById(validId);
    }

    @Test
    public void testExecute_whenTimeDoesNotExist() {
        // Configura o comportamento do repositório para retornar um Optional vazio (não encontrado)
        when(timeRepository.findById(invalidId)).thenReturn(Optional.empty());

        // Executa e verifica se a exceção é lançada
        EntityNotFoundException thrown = assertThrows(EntityNotFoundException.class, () -> {
            getTimeById.execute(invalidId);
        });

        // Verifica a mensagem da exceção
        assertEquals("Time not found", thrown.getMessage());

        // Verifica se o método findById foi chamado com o ID inválido
        verify(timeRepository, times(1)).findById(invalidId);
    }
}
