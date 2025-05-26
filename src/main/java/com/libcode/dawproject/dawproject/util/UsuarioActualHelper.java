package com.libcode.dawproject.dawproject.util;

import com.libcode.dawproject.dawproject.model.Usuario;
import com.libcode.dawproject.dawproject.repository.UsuarioRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class UsuarioActualHelper {

    private final UsuarioRepository usuarioRepository;

    public UsuarioActualHelper(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public Usuario obtenerUsuarioActual() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !(authentication.getPrincipal() instanceof OidcUser)) {
            throw new RuntimeException("Usuario no autenticado o no es OIDC");
        }

        OidcUser oidcUser = (OidcUser) authentication.getPrincipal();
        String email = oidcUser.getEmail();

        Optional<Usuario> usuarioOpt = usuarioRepository.findByCorreo(email);
        return usuarioOpt.orElseThrow(() ->
            new RuntimeException("No se encontró un usuario con el correo: " + email)
        );
    }
}
