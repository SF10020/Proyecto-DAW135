package com.libcode.dawproject.dawproject.service;

import com.libcode.dawproject.dawproject.dto.DecisionSimulacionDTO;
import com.libcode.dawproject.dawproject.model.DecisionSimulacion;
import com.libcode.dawproject.dawproject.repository.DecisionSimulacionRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DecisionSimulacionService {

    private final DecisionSimulacionRepository decisionSimulacionRepository;

    public DecisionSimulacionService(DecisionSimulacionRepository decisionSimulacionRepository) {
        this.decisionSimulacionRepository = decisionSimulacionRepository;
    }

    public List<DecisionSimulacion> obtenerTodas() {
        return decisionSimulacionRepository.findAll();
    }

    public Optional<DecisionSimulacion> obtenerPorId(Long id) {
        return decisionSimulacionRepository.findById(id);
    }

    public DecisionSimulacion guardar(DecisionSimulacion decision) {
        return decisionSimulacionRepository.save(decision);
    }

    public void eliminar(Long id) {
        decisionSimulacionRepository.deleteById(id);
    }

    // agregados
    public List<DecisionSimulacionDTO> obtenerDecisionesConSimulacion() {
        return decisionSimulacionRepository.obtenerDecisionesConSimulacion();
    }

    public List<DecisionSimulacionDTO> obtenerDecisionesPorProyecto(Long proyectoId) {
        return decisionSimulacionRepository.obtenerDecisionesPorProyecto(proyectoId);
    }

    // Ejemplo: Obtener decisiones por simulación:
    // public List<DecisionSimulacion> obtenerPorSimulacionId(Long id) {
    // return decisionSimulacionRepository.findBySimulacionId(id);
    // }
}