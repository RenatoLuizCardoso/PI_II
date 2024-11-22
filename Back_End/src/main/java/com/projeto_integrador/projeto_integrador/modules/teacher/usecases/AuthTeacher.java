package com.projeto_integrador.projeto_integrador.modules.teacher.usecases;

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
import com.projeto_integrador.projeto_integrador.modules.student.dto.AuthStudentResponseDTO;
import com.projeto_integrador.projeto_integrador.modules.teacher.dto.AuthTeacherRequestDTO;
import com.projeto_integrador.projeto_integrador.modules.teacher.dto.AuthTeacherResponseDTO;
import com.projeto_integrador.projeto_integrador.modules.teacher.repository.TeacherRepository;

@Service
public class AuthTeacher {

    private static final Logger logger = LoggerFactory.getLogger(AuthTeacher.class);

    @Value("${security.token.secret}")
    private String secretKey;

    @Autowired 
    private TeacherRepository teacherRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public AuthTeacherResponseDTO execute(AuthTeacherRequestDTO authTeacherRequestDTO)
        throws AuthenticationException {
        
        logger.debug("Attempting to authenticate teacher with email: {}", authTeacherRequestDTO.institutionalEmail());

        if (authTeacherRequestDTO.institutionalEmail() == null || authTeacherRequestDTO.teacherPassword() == null) {
            logger.error("Email or password is null");
            throw new AuthenticationException("Email and password must not be null");
        }

        var teacher = this.teacherRepository.findByInstitutionalEmail(authTeacherRequestDTO.institutionalEmail())
            .orElseThrow(() -> {
                logger.error("Teacher with email {} not found", authTeacherRequestDTO.institutionalEmail());
                return new UsernameNotFoundException("Email/password incorrect");
            });

        var passwordMatches = this.passwordEncoder.matches(authTeacherRequestDTO.teacherPassword(), teacher.getTeacherPassword());

        if (!passwordMatches) {
            logger.error("Password does not match for email {}", authTeacherRequestDTO.institutionalEmail());
            throw new AuthenticationException("Invalid password");
        }

        logger.info("Password matches for email {}", authTeacherRequestDTO.institutionalEmail());

        var roles = Arrays.asList("TEACHER");

        Algorithm algorithm = Algorithm.HMAC256(secretKey);
        var expiresIn = Instant.now().plus(Duration.ofMinutes(10));
        var token = JWT.create()
            .withIssuer("javagas")
            .withSubject(teacher.getTeacherId().toString())
            .withClaim("roles", roles)
            .withExpiresAt(expiresIn)
            .sign(algorithm);

        var authTeacherResponse = AuthTeacherResponseDTO.builder()
            .access_token(token)
            .expires_in(expiresIn.toEpochMilli())
            .roles(roles)
            .build();

        return authTeacherResponse;
    }
}
