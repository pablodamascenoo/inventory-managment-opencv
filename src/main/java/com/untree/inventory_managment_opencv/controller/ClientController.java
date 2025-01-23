package com.untree.inventory_managment_opencv.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.untree.inventory_managment_opencv.model.User;
import com.untree.inventory_managment_opencv.repository.UserRepository;

import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class ClientController {

    @Autowired
    private UserRepository userRepository;

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

        // Verificar se a imagem facial corresponde à imagem registrada

        return "redirect:/"; // Redirecionar para a página inicial após o login
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

        User user = new User(email, password, faceImage);

        userRepository.save(user);

        return "redirect:/login"; // Redirecionar para a página de login após o registro
    }

}
