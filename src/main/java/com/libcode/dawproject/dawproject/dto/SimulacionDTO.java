package com.libcode.dawproject.dawproject.dto;

public class SimulacionDTO {
    private Long id;
    private int tiempoEstimado;
    private double costoEstimado;
    private int calidadEstimada;
    private String fechaSimulacion;

    public SimulacionDTO() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public int getTiempoEstimado() { return tiempoEstimado; }
    public void setTiempoEstimado(int tiempoEstimado) { this.tiempoEstimado = tiempoEstimado; }
    public double getCostoEstimado() { return costoEstimado; }
    public void setCostoEstimado(double costoEstimado) { this.costoEstimado = costoEstimado; }
    public int getCalidadEstimada() { return calidadEstimada; }
    public void setCalidadEstimada(int calidadEstimada) { this.calidadEstimada = calidadEstimada; }
    public String getFechaSimulacion() { return fechaSimulacion; }
    public void setFechaSimulacion(String fechaSimulacion) { this.fechaSimulacion = fechaSimulacion; }
}
