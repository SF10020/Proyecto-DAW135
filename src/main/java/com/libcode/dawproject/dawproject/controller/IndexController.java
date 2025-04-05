package com.libcode.dawproject.dawproject.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class IndexController {

    // Esta será la ruta principal que redirigirá al login
    @GetMapping("/")
    public String redirectToLogin() {
        return "redirect:/login";
    }
    
    // Ruta para mostrar la página de login
    @GetMapping("/login")
    public String login() {
        return "login";
    }
    
    // La ruta /home mostrará el dashboard (antiguo index)
    @GetMapping("/home")
    public String home() {
        return "index";
    }
    
    // Esta ruta procesará el formulario de login (simulado)
    @PostMapping("/auth")
    public String processLogin() {
        // No verificamos credenciales, simplemente redirigimos al dashboard
        return "redirect:/home";
    }
    
    //@GetMapping("/nueva-simulacion")
    //public String nuevaSimulacion() {
        //return "formulario-simulacion";
    //}
}


