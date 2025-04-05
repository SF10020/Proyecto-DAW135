package com.libcode.dawproject.dawproject.repository;

import com.libcode.dawproject.dawproject.model.Simulacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SimulacionRepository extends JpaRepository<Simulacion, Long> {

    // Ejemplo de método personalizado:
    // List<Simulacion> findByProyectoId(Long proyectoId);
}
