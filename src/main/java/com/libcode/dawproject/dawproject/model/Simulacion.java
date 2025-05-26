package com.libcode.dawproject.dawproject.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "simulaciones")
public class Simulacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "proyecto_id", nullable = false)
    private Proyecto proyecto;

    @Column(name = "tiempo_estimado", nullable = false)
    private int tiempoEstimado; // en días o sprints

    @Column(name = "costo_estimado", nullable = false, precision = 10, scale = 2)
    private BigDecimal costoEstimado;

    @Column(name = "calidad_estimada", nullable = false)
    private int calidadEstimada; // por ejemplo: 0 - 100%

    @Column(name = "fecha_simulacion")
    private LocalDateTime fechaSimulacion;

    @OneToMany(mappedBy = "simulacion", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DecisionSimulacion> decisiones;

    @OneToMany(mappedBy = "simulacion", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference("simulacion-tarea")
    private List<Tarea> tareas;

    // Constructor vacío
    public Simulacion() {
        this.fechaSimulacion = LocalDateTime.now();
    }

    // Constructor con parámetros
    public Simulacion(Proyecto proyecto, int tiempoEstimado, BigDecimal costoEstimado, int calidadEstimada) {
        this.proyecto = proyecto;
        this.tiempoEstimado = tiempoEstimado;
        this.costoEstimado = costoEstimado;
        this.calidadEstimada = calidadEstimada;
        this.fechaSimulacion = LocalDateTime.now();
    }

// ============================
    //         Getters
    // ============================

    public Long getId() {
        return id;
    }

    public Proyecto getProyecto() {
        return proyecto;
    }

    public int getTiempoEstimado() {
        return tiempoEstimado;
    }

    public BigDecimal getCostoEstimado() {
        return costoEstimado;
    }

    public int getCalidadEstimada() {
        return calidadEstimada;
    }

    public LocalDateTime getFechaSimulacion() {
        return fechaSimulacion;
    }

    public List<DecisionSimulacion> getDecisiones() {
        return decisiones;
    }

    public List<Tarea> getTareas() {
        return tareas;
    }

    // ============================
    //         Setters
    // ============================

    public void setId(Long id) {
        this.id = id;
    }

    public void setProyecto(Proyecto proyecto) {
        this.proyecto = proyecto;
    }

    public void setTiempoEstimado(int tiempoEstimado) {
        this.tiempoEstimado = tiempoEstimado;
    }

    public void setCostoEstimado(BigDecimal costoEstimado) {
        this.costoEstimado = costoEstimado;
    }

    public void setCalidadEstimada(int calidadEstimada) {
        this.calidadEstimada = calidadEstimada;
    }

    public void setFechaSimulacion(LocalDateTime fechaSimulacion) {
        this.fechaSimulacion = fechaSimulacion;
    }

    public void setDecisiones(List<DecisionSimulacion> decisiones) {
        this.decisiones = decisiones;
    }

    public void setTareas(List<Tarea> tareas) {
        this.tareas = tareas;
    }
}