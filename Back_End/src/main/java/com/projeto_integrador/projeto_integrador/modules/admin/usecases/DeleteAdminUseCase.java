package com.projeto_integrador.projeto_integrador.modules.admin.usecases;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.projeto_integrador.projeto_integrador.modules.admin.repository.AdminRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class DeleteAdminUseCase {
    
    @Autowired
    AdminRepository repository;
    
    public void execute(Long id) {
        if (this.repository.existsById(id)) {
            this.repository.deleteById(id);
        } else {
            throw new EntityNotFoundException("Admin not found");
        }
    }
}
