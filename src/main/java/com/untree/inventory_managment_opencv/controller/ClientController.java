package com.untree.inventory_managment_opencv.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.untree.inventory_managment_opencv.model.User;
import com.untree.inventory_managment_opencv.repository.UserRepository;
import com.untree.inventory_managment_opencv.service.ImageHashService;
import com.untree.inventory_managment_opencv.service.ImageProcessingService;

import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class ClientController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ImageHashService imageHashService;

    @Autowired
    private ImageProcessingService imageProcessingService;

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/register")
    public String register() {
        return "register";
    }

    @PostMapping("/login")
    public String postMethodName(@RequestParam String email, @RequestParam String password,
            @RequestParam String faceImage, Model model) {

        User user = userRepository.findByEmail(email);

        if (user == null || !user.getPassword().equals(password)) {
            model.addAttribute("error", "Credenciais inválidas");
            return "login"; // Redirecionar para a página de login se as credenciais forem inválidas
        }

        try {
            // Verificar se o hash da imagem facial corresponde ao armazenado
            String faceHash = imageHashService.generateImageHash(faceImage);
            if (!faceHash.equals(user.getFaceHash())) {
                model.addAttribute("error", "Imagem facial não corresponde");
                return "login";
            }
    
            return "redirect:/"; // Redirecionar para a página inicial após o login bem-sucedido
        } catch (Exception e) {
            model.addAttribute("error", "Erro ao processar a imagem facial");
            return "login";
        }
    }

    @PostMapping("/register")
    public String register(@RequestParam String email, @RequestParam String password,
            @RequestParam String confirmPassword, @RequestParam String faceImage, Model model) {

        if (!password.equals(confirmPassword)) {
            model.addAttribute("error", "Senhas não conferem");
            return "register"; // Redirecionar para a página de registro se as senhas não coincidirem
        }

        // Verificar se o e-mail já está registrado
        if (userRepository.findByEmail(email) != null) {
            model.addAttribute("error", "E-mail já registrado");
            return "register"; // Redirecionar para a página de registro se o e-mail já estiver registrado
        }

        try {
            // Gera o hash da imagem facial
            String faceHash = imageHashService.generateImageHash(faceImage);
        
            // Cria o usuário com o hash facial
            User user = new User(email, password, faceHash);
        
            userRepository.save(user);
        
            return "redirect:/login";
        } catch (Exception e) {
            model.addAttribute("error", "Erro ao processar a imagem facial");
            return "register";
        }        
    }

}
