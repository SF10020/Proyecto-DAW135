package com.libcode.dawproject.dawproject.controller;

import com.libcode.dawproject.dawproject.model.Proyecto;
import com.libcode.dawproject.dawproject.model.Simulacion;
import com.libcode.dawproject.dawproject.repository.ProyectoRepository;
import com.libcode.dawproject.dawproject.repository.SimulacionRepository;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Controller
@PreAuthorize("hasAnyAuthority('ROLE_Admin', 'ROLE_Editor')")
public class TableroController {

    private final ProyectoRepository proyectoRepository;
    private final SimulacionRepository simulacionRepository;

    public TableroController(ProyectoRepository proyectoRepository, SimulacionRepository simulacionRepository) {
        this.proyectoRepository = proyectoRepository;
        this.simulacionRepository = simulacionRepository;
    }

    @GetMapping("/tablero/{proyectoId}")
    public String mostrarTablero(@PathVariable Long proyectoId, Model model) {
        Optional<Proyecto> proyectoOpt = proyectoRepository.findById(proyectoId);

        if (proyectoOpt.isEmpty()) {
            return "redirect:/simulaciones/nueva?error=proyectoNoEncontrado";
        }

        Proyecto proyecto = proyectoOpt.get();

        // Buscar simulaciones existentes
        List<Simulacion> simulaciones = simulacionRepository.findAll()
                .stream()
                .filter(s -> s.getProyecto().getId().equals(proyectoId))
                .sorted(Comparator.comparing(Simulacion::getFechaSimulacion).reversed())
                .toList();

        Simulacion simulacion;

        if (simulaciones.isEmpty()) {
            // Crear una simulación predeterminada si no existe ninguna
            simulacion = new Simulacion(
                    proyecto,
                    30, // tiempoEstimado por defecto
                    new BigDecimal("10000.00"), // costoEstimado por defecto
                    80 // calidadEstimada por defecto
            );
            simulacion = simulacionRepository.save(simulacion);
        } else {
            simulacion = simulaciones.get(0); // tomar la más reciente
        }

        model.addAttribute("simulacion", simulacion);
        model.addAttribute("proyecto", proyecto);
        model.addAttribute("tareas", simulacion.getTareas());
        model.addAttribute("metodologia", proyecto.getMetodologia().getNombre());

        return "tablero"; // vista que aún generaremos
    }
}


