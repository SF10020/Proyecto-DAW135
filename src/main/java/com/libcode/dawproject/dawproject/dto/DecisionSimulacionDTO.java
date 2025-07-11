package com.libcode.dawproject.dawproject.dto;

import java.math.BigDecimal;

public class DecisionSimulacionDTO {
    private Long idDecision;
    private String descripcion;
    private Long idSimulacion;
    private BigDecimal costoEstimado;
    private String nombreProyecto;
    private String prioridad;

    public DecisionSimulacionDTO(Long idDecision, String descripcion, Long idSimulacion, BigDecimal costoEstimado, String nombreProyecto, String prioridad) {
        this.idDecision = idDecision;
        this.descripcion = descripcion;
        this.idSimulacion = idSimulacion;
        this.costoEstimado = costoEstimado;
        this.nombreProyecto = nombreProyecto;
        this.prioridad = prioridad;
    }

    // setter y getter para prioridad
    public String getPrioridad() {
        return prioridad;
    }
    public void setPrioridad(String prioridad) {
        this.prioridad = prioridad;
    }

    // Getters y setters
    public Long getIdDecision() {
        return idDecision;
    }

    public void setIdDecision(Long idDecision) {
        this.idDecision = idDecision;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Long getIdSimulacion() {
        return idSimulacion;
    }

    public void setIdSimulacion(Long idSimulacion) {
        this.idSimulacion = idSimulacion;
    }

    public BigDecimal getCostoEstimado() {
        return costoEstimado;
    }

    public void setCostoEstimado(BigDecimal costoEstimado) {
        this.costoEstimado = costoEstimado;
    }

    public String getNombreProyecto() {
        return nombreProyecto;
    }

    public void setNombreProyecto(String nombreProyecto) {
        this.nombreProyecto = nombreProyecto;
    }
}
