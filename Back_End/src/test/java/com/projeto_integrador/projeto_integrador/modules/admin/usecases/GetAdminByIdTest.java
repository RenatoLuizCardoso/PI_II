package com.projeto_integrador.projeto_integrador.modules.admin.usecases;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
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

import com.projeto_integrador.projeto_integrador.modules.admin.entity.AdminEntity;
import com.projeto_integrador.projeto_integrador.modules.admin.repository.AdminRepository;

import jakarta.persistence.EntityNotFoundException;

@ExtendWith(MockitoExtension.class)
public class GetAdminByIdTest {

    @Mock
    private AdminRepository repository;

    @InjectMocks
    private GetAdminById getAdminById;

    @BeforeEach
    public void setUp() {
    }

    @Test
    @DisplayName("Should return AdminEntity when ID exists")
    public void shouldReturnAdminWhenIdExists() {
        Long adminId = 1L;
        AdminEntity admin = new AdminEntity();
        admin.setAdminId(adminId);
        admin.setAdminEmail("admin@example.com");

        when(repository.findById(adminId)).thenReturn(Optional.of(admin));

        AdminEntity result = getAdminById.execute(adminId);

        assertThat(result).isNotNull();
        assertThat(result.getAdminId()).isEqualTo(adminId);
        assertThat(result.getAdminEmail()).isEqualTo("admin@example.com");
        verify(repository, times(1)).findById(adminId);
    }

    @Test
    @DisplayName("Should throw EntityNotFoundException when ID does not exist")
    public void shouldThrowExceptionWhenIdDoesNotExist() {
        Long adminId = 1L;
        when(repository.findById(adminId)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> getAdminById.execute(adminId))
            .isInstanceOf(EntityNotFoundException.class)
            .hasMessage("Admin not found");

        verify(repository, times(1)).findById(adminId);
    }
}
