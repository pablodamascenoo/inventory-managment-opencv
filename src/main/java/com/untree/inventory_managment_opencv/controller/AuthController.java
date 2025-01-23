package com.untree.inventory_managment_opencv.controller;

import com.untree.inventory_managment_opencv.DTO.RegisterDTO;
import com.untree.inventory_managment_opencv.config.TokenService;
import com.untree.inventory_managment_opencv.model.User;
import com.untree.inventory_managment_opencv.model.UserRoles;
import com.untree.inventory_managment_opencv.repository.UserRepository;

import jakarta.servlet.http.Cookie;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
// import javax.servlet.http.Cookie;
// import javax.servlet.http.HttpServletResponse;

@Controller
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TokenService TokenService;

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @PostMapping("/login")
    public String loginUser(@RequestParam String email, @RequestParam String password, Model model) {
        try {
            var usernamePassword = new UsernamePasswordAuthenticationToken(email,
                    password);
            var auth = this.authenticationManager.authenticate(usernamePassword);

            var token = TokenService.generateToken((User) auth.getPrincipal());

            Cookie cookie = new Cookie("token", token);
            cookie.setHttpOnly(true);
            cookie.setPath("/");

            model.addAttribute("token", token);
            return "redirect:/";
        } catch (Exception e) {
            model.addAttribute("error", "Email ou senha inválidos");
            return "login";
        }
    }

    @GetMapping("/register")
    public String register() {
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@RequestParam String email, @RequestParam String password,
            @RequestParam String confirmPassword, @RequestParam String role, Model model) {

        if (!password.equals(confirmPassword)) {
            model.addAttribute("error", "Senhas não conferem");
            return "register";
        }
        User user = userRepository.findByEmail(email);
        if (user != null) {
            model.addAttribute("error", "Email já cadastrado");
            return "register";
        }

        String encryptedPassword = new BCryptPasswordEncoder().encode(password);

        user = new User(email, encryptedPassword, role);
        userRepository.save(user);
        return "redirect:/login";

    }
}
