package com.projeto_integrador.projeto_integrador.modules.student.usecases;

import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;
import javax.security.sasl.AuthenticationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.projeto_integrador.projeto_integrador.modules.student.dto.AuthStudentRequestDTO;
import com.projeto_integrador.projeto_integrador.modules.student.dto.AuthStudentResponseDTO;
import com.projeto_integrador.projeto_integrador.modules.student.repository.StudentRepository;

@Service
public class AuthStudent {

    private static final Logger logger = LoggerFactory.getLogger(AuthStudent.class);

    @Value("${security.token.secret}")
    private String secretKeyStudent;

    @Autowired 
    private StudentRepository studentRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public AuthStudentResponseDTO execute(AuthStudentRequestDTO authStudentRequestDTO)
        throws AuthenticationException {
        
        logger.debug("Attempting to authenticate student with email: {}", authStudentRequestDTO.institutionalEmail());

        if (authStudentRequestDTO.institutionalEmail() == null || authStudentRequestDTO.studentPassword() == null) {
            logger.error("Email or password is null");
            throw new AuthenticationException("Email and password must not be null");
        }

        var student = this.studentRepository.findByInstitutionalEmail(authStudentRequestDTO.institutionalEmail())
            .orElseThrow(() -> {
                logger.error("Student with email {} not found", authStudentRequestDTO.institutionalEmail());
                return new UsernameNotFoundException("Email/password incorrect");
            });

        var passwordMatches = this.passwordEncoder.matches(authStudentRequestDTO.studentPassword(), student.getStudentPassword());

        if (!passwordMatches) {
            logger.error("Password does not match for email {}", authStudentRequestDTO.institutionalEmail());
            throw new AuthenticationException("Invalid password");
        }

        logger.info("Password matches for email {}", authStudentRequestDTO.institutionalEmail());

        var roles = Arrays.asList("STUDENT");

        Algorithm algorithm = Algorithm.HMAC256(secretKeyStudent);
        var expiresIn = Instant.now().plus(Duration.ofMinutes(10));
        var token = JWT.create()
            .withIssuer("javagas")
            .withSubject(student.getStudentId().toString())
            .withClaim("roles", roles)
            .withExpiresAt(expiresIn)
            .sign(algorithm);

        var authStudentResponse = AuthStudentResponseDTO.builder()
            .access_token(token)
            .expires_in(expiresIn.toEpochMilli())
            .roles(roles)
            .build();

        return authStudentResponse;
    }
}
