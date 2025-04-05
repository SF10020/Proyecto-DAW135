package com.libcode.dawproject.dawproject.repository;

import com.libcode.dawproject.dawproject.model.DecisionSimulacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DecisionSimulacionRepository extends JpaRepository<DecisionSimulacion, Long> {

    // Ejemplo de método personalizado:
    // List<DecisionSimulacion> findBySimulacionId(Long simulacionId);
}

