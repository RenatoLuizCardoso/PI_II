package com.projeto_integrador.projeto_integrador.modules.subjects.usecases;

import com.projeto_integrador.projeto_integrador.modules.subjects.entity.SubjectEntity;
import com.projeto_integrador.projeto_integrador.modules.subjects.repository.SubjectRepository;
import com.projeto_integrador.projeto_integrador.modules.subjects.usecases.PutSubjectById;

import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)  // Suporte para injeção de mocks
class PutSubjectByIdTest {

    @Mock
    private SubjectRepository subjectRepository;  // Mock do repositório

    @InjectMocks
    private PutSubjectById putSubjectById;  // A classe que queremos testar

    @Test
    void testExecuteWhenSubjectExists() {
        // Criando o objeto SubjectEntity original
        SubjectEntity existingSubject = SubjectEntity.builder()
                                                     .subjectId(1L)
                                                     .subjectName("Math")
                                                     .subjectHours("40")
                                                     .build();

        // Criando o objeto SubjectEntity com os dados atualizados
        SubjectEntity updatedSubject = SubjectEntity.builder()
                                                    .subjectName("Mathematics")
                                                    .subjectHours("45")
                                                    .build();

        // Simulando que o findById encontra o subject com o id 1
        when(subjectRepository.findById(1L)).thenReturn(java.util.Optional.of(existingSubject));

        // Simulando o comportamento do save (para garantir que o método de salvamento foi chamado)
        when(subjectRepository.save(any(SubjectEntity.class))).thenReturn(updatedSubject);

        // Executando o caso de uso
        SubjectEntity result = putSubjectById.execute(1L, updatedSubject);

        // Verificando se o retorno é o que esperamos
        assertNotNull(result);
        assertEquals("Mathematics", result.getSubjectName());
        assertEquals("45", result.getSubjectHours());
        
        // Verificando se o método findById foi chamado corretamente
        verify(subjectRepository, times(1)).findById(1L);
        verify(subjectRepository, times(1)).save(any(SubjectEntity.class));
    }

    @Test
    void testExecuteWhenSubjectNotFound() {
        // Simulando que o findById não encontrou nada para o id 1
        when(subjectRepository.findById(1L)).thenReturn(java.util.Optional.empty());

        // Executando o caso de uso e verificando se a exceção é lançada
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> {
            putSubjectById.execute(1L, new SubjectEntity());
        });

        // Verificando a mensagem da exceção
        assertEquals("Subject not found", exception.getMessage());

        // Verificando se o método findById foi chamado
        verify(subjectRepository, times(1)).findById(1L);
        verify(subjectRepository, times(0)).save(any(SubjectEntity.class)); // save não deve ser chamado
    }
}
