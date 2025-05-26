package com.libcode.dawproject.dawproject.controller;

import com.libcode.dawproject.dawproject.model.Simulacion;
import com.libcode.dawproject.dawproject.model.Tarea;
import com.libcode.dawproject.dawproject.model.Usuario;
import com.libcode.dawproject.dawproject.repository.SimulacionRepository;
import com.libcode.dawproject.dawproject.repository.TareaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/tareas")
@PreAuthorize("hasAnyAuthority('ROLE_Admin', 'ROLE_Editor')")
public class TareaController {

    @Autowired
    private TareaRepository tareaRepository;

    @Autowired
    private SimulacionRepository simulacionRepository;

    @Autowired
    private com.libcode.dawproject.dawproject.util.UsuarioActualHelper usuarioActualHelper;


    // Obtener todas las tareas por simulación
    @GetMapping("/simulacion/{simulacionId}")
    public ResponseEntity<List<Tarea>> obtenerTareasPorSimulacion(@PathVariable Long simulacionId) {
        List<Tarea> tareas = tareaRepository.findBySimulacionId(simulacionId);
        return ResponseEntity.ok(tareas);
    }

    // Crear nueva tarea
    @PostMapping
    public ResponseEntity<Tarea> crearTarea(@RequestBody Tarea tarea) {
        Optional<Simulacion> simulacionOpt = simulacionRepository.findById(tarea.getSimulacion().getId());

        if (simulacionOpt.isPresent()) {
            tarea.setSimulacion(simulacionOpt.get());

            Usuario usuario = usuarioActualHelper.obtenerUsuarioActual();
            tarea.setUsuario(usuario); // Asocia el usuario autenticado a la tarea

            Tarea nuevaTarea = tareaRepository.save(tarea);
            return ResponseEntity.ok(nuevaTarea);
        }

        return ResponseEntity.badRequest().build();
    }

    // Actualizar estado de una tarea (por drag & drop)
    @PutMapping("/{id}/estado")
    public ResponseEntity<Tarea> actualizarEstado(@PathVariable Long id, @RequestBody Map<String, String> estadoMap) {
        String nuevoEstado = estadoMap.get("estado");
        Optional<Tarea> tareaOpt = tareaRepository.findById(id);

        if (tareaOpt.isPresent()) {
            Tarea tarea = tareaOpt.get();
            tarea.setEstado(nuevoEstado);
            tareaRepository.save(tarea);
            return ResponseEntity.ok(tarea);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarTarea(@PathVariable Long id) {
        if (tareaRepository.existsById(id)) {
            tareaRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}

