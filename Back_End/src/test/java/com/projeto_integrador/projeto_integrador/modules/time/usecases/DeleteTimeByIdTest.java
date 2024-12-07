package com.projeto_integrador.projeto_integrador.modules.time.usecases;



import com.projeto_integrador.projeto_integrador.modules.time.repository.TimeRepository;
import com.projeto_integrador.projeto_integrador.modules.time.usecases.DeleteTimeById;

import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class DeleteTimeByIdTest {

    @Mock
    private TimeRepository timeRepository;

    @InjectMocks
    private DeleteTimeById deleteTimeById;

    private Long validId;
    private Long invalidId;

    @BeforeEach
    public void setUp() {
        validId = 1L;
        invalidId = 999L;
    }

    @Test
    public void testExecute_whenTimeExists() {
        // Configura o comportamento do repositório para o ID válido
        when(timeRepository.existsById(validId)).thenReturn(true);

        // Chama o método a ser testado
        deleteTimeById.execute(validId);

        // Verifica se o repositório tentou excluir o registro com o ID válido
        verify(timeRepository, times(1)).deleteById(validId);
    }

    @Test
    public void testExecute_whenTimeDoesNotExist() {
        // Configura o comportamento do repositório para o ID inválido
        when(timeRepository.existsById(invalidId)).thenReturn(false);

        // Executa e verifica se a exceção é lançada
        try {
            deleteTimeById.execute(invalidId);
        } catch (EntityNotFoundException e) {
            // Verifica se a exceção foi lançada com a mensagem correta
            assertEquals("Time not found", e.getMessage());
        }

        // Verifica se o repositório não tentou excluir nada
        verify(timeRepository, times(0)).deleteById(invalidId);
    }
}
