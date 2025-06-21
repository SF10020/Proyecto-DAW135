package com.libcode.dawproject.dawproject.repository;

import com.libcode.dawproject.dawproject.dto.DecisionSimulacionDTO;
import com.libcode.dawproject.dawproject.model.DecisionSimulacion;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface DecisionSimulacionRepository extends JpaRepository<DecisionSimulacion, Long> {

    @Query("SELECT new com.libcode.dawproject.dawproject.dto.DecisionSimulacionDTO(" +
            "d.id, d.descripcion, s.id, s.costoEstimado, p.nombre, d.prioridad) " +
            "FROM DecisionSimulacion d " +
            "JOIN d.simulacion s " +
            "JOIN s.proyecto p")
    List<DecisionSimulacionDTO> obtenerDecisionesConSimulacion();

    @Query("SELECT new com.libcode.dawproject.dawproject.dto.DecisionSimulacionDTO(" +
            "d.id, d.descripcion, s.id, s.costoEstimado, p.nombre, d.prioridad) " +
            "FROM DecisionSimulacion d " +
            "JOIN d.simulacion s " +
            "JOIN s.proyecto p " +
            "WHERE p.id = :proyectoId")
    List<DecisionSimulacionDTO> obtenerDecisionesPorProyecto(@Param("proyectoId") Long proyectoId);

    List<DecisionSimulacion> findBySimulacionId(Long simulacionId);
}
