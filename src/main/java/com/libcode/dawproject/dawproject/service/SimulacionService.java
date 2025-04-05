package com.libcode.dawproject.dawproject.service;

import com.libcode.dawproject.dawproject.model.Simulacion;
import com.libcode.dawproject.dawproject.repository.SimulacionRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SimulacionService {

    private final SimulacionRepository simulacionRepository;

    public SimulacionService(SimulacionRepository simulacionRepository) {
        this.simulacionRepository = simulacionRepository;
    }

    public List<Simulacion> obtenerTodas() {
        return simulacionRepository.findAll();
    }

    public Optional<Simulacion> obtenerPorId(Long id) {
        return simulacionRepository.findById(id);
    }

    public Simulacion guardar(Simulacion simulacion) {
        return simulacionRepository.save(simulacion);
    }

    public void eliminar(Long id) {
        simulacionRepository.deleteById(id);
    }

    // Ejemplo: Obtener simulaciones por ID de proyecto (si se agrega el método en el repo)
    // public List<Simulacion> obtenerPorProyectoId(Long proyectoId) {
    //     return simulacionRepository.findByProyectoId(proyectoId);
    // }
}

