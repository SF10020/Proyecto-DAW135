package com.libcode.dawproject.dawproject.controller;

import com.libcode.dawproject.dawproject.model.Metodologia;
import com.libcode.dawproject.dawproject.service.MetodologiaService;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/metodologias")
@PreAuthorize("hasAnyAuthority('ROLE_Admin', 'ROLE_Editor')")
public class MetodologiaController {

    private final MetodologiaService metodologiaService;

    public MetodologiaController(MetodologiaService metodologiaService) {
        this.metodologiaService = metodologiaService;
    }

    @GetMapping
    public String listarMetodologias(Model model) {
        List<Metodologia> metodologias = metodologiaService.obtenerTodas();
        model.addAttribute("metodologias", metodologias);
        return "metodologias/lista";
    }

    @PostMapping("/guardar")
    public String guardarMetodologia(@ModelAttribute Metodologia metodologia,
                                     RedirectAttributes redirectAttributes) {
        metodologiaService.guardar(metodologia);
        redirectAttributes.addFlashAttribute("mensaje", "Metodología guardada exitosamente.");
        return "redirect:/metodologias";
    }

    @PostMapping("/actualizar")
    public String actualizarMetodologia(@ModelAttribute Metodologia metodologia,
                                        RedirectAttributes redirectAttributes) {
        metodologiaService.guardar(metodologia);
        redirectAttributes.addFlashAttribute("mensaje", "Metodología actualizada correctamente.");
        return "redirect:/metodologias";
    }
}

// @Controller
// @RequestMapping("/metodologias")
// public class MetodologiaController {

//     private final MetodologiaService metodologiaService;

//     public MetodologiaController(MetodologiaService metodologiaService) {
//         this.metodologiaService = metodologiaService;
//     }

//     // Ruta: http://localhost:8080/metodologias
//     @GetMapping
//     public String listarMetodologias(Model model) {
//         List<Metodologia> metodologias = metodologiaService.obtenerTodas();
//         model.addAttribute("metodologias", metodologias);
//         return "metodologias/lista"; // Espera lista.html en templates/Metodologias/
//     }
// }

