package com.libcode.dawproject.dawproject.controller;

import com.libcode.dawproject.dawproject.model.Metodologia;
import com.libcode.dawproject.dawproject.service.MetodologiaService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/metodologias")
public class MetodologiaController {

    private final MetodologiaService metodologiaService;

    public MetodologiaController(MetodologiaService metodologiaService) {
        this.metodologiaService = metodologiaService;
    }

    // Ruta: http://localhost:8080/metodologias
    @GetMapping
    public String listarMetodologias(Model model) {
        List<Metodologia> metodologias = metodologiaService.obtenerTodas();
        model.addAttribute("metodologias", metodologias);
        return "metodologias/lista"; // Espera lista.html en templates/Metodologias/
    }
}

