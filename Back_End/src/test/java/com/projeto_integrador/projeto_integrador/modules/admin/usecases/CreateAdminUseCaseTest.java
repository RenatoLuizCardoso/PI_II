package com.projeto_integrador.projeto_integrador.modules.admin.usecases;

import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.*;

import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.projeto_integrador.projeto_integrador.exceptions.UserFoundException;
import com.projeto_integrador.projeto_integrador.modules.admin.entity.AdminEntity;
import com.projeto_integrador.projeto_integrador.modules.admin.repository.AdminRepository;

@ExtendWith(MockitoExtension.class)
public class CreateAdminUseCaseTest {

    @InjectMocks
    private CreateAdminUseCase createAdminUseCase;

    @Mock
    private AdminRepository adminRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Test
    @DisplayName("Should throw UserFoundException if admin email already exists")
    public void should_throw_exception_if_email_already_exists() {
        var existingAdmin = new AdminEntity();
        existingAdmin.setAdminEmail("admin@example.com");

        var newAdmin = new AdminEntity();
        newAdmin.setAdminEmail("admin@example.com");
        newAdmin.setAdminPassword("password123");

        when(adminRepository.findByAdminEmail("admin@example.com"))
            .thenReturn(Optional.of(existingAdmin));

        assertThatThrownBy(() -> createAdminUseCase.execute(newAdmin))
            .isInstanceOf(UserFoundException.class);

        verify(adminRepository, never()).save(any());
    }

    @Test
    @DisplayName("Should encode password and save new admin when email is not found")
    public void should_encode_password_and_save_new_admin() {
        var newAdmin = new AdminEntity();
        newAdmin.setAdminEmail("admin@example.com");
        newAdmin.setAdminPassword("password123");

        when(adminRepository.findByAdminEmail("admin@example.com"))
            .thenReturn(Optional.empty());

        when(passwordEncoder.encode("password123"))
            .thenReturn("encodedPassword");

        when(adminRepository.save(any(AdminEntity.class)))
            .thenAnswer(invocation -> invocation.getArgument(0));

        AdminEntity result = createAdminUseCase.execute(newAdmin);

        assertThat(result).isNotNull();
        assertThat(result.getAdminPassword()).isEqualTo("encodedPassword");
        
        verify(adminRepository).save(newAdmin);
        verify(passwordEncoder).encode("password123");
    }
}
