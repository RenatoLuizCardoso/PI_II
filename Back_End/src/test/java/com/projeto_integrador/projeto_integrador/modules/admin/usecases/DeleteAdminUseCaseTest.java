package com.projeto_integrador.projeto_integrador.modules.admin.usecases;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

import com.projeto_integrador.projeto_integrador.modules.admin.repository.AdminRepository;

import jakarta.persistence.EntityNotFoundException;

@ExtendWith(MockitoExtension.class)
public class DeleteAdminUseCaseTest {

    @Mock
    private AdminRepository repository;

    @InjectMocks
    private DeleteAdminUseCase deleteAdminUseCase;

    @BeforeEach
    public void setUp() {

    }

    @Test
    @DisplayName("Should delete admin when ID exists")
    public void shouldDeleteAdminWhenIdExists() {
        Long adminId = 1L;
        when(repository.existsById(adminId)).thenReturn(true);

        deleteAdminUseCase.execute(adminId);

        verify(repository, times(1)).deleteById(adminId);
    }

    @Test
    @DisplayName("Should throw EntityNotFoundException when ID does not exist")
    public void shouldThrowExceptionWhenIdDoesNotExist() {
        Long adminId = 1L;
        when(repository.existsById(adminId)).thenReturn(false);

        assertThatThrownBy(() -> deleteAdminUseCase.execute(adminId))
            .isInstanceOf(EntityNotFoundException.class)
            .hasMessage("Admin not found");
    }
}
