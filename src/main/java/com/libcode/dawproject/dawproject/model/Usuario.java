package com.libcode.dawproject.dawproject.model;

import jakarta.persistence.*;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name = "usuarios")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String nombre;

    @Column(unique = true, length = 100)
    private String correo;

    @Column(nullable = false, length = 20)
    private String rol;

    // Relación con Proyecto (1 Usuario puede tener muchos proyectos)
    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Proyecto> proyectos;

    // Relación con Tarea (1 Usuario puede tener muchas tareas)
    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference("usuario-tarea")
    private List<Tarea> tareas;

    // Constructor vacío requerido por JPA
    public Usuario() {}

    // Constructor con parámetros
    public Usuario(String nombre, String correo, String rol) {
        this.nombre = nombre;
        this.correo = correo;
        this.rol = rol;
    }

    // =============================
    //            Getters
    // =============================

    public Long getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getCorreo() {
        return correo;
    }

    public String getRol() {
        return rol;
    }

    public List<Proyecto> getProyectos() {
        return proyectos;
    }

    public List<Tarea> getTareas() {
        return tareas;
    }

    // =============================
    //            Setters
    // =============================

    public void setId(Long id) {
        this.id = id;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public void setProyectos(List<Proyecto> proyectos) {
        this.proyectos = proyectos;
    }

    public void setTareas(List<Tarea> tareas) {
        this.tareas = tareas;
    }
}
