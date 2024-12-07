package com.projeto_integrador.projeto_integrador.modules.subjects.usecases;

import com.projeto_integrador.projeto_integrador.modules.subjects.entity.SubjectEntity;
import com.projeto_integrador.projeto_integrador.modules.subjects.repository.SubjectRepository;
import com.projeto_integrador.projeto_integrador.modules.subjects.usecases.GetSubjectById;

import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)  // Suporte para injeção de mocks
class GetSubjectByIdTest {

    @Mock
    private SubjectRepository subjectRepository;  // Mock do repositório

    @InjectMocks
    private GetSubjectById getSubjectById;  // A classe que queremos testar

    @Test
    void testExecuteWhenSubjectExists() {
        // Criando um objeto de teste
        SubjectEntity subject = SubjectEntity.builder()
                                             .subjectId(1L)
                                             .subjectName("Math")
                                             .subjectHours("40")
                                             .build();

        // Simulando o comportamento do findById() do repositório
        when(subjectRepository.findById(1L)).thenReturn(java.util.Optional.of(subject));

        // Executando o caso de uso
        SubjectEntity result = getSubjectById.execute(1L);

        // Verificando se o retorno é o que esperamos
        assertNotNull(result);
        assertEquals(1L, result.getSubjectId());
        assertEquals("Math", result.getSubjectName());
        assertEquals("40", result.getSubjectHours());

        // Verificando se o método findById foi chamado
        verify(subjectRepository, times(1)).findById(1L);
    }

    @Test
    void testExecuteWhenSubjectNotFound() {
        // Simulando que o método findById não encontrou nada para o id 1
        when(subjectRepository.findById(1L)).thenReturn(java.util.Optional.empty());

        // Executando o caso de uso e verificando se a exceção é lançada
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> {
            getSubjectById.execute(1L);  // Executa o método que deve lançar a exceção
        });

        // Verificando a mensagem da exceção
        assertEquals("Subject not found", exception.getMessage());

        // Verificando se o método findById foi chamado
        verify(subjectRepository, times(1)).findById(1L);
    }
}
