package com.projeto_integrador.projeto_integrador.modules.admin.controller;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import com.projeto_integrador.projeto_integrador.modules.admin.entity.AdminEntity;
import com.projeto_integrador.projeto_integrador.modules.admin.usecases.CreateAdminUseCase;
import com.projeto_integrador.projeto_integrador.modules.admin.usecases.DeleteAdminUseCase;
import com.projeto_integrador.projeto_integrador.modules.admin.usecases.GetAdminById;
import com.projeto_integrador.projeto_integrador.modules.admin.usecases.GetAllAdmins;
import com.projeto_integrador.projeto_integrador.modules.admin.usecases.PutAdminById;

import jakarta.persistence.EntityNotFoundException;

public class AdminControllerTest {

    @InjectMocks
    private AdminController adminController;

    @Mock
    private CreateAdminUseCase createAdmin;

    @Mock
    private GetAllAdmins getAllAdmins;

    @Mock
    private GetAdminById getAdminById;

    @Mock
    private PutAdminById putAdminById;

    @Mock
    private DeleteAdminUseCase deleteAdminById;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void shouldCreateAdminSuccessfully() {
        AdminEntity adminEntity = new AdminEntity();
        when(createAdmin.execute(adminEntity)).thenReturn(adminEntity);

        ResponseEntity<Object> response = adminController.create(adminEntity);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(adminEntity, response.getBody());
    }

    @Test
    public void shouldReturnAllAdmins() {
        List<AdminEntity> adminList = List.of(new AdminEntity(), new AdminEntity());
        when(getAllAdmins.execute()).thenReturn(adminList);

        ResponseEntity<Object> response = adminController.getAllAdmins();

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(adminList, response.getBody());
    }

    @Test
    public void shouldReturnAdminById() {
        AdminEntity adminEntity = new AdminEntity();
        when(getAdminById.execute(1L)).thenReturn(adminEntity);

        ResponseEntity<Object> response = adminController.getById(1L);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(adminEntity, response.getBody());
    }

    @Test
    public void shouldReturnNotFoundWhenAdminNotFoundById() {
        when(getAdminById.execute(1L)).thenThrow(new EntityNotFoundException("Admin not found"));

        // Verificando se o status 404 é retornado
        ResponseEntity<Object> response = adminController.getById(1L);

        assertEquals(404, response.getStatusCodeValue());
        assertEquals("Admin not found", response.getBody());
    }

    @Test
    public void shouldUpdateAdminSuccessfully() {
        AdminEntity adminEntity = new AdminEntity();
        when(putAdminById.execute(1L, adminEntity)).thenReturn(adminEntity);

        ResponseEntity<Object> response = adminController.putAdmin(adminEntity, 1L);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(adminEntity, response.getBody());
    }

    @Test
    public void shouldDeleteAdminSuccessfully() {
        doNothing().when(deleteAdminById).execute(1L);

        ResponseEntity<Object> response = adminController.deleteAdmin(1L);

        assertEquals(200, response.getStatusCodeValue());
        verify(deleteAdminById, times(1)).execute(1L);
    }
}
