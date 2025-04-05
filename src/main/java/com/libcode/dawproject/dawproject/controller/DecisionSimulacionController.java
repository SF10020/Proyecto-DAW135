package com.libcode.dawproject.dawproject.controller;

import com.libcode.dawproject.dawproject.model.DecisionSimulacion;
import com.libcode.dawproject.dawproject.service.DecisionSimulacionService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/decisiones")
public class DecisionSimulacionController {

    private final DecisionSimulacionService decisionSimulacionService;

    public DecisionSimulacionController(DecisionSimulacionService decisionSimulacionService) {
        this.decisionSimulacionService = decisionSimulacionService;
    }

    // Ruta: http://localhost:8080/decisiones
    @GetMapping
    public String listarDecisiones(Model model) {
        List<DecisionSimulacion> decisiones = decisionSimulacionService.obtenerTodas();
        model.addAttribute("decisiones", decisiones);
        return "decisiones/lista"; // Hace referencia a templates/Decisiones/lista.html
    }
}
