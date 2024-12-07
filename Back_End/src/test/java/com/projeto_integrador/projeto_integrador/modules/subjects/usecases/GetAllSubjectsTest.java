package com.projeto_integrador.projeto_integrador.modules.subjects.usecases;

import com.projeto_integrador.projeto_integrador.modules.subjects.entity.SubjectEntity;
import com.projeto_integrador.projeto_integrador.modules.subjects.repository.SubjectRepository;
import com.projeto_integrador.projeto_integrador.modules.subjects.usecases.GetAllSubjects;

import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)  // Suporte para injeção de mocks
class GetAllSubjectsTest {

    @Mock
    private SubjectRepository subjectRepository;  // Mock do repositório

    @InjectMocks
    private GetAllSubjects getAllSubjects;  // A classe que queremos testar

    @Test
    void testExecuteWithSubjects() {
        // Criando um objeto de teste
        SubjectEntity subject = SubjectEntity.builder()
                                             .subjectId(1L)
                                             .subjectName("Math")
                                             .subjectHours("40")
                                             .build();

        // Simulando o comportamento do findAll() do repositório
        when(subjectRepository.findAll()).thenReturn(List.of(subject));  // Retorna uma lista com o subject

        // Executando o caso de uso
        List<SubjectEntity> result = getAllSubjects.execute();

        // Verificando se o retorno tem o tamanho esperado (1 matéria)
        assertEquals(1, result.size());

        // Verificando se a matéria retornada é a que esperamos
        assertEquals("Math", result.get(0).getSubjectName());
        assertEquals("40", result.get(0).getSubjectHours());

        // Verificando se o método findAll foi chamado
        verify(subjectRepository, times(1)).findAll();
    }

    @Test
    void testExecuteWhenNoSubjectsFound() {
        // Simulando que não há matérias cadastradas no repositório
        when(subjectRepository.findAll()).thenReturn(List.of());

        // Executando o caso de uso e verificando se a exceção é lançada
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> {
            getAllSubjects.execute();  // Executa o método que deve lançar a exceção
        });

        // Verificando a mensagem da exceção
        assertEquals("Subject not Registered", exception.getMessage());

        // Verificando se o método findAll foi chamado
        verify(subjectRepository, times(1)).findAll();
    }
}
