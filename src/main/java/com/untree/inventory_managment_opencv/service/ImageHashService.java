package com.untree.inventory_managment_opencv.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.springframework.stereotype.Service;

@Service
public class ImageHashService {

    /**
     * Gera um hash SHA-256 a partir do conteúdo da imagem.
     * 
     * @param imagePath Caminho para o arquivo de imagem.
     * @return Hash gerado como string hexadecimal.
     * @throws IOException Se o arquivo não puder ser lido.
     * @throws NoSuchAlgorithmException Se o algoritmo SHA-256 não for encontrado.
     */
    public String generateImageHash(String imagePath) throws IOException, NoSuchAlgorithmException {
        byte[] imageBytes = Files.readAllBytes(Paths.get(imagePath));
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] hashBytes = digest.digest(imageBytes);

        // Converte o hash para formato hexadecimal
        StringBuilder hexString = new StringBuilder();
        for (byte b : hashBytes) {
            String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }
        return hexString.toString();
    }
}
