package com.projeto_integrador.projeto_integrador;

import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import com.projeto_integrador.projeto_integrador.modules.student.usecases.EmailService;

@SpringBootTest
public class EmailServiceTest {

    @Mock
    private JavaMailSender mailSender;

    @InjectMocks
    private EmailService emailService;

    @Test
    public void testEmailSending() {
        String toEmail = "nicolasdeoliveiravalle@gmail.com"; // Altere para um email válido
        String token = "token-de-teste"; // Gera um token de teste

        // Envie o e-mail
        emailService.sendResetPasswordEmail(toEmail, token);

        // Verifique se o método send foi chamado com os parâmetros corretos
        verify(mailSender).send(org.mockito.ArgumentMatchers.any(SimpleMailMessage.class));
    }
}
