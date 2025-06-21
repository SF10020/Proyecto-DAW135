package com.libcode.dawproject.dawproject.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.libcode.dawproject.dawproject.dto.CrearSimulacionRequest;
import com.libcode.dawproject.dawproject.dto.SimulacionDTO;
import com.libcode.dawproject.dawproject.model.DecisionSimulacion;
import com.libcode.dawproject.dawproject.model.Proyecto;
import com.libcode.dawproject.dawproject.model.Simulacion;
import com.libcode.dawproject.dawproject.repository.ProyectoRepository;
import com.libcode.dawproject.dawproject.repository.SimulacionRepository;
import com.libcode.dawproject.dawproject.service.DecisionSimulacionService;
import com.libcode.dawproject.dawproject.service.ProyectoService;
import com.libcode.dawproject.dawproject.service.SimulacionService;

@RestController
@RequestMapping("/api/simulaciones")
@PreAuthorize("hasAnyAuthority('ROLE_Admin', 'ROLE_Editor')")
public class SimulacionApiController {

    @Autowired
    private SimulacionRepository simulacionRepository;

    @Autowired
    private ProyectoRepository proyectoRepository;

    @Autowired
    private SimulacionService simulacionService;
    
    @Autowired
    private ProyectoService proyectoService;
    
    @Autowired
    private DecisionSimulacionService decisionSimulacionService;

    @PostMapping
    public ResponseEntity<Simulacion> crearSimulacion(@RequestBody CrearSimulacionRequest request) {
        Optional<Proyecto> proyectoOpt = proyectoRepository.findById(request.getProyectoId());

        if (proyectoOpt.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        Simulacion simulacion = new Simulacion(
                proyectoOpt.get(),
                request.getTiempoEstimado(),
                request.getCostoEstimado(),
                request.getCalidadEstimada());

        Simulacion guardada = simulacionRepository.save(simulacion);
        return ResponseEntity.ok(guardada);
    }

    // ENDPOINT PARA ACTUALIZAR SIMULACIÓN VIA AJAX (PUT)
    @PutMapping("/actualizar")
    public ResponseEntity<SimulacionDTO> actualizarSimulacion(@RequestBody Simulacion simulacionActualizada) {
        // 1. Verificar si la simulación existe en la base de datos
        Simulacion simulacionExistente = simulacionService.obtenerPorId(simulacionActualizada.getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Simulación no encontrada con ID: " + simulacionActualizada.getId()));

        // 2. Actualizar solo los campos que vienen del modal
        simulacionExistente.setTiempoEstimado(simulacionActualizada.getTiempoEstimado());
        simulacionExistente.setCostoEstimado(simulacionActualizada.getCostoEstimado());
        simulacionExistente.setCalidadEstimada(simulacionActualizada.getCalidadEstimada());

        // 3. Guardar los cambios en la base de datos
        Simulacion simulacionGuardada = simulacionService.guardar(simulacionExistente);

        // 4. Mapear a DTO
        SimulacionDTO dto = new SimulacionDTO();
        dto.setId(simulacionGuardada.getId());
        dto.setTiempoEstimado(simulacionGuardada.getTiempoEstimado());
        dto.setCostoEstimado(
                simulacionGuardada.getCostoEstimado() != null ? simulacionGuardada.getCostoEstimado().doubleValue()
                        : 0.0);
        dto.setCalidadEstimada(simulacionGuardada.getCalidadEstimada());
        dto.setFechaSimulacion(
                simulacionGuardada.getFechaSimulacion() != null
                        ? simulacionGuardada.getFechaSimulacion().toString()
                        : null);

        // 5. Devolver la simulación actualizada como respuesta JSON
        return ResponseEntity.ok(dto);
    }

    // agregado
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

}