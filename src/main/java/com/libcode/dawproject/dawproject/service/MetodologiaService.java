package com.libcode.dawproject.dawproject.service;

import com.libcode.dawproject.dawproject.model.Metodologia;
import com.libcode.dawproject.dawproject.repository.MetodologiaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MetodologiaService {

    private final MetodologiaRepository metodologiaRepository;

    public MetodologiaService(MetodologiaRepository metodologiaRepository) {
        this.metodologiaRepository = metodologiaRepository;
    }

    public List<Metodologia> obtenerTodas() {
        return metodologiaRepository.findAll();
    }

    public Optional<Metodologia> obtenerPorId(Long id) {
        return metodologiaRepository.findById(id);
    }

    public Metodologia guardar(Metodologia metodologia) {
        return metodologiaRepository.save(metodologia);
    }

    public void eliminar(Long id) {
        metodologiaRepository.deleteById(id);
    }
}

