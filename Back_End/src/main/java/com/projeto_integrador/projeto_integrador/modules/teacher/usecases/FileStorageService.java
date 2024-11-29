package com.projeto_integrador.projeto_integrador.modules.teacher.usecases;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileStorageService {

    private final String uploadDir = "uploads/teacher_photos/";

    public String saveFile(MultipartFile file) throws IOException {
        String fileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
        Path filePath = Paths.get(uploadDir, fileName);
    
        Files.createDirectories(filePath.getParent()); // Cria diretórios se não existirem
        Files.write(filePath, file.getBytes());        // Salva o arquivo
    
        // Retorna a URL relativa, acessível pelo navegador
        return "/uploads/teacher_photos/" + fileName;
    }
}