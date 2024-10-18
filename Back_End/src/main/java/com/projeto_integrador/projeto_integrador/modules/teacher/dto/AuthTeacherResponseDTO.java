package com.projeto_integrador.projeto_integrador.modules.teacher.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthTeacherResponseDTO {
    
    private String access_token;
    private Long expires_in;
}
