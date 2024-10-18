package com.projeto_integrador.projeto_integrador.modules.admin.usecases;

import java.time.Duration;
import java.time.Instant;

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

    public String execute(AuthAdminDTO authAdminDTO) throws AuthenticationException {
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

        var token = JWT.create()
            .withIssuer("cps")
            .withExpiresAt(Instant.now().plus(Duration.ofHours(7)))
            .withSubject(admin.getAdminId().toString())
            .sign(algorithm);

        logger.info("Token generated for email {}", authAdminDTO.getAdminEmail());

        return token;
    }
}
