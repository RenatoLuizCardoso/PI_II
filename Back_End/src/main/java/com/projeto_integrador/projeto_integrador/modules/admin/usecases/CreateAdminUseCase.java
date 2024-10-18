package com.projeto_integrador.projeto_integrador.modules.admin.usecases;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.projeto_integrador.projeto_integrador.exceptions.UserFoundException;
import com.projeto_integrador.projeto_integrador.modules.admin.entity.AdminEntity;
import com.projeto_integrador.projeto_integrador.modules.admin.repository.AdminRepository;

@Service
public class CreateAdminUseCase {
    @Autowired
    AdminRepository adminRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public AdminEntity execute(AdminEntity adminEntity){
        this.adminRepository.findByAdminEmail(adminEntity.getAdminEmail())
                                .ifPresent(user -> {
                                    throw new UserFoundException();
        });

        var password = passwordEncoder.encode(adminEntity.getAdminPassword());
        adminEntity.setAdminPassword(password);

        return this.adminRepository.save(adminEntity);
    }
}
