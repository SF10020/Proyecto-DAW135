package com.libcode.dawproject.dawproject.controller;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {

    // Redirige a /home si el usuario accede a la raíz
    @GetMapping("/")
    public String redirectToHome() {
        return "redirect:/home";
    }

    // Muestra la vista personalizada con el botón de Auth0
    @GetMapping("/login")
    public String login() {
        return "login";
    }

    // Vista principal del dashboard
    @GetMapping("/home")
    public String home() {
        return "index";
    }
}



