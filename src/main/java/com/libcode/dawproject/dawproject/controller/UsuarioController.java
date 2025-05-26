package com.libcode.dawproject.dawproject.controller;

import com.libcode.dawproject.dawproject.model.Usuario;
import com.libcode.dawproject.dawproject.service.UsuarioService;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

// @RestController
@Controller
@PreAuthorize("hasAuthority('ROLE_Admin')")
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping("/usuarios")
    public String listarUsuarios(Model model) {
        List<Usuario> usuarios = usuarioService.obtenerTodos();
        model.addAttribute("usuarios", usuarios);
        return "usuarios/lista"; 
    }

    @PostMapping("/crear-usuario")
    public String crearUsuario(@ModelAttribute Usuario usuario) {
        usuarioService.guardar(usuario);
        return "redirect:/usuarios";
    }

    @PostMapping("/editar-usuario")
    public String editarUsuario(@ModelAttribute Usuario usuario) {
        usuarioService.guardar(usuario);
        return "redirect:/usuarios";
    }

    @PostMapping("/eliminar-usuario")
    public String eliminarUsuario(@RequestParam("id") Long id) {
        usuarioService.eliminar(id);
        return "redirect:/usuarios";
    }

}


// @Controller
// @RequestMapping("/usuarios")
// public class UsuarioController {

//     private final UsuarioService usuarioService;

//     public UsuarioController(UsuarioService usuarioService) {
//         this.usuarioService = usuarioService;
//     }

//     // Ruta: http://localhost:8080/usuarios
//     @GetMapping
//     public String listarUsuarios(Model model) {
//         List<Usuario> usuarios = usuarioService.obtenerTodos();
//         model.addAttribute("usuarios", usuarios);
//         return "usuarios/lista"; // hace referencia a templates/Usuarios/lista.html
//     }
// }