package com.libcode.dawproject.dawproject.controller;

import com.libcode.dawproject.dawproject.dto.DecisionSimulacionDTO;
import com.libcode.dawproject.dawproject.model.DecisionSimulacion;
import com.libcode.dawproject.dawproject.model.Proyecto;
import com.libcode.dawproject.dawproject.model.Simulacion;
import com.libcode.dawproject.dawproject.service.DecisionSimulacionService;
import com.libcode.dawproject.dawproject.service.ProyectoService;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@PreAuthorize("hasAnyAuthority('ROLE_Admin', 'ROLE_Editor')")
public class DecisionSimulacionViewController {

    private final DecisionSimulacionService decisionSimulacionService;
    private final ProyectoService proyectoService;

    public DecisionSimulacionViewController(DecisionSimulacionService decisionSimulacionService,
            ProyectoService proyectoService) {
        this.decisionSimulacionService = decisionSimulacionService;
        this.proyectoService = proyectoService;
    }
    @PostMapping("/decisiones/crearDecision")
    @ResponseBody
    public ResponseEntity<?> crearDecisionSimulacion(
            @RequestParam("idDecision") Long idDecision,
            @RequestParam("descripcion") String descripcion) {

        // comprobar si la simulación existe
        Simulacion simulacion = new Simulacion();
        simulacion.setId(idDecision);

        // Crear la decisión de simulación
        DecisionSimulacion decision = new DecisionSimulacion();
        decision.setSimulacion(simulacion);
        decision.setDescripcion(descripcion);

        decisionSimulacionService.guardar(decision);

        return ResponseEntity.ok().body("Guardado correctamente");
    }
    @GetMapping("/decisiones")
    public String listarDecisiones(@RequestParam(value = "proyectoId", required = false) Long proyectoId, Model model) {
        List<DecisionSimulacionDTO> decisiones;

        if (proyectoId != null) {
            decisiones = decisionSimulacionService.obtenerDecisionesPorProyecto(proyectoId);
        } else {
            decisiones = decisionSimulacionService.obtenerDecisionesConSimulacion();
        }

        List<Proyecto> proyectos = proyectoService.obtenerTodos();

        model.addAttribute("decisiones", decisiones);
        model.addAttribute("proyectos", proyectos);
        return "decisiones/lista"; // Se renderiza templates/decisiones/lista.html
    }

    @GetMapping("/decisiones/filter")
    @ResponseBody
    public List<DecisionSimulacionDTO> obtenerDecisionesFiltradas(@RequestParam Long proyectoId) {
        return decisionSimulacionService.obtenerDecisionesPorProyecto(proyectoId);
    }

    @DeleteMapping("/decisiones/eliminar/{id}")
    @ResponseBody
    public ResponseEntity<?> eliminarDecision(@PathVariable Long id) {
        try {
            decisionSimulacionService.eliminar(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error al eliminar la decisión.");
        }
    }


}