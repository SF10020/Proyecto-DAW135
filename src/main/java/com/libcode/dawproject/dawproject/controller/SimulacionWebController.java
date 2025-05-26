package com.libcode.dawproject.dawproject.controller;

import com.libcode.dawproject.dawproject.model.Proyecto;
import com.libcode.dawproject.dawproject.service.ProyectoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class SimulacionWebController {

    private final ProyectoService proyectoService;

    public SimulacionWebController(ProyectoService proyectoService) {
        this.proyectoService = proyectoService;
    }

    // Muestra la vista con todos los proyectos para iniciar simulación
    @GetMapping("/nueva-simulacion")
    public String mostrarProyectosParaSimulacion(Model model) {
        List<Proyecto> proyectos = proyectoService.obtenerTodos();
        model.addAttribute("proyectos", proyectos);
        return "simulaciones/seleccionar-proyecto"; // ruta en templates
    }
}
