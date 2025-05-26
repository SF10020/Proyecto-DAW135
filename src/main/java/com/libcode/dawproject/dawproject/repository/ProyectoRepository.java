package com.libcode.dawproject.dawproject.repository;

import com.libcode.dawproject.dawproject.model.Proyecto;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProyectoRepository extends JpaRepository<Proyecto, Long> {

    List<Proyecto> findByMetodologiaId(Long metodologiaId);
    // Ejemplo de método personalizado:
    List<Proyecto> findByUsuarioId(Long usuarioId);
}
