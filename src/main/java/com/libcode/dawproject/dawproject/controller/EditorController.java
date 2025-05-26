package com.libcode.dawproject.dawproject.controller;

import com.libcode.dawproject.dawproject.model.Proyecto;
import com.libcode.dawproject.dawproject.service.ProyectoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/editor")
@PreAuthorize("hasAuthority('ROLE_Editor')")
public class EditorController {

    @Autowired
    private ProyectoService proyectoService;

    @GetMapping("/proyectos")
    public List<Proyecto> obtenerTodos() {
        return proyectoService.obtenerTodos();
    }

    @GetMapping("/proyectos/{id}")
    public Optional<Proyecto> obtenerPorId(@PathVariable Long id) {
        return proyectoService.obtenerPorId(id);
    }

    @PostMapping("/proyectos")
    public Proyecto crearProyecto(@RequestBody Proyecto proyecto) {
        return proyectoService.guardar(proyecto);
    }

    @PutMapping("/proyectos/{id}")
    public Proyecto actualizarProyecto(@PathVariable Long id, @RequestBody Proyecto proyecto) {
        proyecto.setId(id);
        return proyectoService.guardar(proyecto);
    }
}

