package com.libcode.dawproject.dawproject.model;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "metodologias")
public class Metodologia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false, length = 50)
    private String nombre;

    // Relación con Proyecto (1 Metodología puede tener muchos proyectos)
    @OneToMany(mappedBy = "metodologia", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Proyecto> proyectos;

    // Constructor vacío requerido por JPA
    public Metodologia() {}

    // Constructor con parámetros
    public Metodologia(String nombre) {
        this.nombre = nombre;
    }

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public List<Proyecto> getProyectos() {
        return proyectos;
    }

    public void setProyectos(List<Proyecto> proyectos) {
        this.proyectos = proyectos;
    }
}
