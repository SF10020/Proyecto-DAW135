package com.libcode.dawproject.dawproject.repository;

import com.libcode.dawproject.dawproject.model.Tarea;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface TareaRepository extends JpaRepository<Tarea, Long> {
    List<Tarea> findBySimulacionId(Long simulacionId);
}

