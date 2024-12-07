package com.projeto_integrador.projeto_integrador.modules.admin.usecases;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

import com.projeto_integrador.projeto_integrador.modules.admin.entity.AdminEntity;
import com.projeto_integrador.projeto_integrador.modules.admin.repository.AdminRepository;

import jakarta.persistence.EntityNotFoundException;

@ExtendWith(MockitoExtension.class)
public class PutAdminByIdTest {

    @Mock
    private AdminRepository repository;

    @InjectMocks
    private PutAdminById putAdminById;

    @BeforeEach
    public void setUp() {
    }

    @Test
    @DisplayName("Should update and return AdminEntity when ID exists")
    public void shouldUpdateAdminWhenIdExists() {
        Long adminId = 1L;
        AdminEntity existingAdmin = new AdminEntity();
        existingAdmin.setAdminId(adminId);
        existingAdmin.setAdminName("Old Name");
        existingAdmin.setAdminEmail("old.email@example.com");
        existingAdmin.setAdminPassword("oldPassword");

        AdminEntity updatedAdminData = new AdminEntity();
        updatedAdminData.setAdminName("New Name");
        updatedAdminData.setAdminEmail("new.email@example.com");
        updatedAdminData.setAdminPassword("newPassword");

        when(repository.findById(adminId)).thenReturn(Optional.of(existingAdmin));
        when(repository.save(any(AdminEntity.class))).thenReturn(existingAdmin);

        AdminEntity result = putAdminById.execute(adminId, updatedAdminData);

        assertThat(result).isNotNull();
        assertThat(result.getAdminName()).isEqualTo("New Name");
        assertThat(result.getAdminEmail()).isEqualTo("new.email@example.com");
        assertThat(result.getAdminPassword()).isEqualTo("newPassword");

        verify(repository, times(1)).findById(adminId);
        verify(repository, times(1)).save(existingAdmin);
    }

    @Test
    @DisplayName("Should throw EntityNotFoundException when ID does not exist")
    public void shouldThrowExceptionWhenIdDoesNotExist() {
        Long adminId = 1L;
        AdminEntity updatedAdminData = new AdminEntity();
        updatedAdminData.setAdminName("New Name");
        updatedAdminData.setAdminEmail("new.email@example.com");
        updatedAdminData.setAdminPassword("newPassword");

        when(repository.findById(adminId)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> putAdminById.execute(adminId, updatedAdminData))
            .isInstanceOf(EntityNotFoundException.class)
            .hasMessage("Admin not found");

        verify(repository, times(1)).findById(adminId);
        verify(repository, never()).save(any(AdminEntity.class)); 
    }
}
