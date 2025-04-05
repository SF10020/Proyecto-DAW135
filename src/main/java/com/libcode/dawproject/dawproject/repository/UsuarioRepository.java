package com.libcode.dawproject.dawproject.repository;

import com.libcode.dawproject.dawproject.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    // Puedes agregar métodos personalizados:
    // Optional<Usuario> findByCorreo(String correo);
}

