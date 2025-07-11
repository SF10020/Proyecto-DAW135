package com.libcode.dawproject.dawproject.model;

import jakarta.persistence.*;

@Entity
@Table(name = "decisiones_simulacion")
public class DecisionSimulacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "simulacion_id", nullable = false)
    private Simulacion simulacion;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String descripcion;

    @Column(length = 50) 
    private String prioridad;

    // Constructor vacío
    public DecisionSimulacion() {}

    // Constructor con campos
    public DecisionSimulacion(Simulacion simulacion, String descripcion, String prioridad) {
        this.simulacion = simulacion;
        this.descripcion = descripcion;
        this.prioridad = prioridad;
    }

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Simulacion getSimulacion() {
        return simulacion;
    }

    public void setSimulacion(Simulacion simulacion) {
        this.simulacion = simulacion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getPrioridad() {
        return prioridad;
    }

    public void setPrioridad(String prioridad) {
        this.prioridad = prioridad;
    }
}
