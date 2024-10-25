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

    @Value("${security.token.secret.student}")
    private String secretKey;

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

        Algorithm algorithm = Algorithm.HMAC256(secretKey);
        var expires_in = Instant.now().plus(Duration.ofHours(7));

        var token = JWT.create()
            .withIssuer("cps")
            .withSubject(student.getStudentId().toString())
            .withClaim("roles", Arrays.asList("student"))
            .withExpiresAt(expires_in)
            .sign(algorithm);

        logger.info("Token generated for email {}", authStudentRequestDTO.institutionalEmail());

        var authStudentResponse = AuthStudentResponseDTO.builder()
            .access_token(token)
            .expires_in(expires_in.toEpochMilli())
            .build();

        return authStudentResponse;
    }
}
