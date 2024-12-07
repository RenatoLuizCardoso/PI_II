package com.projeto_integrador.projeto_integrador.modules.subjects.usecases;

import com.projeto_integrador.projeto_integrador.modules.subjects.entity.SubjectEntity;
import com.projeto_integrador.projeto_integrador.modules.subjects.repository.SubjectRepository;
import com.projeto_integrador.projeto_integrador.modules.subjects.usecases.CreateSubject;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CreateSubjectTest {

    @Mock
    private SubjectRepository subjectRepository;

    @InjectMocks
    private CreateSubject createSubject;

    private SubjectEntity subjectEntity;

    @BeforeEach
    void setUp() {
        // Inicializa o mock do Mockito
        MockitoAnnotations.openMocks(this);

        // Cria um objeto SubjectEntity para testes
        subjectEntity = new SubjectEntity();
        subjectEntity.setSubjectName("Mathematics");
        subjectEntity.setSubjectHours("120");
    }

    @Test
    void testExecute() {
        // Definindo o comportamento do mock
        when(subjectRepository.save(subjectEntity)).thenReturn(subjectEntity);

        // Executando o método do caso de uso
        SubjectEntity result = createSubject.execute(subjectEntity);

        // Verificando se o repositório foi chamado corretamente
        verify(subjectRepository, times(1)).save(subjectEntity);

        // Asserts: verificando se a resposta é a esperada
        assertNotNull(result);
        assertEquals("Mathematics", result.getSubjectName());
        assertEquals("120", result.getSubjectHours());
    }
}
