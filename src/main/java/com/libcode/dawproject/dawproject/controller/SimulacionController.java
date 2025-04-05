package com.libcode.dawproject.dawproject.controller;

import com.libcode.dawproject.dawproject.model.Simulacion;
import com.libcode.dawproject.dawproject.service.SimulacionService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/simulaciones")
public class SimulacionController {

    private final SimulacionService simulacionService;

    public SimulacionController(SimulacionService simulacionService) {
        this.simulacionService = simulacionService;
    }

    // Ruta: http://localhost:8080/simulaciones
    @GetMapping
    public String listarSimulaciones(Model model) {
        List<Simulacion> simulaciones = simulacionService.obtenerTodas();
        model.addAttribute("simulaciones", simulaciones);
        return "simulaciones/lista"; // espera vista en templates/Simulaciones/lista.html
    }
}

