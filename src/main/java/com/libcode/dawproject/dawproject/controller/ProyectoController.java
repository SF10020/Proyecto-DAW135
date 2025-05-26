package com.libcode.dawproject.dawproject.controller;

import com.libcode.dawproject.dawproject.model.Proyecto;
import com.libcode.dawproject.dawproject.service.MetodologiaService;
import com.libcode.dawproject.dawproject.service.ProyectoService;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/proyectos")
@PreAuthorize("hasAnyAuthority('ROLE_Admin', 'ROLE_Editor')")
public class ProyectoController {

    private final ProyectoService proyectoService;
    private final MetodologiaService metodologiaService;

    public ProyectoController(ProyectoService proyectoService, MetodologiaService metodologiaService) {
        this.proyectoService = proyectoService;
        this.metodologiaService = metodologiaService;
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
}