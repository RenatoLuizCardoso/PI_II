package com.projeto_integrador.projeto_integrador.modules.admin.usecases;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.projeto_integrador.projeto_integrador.modules.admin.entity.AdminEntity;
import com.projeto_integrador.projeto_integrador.modules.admin.repository.AdminRepository;


import jakarta.persistence.EntityNotFoundException;

@Service
public class PutAdminById {

    @Autowired
    AdminRepository repository;
    
    public AdminEntity execute(Long id, AdminEntity adminEntity) {
        AdminEntity updateAdmin = this.repository.findById(id).orElseThrow(
            () -> new EntityNotFoundException("Admin not found")
        );
        updateAdmin.setAdminName(adminEntity.getAdminName());
        updateAdmin.setAdminEmail(adminEntity.getAdminEmail());
        updateAdmin.setAdminPassword(adminEntity.getAdminPassword());

        return this.repository.save(updateAdmin);
    }
}
