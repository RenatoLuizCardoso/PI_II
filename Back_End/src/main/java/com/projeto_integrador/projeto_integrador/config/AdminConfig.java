package com.projeto_integrador.projeto_integrador.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.projeto_integrador.projeto_integrador.modules.admin.entity.AdminEntity;
import com.projeto_integrador.projeto_integrador.modules.admin.repository.AdminRepository;

@Configuration
public class AdminConfig {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Bean
    public CommandLineRunner insertAdmin(AdminRepository adminRepository) {
        return args -> {
            if (adminRepository.count() == 0) {
                AdminEntity admin = AdminEntity.builder()
                        .adminName("Tadeu")
                        .adminEmail("tadeumaffeis@gmail.com")
                        .adminPassword(passwordEncoder.encode("12345678"))
                        .build();

                adminRepository.save(admin);
                System.out.println("Admin inserido com sucesso!");
            }
        };
    }

}
