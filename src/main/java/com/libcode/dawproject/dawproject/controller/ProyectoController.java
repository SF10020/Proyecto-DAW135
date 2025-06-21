package com.libcode.dawproject.dawproject.controller;

import com.libcode.dawproject.dawproject.model.DecisionSimulacion;
import com.libcode.dawproject.dawproject.model.Proyecto;
import com.libcode.dawproject.dawproject.model.Simulacion;
import com.libcode.dawproject.dawproject.service.DecisionSimulacionService;
import com.libcode.dawproject.dawproject.service.MetodologiaService;
import com.libcode.dawproject.dawproject.service.ProyectoService;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/proyectos")
@PreAuthorize("hasAnyAuthority('ROLE_Admin', 'ROLE_Editor')")
public class ProyectoController {

    private final ProyectoService proyectoService;
    private final MetodologiaService metodologiaService;
    private final DecisionSimulacionService decisionSimulacionService;

    public ProyectoController(ProyectoService proyectoService, MetodologiaService metodologiaService,
            DecisionSimulacionService decisionSimulacionService) {
        this.proyectoService = proyectoService;
        this.metodologiaService = metodologiaService;
        this.decisionSimulacionService = decisionSimulacionService;
    }

    @PostMapping("/decisiones/crear")
    @ResponseBody
    public ResponseEntity<?> crearDecisionSimulacion(
            @RequestParam("proyectoId") Long proyectoId,
            @RequestParam("descripcion") String descripcion,
            @RequestParam("prioridad") String prioridad) { // nuevo parámetro

        Optional<Proyecto> proyectoOpt = proyectoService.obtenerPorId(proyectoId);
        if (proyectoOpt.isEmpty()) {
            return ResponseEntity.badRequest().body("Proyecto no encontrado");
        }

        Proyecto proyecto = proyectoOpt.get();

        List<Simulacion> simulaciones = proyecto.getSimulaciones();
        if (simulaciones == null || simulaciones.isEmpty()) {
            return ResponseEntity.badRequest().body("No existe simulación para este proyecto");
        }

        Simulacion simulacion = simulaciones.get(0);

        DecisionSimulacion decision = new DecisionSimulacion();
        decision.setSimulacion(simulacion);
        decision.setDescripcion(descripcion);
        decision.setPrioridad(prioridad); // asignar prioridad

        try {
            decisionSimulacionService.guardar(decision);
            return ResponseEntity.ok("Decisión guardada correctamente");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error al guardar la decisión");
        }
    }

    // Ruta: http://localhost:8080/proyectos
    @GetMapping
    public String listarProyectos(Model model,
            @RequestParam(value = "exito", required = false) String exito,
            @RequestParam(value = "eliminado", required = false) String eliminado,
            @RequestParam(value = "error", required = false) String error) {
        List<Proyecto> proyectos = proyectoService.obtenerTodos();
        model.addAttribute("proyectos", proyectos);
        model.addAttribute("metodologias", metodologiaService.obtenerTodas());

        if (exito != null) {
            model.addAttribute("mensajeExito", "Proyecto guardado exitosamente.");
        }

        if (eliminado != null) {
            model.addAttribute("mensajeExito", "Proyecto se ha eliminado exitosamente.");
        }

        if (error != null) {
            model.addAttribute("mensajeError", "Hubo un error al guardar o eliminar el proyecto.");
        }

        return "proyectos/lista";
    }

    @PostMapping("/guardar")
    public String guardarProyecto(@RequestParam("nombre") String nombre,
            @RequestParam("metodologiaId") Long metodologiaId) {
        try {
            proyectoService.guardarProyecto(nombre, metodologiaId);
            return "redirect:/proyectos?exito";
        } catch (Exception e) {
            return "redirect:/proyectos?error";
        }
    }

    @PostMapping("/eliminar")
    public String eliminarProyecto(@RequestParam("id") Long id) {
        try {
            proyectoService.eliminarProyecto(id);
            return "redirect:/proyectos?eliminado";
        } catch (Exception e) {
            return "redirect:/proyectos?error";
        }
    }

    @GetMapping("/por-metodologia")
    @ResponseBody
    public List<Proyecto> obtenerProyectosPorMetodologia(@RequestParam("id") Long metodologiaId) {
        return proyectoService.obtenerPorMetodologiaId(metodologiaId);
    }

    // temporal
    // @PostMapping("/decisiones/crearDecision")
    // @ResponseBody
    // public ResponseEntity<?> crearDecisionSimulacion(
    // @RequestParam("idDecision") Long idDecision,
    // @RequestParam("descripcion") String descripcion) {

    // // comprobar si la simulación existe
    // Simulacion simulacion = new Simulacion();
    // simulacion.setId(idDecision);

    // // Crear la decisión de simulación
    // DecisionSimulacion decision = new DecisionSimulacion();
    // decision.setSimulacion(simulacion);
    // decision.setDescripcion(descripcion);

    // decisionSimulacionService.guardar(decision);

    // return ResponseEntity.ok().body("Guardado correctamente");
    // }

}