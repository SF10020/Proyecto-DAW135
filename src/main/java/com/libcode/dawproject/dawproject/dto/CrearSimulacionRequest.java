package com.libcode.dawproject.dawproject.dto;

import java.math.BigDecimal;

public class CrearSimulacionRequest {

    private Long proyectoId;
    private int tiempoEstimado;
    private BigDecimal costoEstimado;
    private int calidadEstimada;

    public Long getProyectoId() {
        return proyectoId;
    }

    public void setProyectoId(Long proyectoId) {
        this.proyectoId = proyectoId;
    }

    public int getTiempoEstimado() {
        return tiempoEstimado;
    }

    public void setTiempoEstimado(int tiempoEstimado) {
        this.tiempoEstimado = tiempoEstimado;
    }

    public BigDecimal getCostoEstimado() {
        return costoEstimado;
    }

    public void setCostoEstimado(BigDecimal costoEstimado) {
        this.costoEstimado = costoEstimado;
    }

    public int getCalidadEstimada() {
        return calidadEstimada;
    }

    public void setCalidadEstimada(int calidadEstimada) {
        this.calidadEstimada = calidadEstimada;
    }
}

