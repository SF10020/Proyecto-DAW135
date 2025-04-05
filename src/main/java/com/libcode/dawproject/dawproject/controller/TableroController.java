package com.libcode.dawproject.dawproject.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TableroController {

    @GetMapping("/nueva-simulacion")
    public String mostrarTablero() {
        return "tablero"; // Esto busca el archivo tablero.html en src/main/resources/templates
    }
}

