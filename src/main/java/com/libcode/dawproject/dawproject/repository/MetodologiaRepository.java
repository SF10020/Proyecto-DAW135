package com.libcode.dawproject.dawproject.repository;

import com.libcode.dawproject.dawproject.model.Metodologia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MetodologiaRepository extends JpaRepository<Metodologia, Long> {

    // Ejemplo de método personalizado:
    // Optional<Metodologia> findByNombre(String nombre);
}

