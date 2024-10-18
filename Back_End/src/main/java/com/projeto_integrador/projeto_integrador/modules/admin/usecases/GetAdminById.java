package com.projeto_integrador.projeto_integrador.modules.admin.usecases;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import com.projeto_integrador.projeto_integrador.modules.admin.entity.AdminEntity;
import com.projeto_integrador.projeto_integrador.modules.admin.repository.AdminRepository;


import jakarta.persistence.EntityNotFoundException;

@Service
public class GetAdminById {
    
    @Autowired
    AdminRepository repository;
    
    public AdminEntity execute(@PathVariable Long id) {
        return this.repository.findById(id).orElseThrow(
            () -> new EntityNotFoundException("Admin not found")
        );
    }
}
