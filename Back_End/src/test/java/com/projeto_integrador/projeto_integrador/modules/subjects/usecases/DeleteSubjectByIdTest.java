package com.projeto_integrador.projeto_integrador.modules.subjects.usecases;

import com.projeto_integrador.projeto_integrador.modules.subjects.repository.SubjectRepository;
import com.projeto_integrador.projeto_integrador.modules.subjects.usecases.DeleteSubjectById;

import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class) // Isso permite que o Mockito injete os mocks automaticamente
class DeleteSubjectByIdTest {

    @Mock
    private SubjectRepository subjectRepository; // Mock da dependência SubjectRepository

    @InjectMocks
    private DeleteSubjectById deleteSubjectById; // A dependência é injetada aqui

    private Long subjectId = 1L; // ID fictício para o teste

    @Test
    void testExecuteSubjectExists() {
        // Simula que o subject existe
        when(subjectRepository.existsById(subjectId)).thenReturn(true);

        // Executa o caso de uso
        deleteSubjectById.execute(subjectId);

        // Verifica se o repositório tentou excluir o subject
        verify(subjectRepository, times(1)).deleteById(subjectId);
    }

    @Test
    void testExecuteSubjectNotFound() {
        // Simula que o subject não existe
        when(subjectRepository.existsById(subjectId)).thenReturn(false);

        // Executa o caso de uso e verifica se a exceção é lançada
        try {
            deleteSubjectById.execute(subjectId);
        } catch (EntityNotFoundException e) {
            // Verifica se a mensagem da exceção é a esperada
            assertEquals("Subject not found", e.getMessage());
        }

        // Verifica se o método deleteById não foi chamado
        verify(subjectRepository, never()).deleteById(subjectId);
    }
}
