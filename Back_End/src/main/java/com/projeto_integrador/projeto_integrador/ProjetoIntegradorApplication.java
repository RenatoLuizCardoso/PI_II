package com.projeto_integrador.projeto_integrador;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "Projeto Integrador", description = "API responsável pelo gerenciamento de professores, alunos e horários da FATEC", version = "1.0"))
public class ProjetoIntegradorApplication {
	public static void main(String[] args) {
		SpringApplication.run(ProjetoIntegradorApplication.class, args);
	}

}
