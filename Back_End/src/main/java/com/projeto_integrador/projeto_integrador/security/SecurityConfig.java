package com.projeto_integrador.projeto_integrador.security;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {

    @Autowired
    SecurityFilter securityFilter;

    @Autowired
    SecurityStudentFilter securityStudentFilter;

    @Autowired
    SecurityTeacherFilter securityTeacherFilter;

    private static final String[] PERMIT_ALL_LIST = {
        "/swagger-ui/**",
        "/v3/api-docs/**",
        "/swagger-resources/**",
        "/actuator/**"
    };

    private static final String[] PERMIT_TEMPORARILY = {
        // "/course/",
        // "/room/",
        // "/schedule/",
        // "/student",
        // "/subject",
        // "/teacher/",
        // "/time/"
    };

    
    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable())
        .authorizeHttpRequests(auth -> {
            auth.requestMatchers("/admin/auth").permitAll()
                .requestMatchers("/teacher/auth").permitAll()
                .requestMatchers("/student/auth").permitAll()
                .requestMatchers("/student/register").permitAll()
                .requestMatchers("/student/forgot-password").permitAll()
                .requestMatchers("/student/reset-password").permitAll()
                .requestMatchers("/teacher/forgot-password").permitAll()
                .requestMatchers("/teacher/reset-password").permitAll()
                .requestMatchers("/student/").permitAll()
                .requestMatchers(PERMIT_ALL_LIST).permitAll()
                .requestMatchers(PERMIT_TEMPORARILY).permitAll();

            auth.anyRequest().authenticated();
        })
        .addFilterBefore(securityStudentFilter, BasicAuthenticationFilter.class)
        .addFilterBefore(securityTeacherFilter, BasicAuthenticationFilter.class)
        .addFilterBefore(securityFilter, BasicAuthenticationFilter.class);
               
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
