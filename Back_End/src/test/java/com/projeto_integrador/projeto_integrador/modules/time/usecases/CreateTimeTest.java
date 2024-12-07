package com.projeto_integrador.projeto_integrador.modules.time.usecases;

import com.projeto_integrador.projeto_integrador.modules.time.entity.TimeEntity;
import com.projeto_integrador.projeto_integrador.modules.time.repository.TimeRepository;
import com.projeto_integrador.projeto_integrador.modules.time.usecases.CreateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CreateTimeTest {

    @Mock
    private TimeRepository timeRepository;

    @InjectMocks
    private CreateTime createTime;

    private TimeEntity timeEntity;

    @BeforeEach
    public void setUp() {
        // Criar um TimeEntity de exemplo para os testes
        timeEntity = TimeEntity.builder()
                               .timeId(1L)
                               .startTime("08:00")
                               .endTime("17:00")
                               .build();
    }

    @Test
    public void testExecute() {
        // Configura o comportamento do repositório mockado
        when(timeRepository.save(timeEntity)).thenReturn(timeEntity);

        // Chama o método que está sendo testado
        TimeEntity result = createTime.execute(timeEntity);

        // Verifica se o retorno do método é o esperado
        assertEquals(timeEntity, result);
    }
}
