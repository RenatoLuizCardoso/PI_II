package com.projeto_integrador.projeto_integrador.modules.admin.usecases;

import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;

import javax.naming.AuthenticationException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.projeto_integrador.projeto_integrador.modules.admin.dto.AuthAdminDTO;
import com.projeto_integrador.projeto_integrador.modules.admin.dto.AuthAdminResponseDTO;
import com.projeto_integrador.projeto_integrador.modules.admin.repository.AdminRepository;

@Service
public class AuthAdminUseCase {

    private static final Logger logger = LoggerFactory.getLogger(AuthAdminUseCase.class);

    @Value("${security.token.secret}")
    private String secretKey;

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public AuthAdminResponseDTO execute(AuthAdminDTO authAdminDTO) throws AuthenticationException {
        logger.debug("Attempting to authenticate admin with email: {}", authAdminDTO.getAdminEmail());
        
        if (authAdminDTO.getAdminEmail() == null || authAdminDTO.getAdminPassword() == null) {
            logger.error("Email or password is null");
            throw new AuthenticationException("Email and password must not be null");
        }

        var admin = this.adminRepository.findByAdminEmail(authAdminDTO.getAdminEmail())
            .orElseThrow(() -> {
                logger.error("Admin with email {} not found", authAdminDTO.getAdminEmail());
                return new UsernameNotFoundException("Email not found");
            });

        var passwordMatches = this.passwordEncoder.matches(authAdminDTO.getAdminPassword(), admin.getAdminPassword());

        if (!passwordMatches) {
            logger.error("Password does not match for email {}", authAdminDTO.getAdminEmail());
            throw new AuthenticationException("Invalid password");
        }

        logger.info("Password matches for email {}", authAdminDTO.getAdminEmail());

        Algorithm algorithm = Algorithm.HMAC256(secretKey);

        var expiresIn = Instant.now().plus(Duration.ofHours(7));

        var token = JWT.create().withIssuer("javagas")
            .withExpiresAt(expiresIn)
            .withSubject(admin.getAdminId().toString())
            .withClaim("roles", Arrays.asList("ADMIN"))
            .sign(algorithm);

        var roles = Arrays.asList("ADMIN");

        var authAdminResponseDTO = AuthAdminResponseDTO.builder()
            .access_token(token)
            .expires_in(expiresIn.toEpochMilli())
            .roles(roles)
            .build();

        return authAdminResponseDTO;
    }
}
