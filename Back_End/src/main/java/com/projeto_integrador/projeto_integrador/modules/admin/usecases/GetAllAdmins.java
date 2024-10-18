package com.projeto_integrador.projeto_integrador.modules.admin.usecases;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.projeto_integrador.projeto_integrador.modules.admin.entity.AdminEntity;
import com.projeto_integrador.projeto_integrador.modules.admin.repository.AdminRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class GetAllAdmins {
    
    @Autowired
    AdminRepository repository;

    public List<AdminEntity> execute() {
        var allAdmins = repository.findAll();
        if (allAdmins.isEmpty()) {
            throw new EntityNotFoundException("Admin not Register");
        }
        return allAdmins;
    }
}
