/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Caso2_kendal_lopez.demo.service;

import Caso2_kendal_lopez.demo.domain.Usuario;
import Caso2_kendal_lopez.demo.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private CorreoService correoService;

    public List<Usuario> listarTodos() {
        return usuarioRepository.findAll();
    }

    public Optional<Usuario> buscarPorId(Long id) {
        return usuarioRepository.findById(id);
    }

    public Usuario guardar(Usuario usuario) {
        // TRUCO PARA EDICIÓN: Si estamos editando y no se subió foto nueva, mantenemos la anterior
        if (usuario.getId() != null) {
            Usuario usuarioExistente = usuarioRepository.findById(usuario.getId()).orElse(null);
            if (usuarioExistente != null && (usuario.getFoto() == null || usuario.getFoto().isEmpty())) {
                usuario.setFoto(usuarioExistente.getFoto());
            }
            // También mantenemos la contraseña si el campo viene vacío (opcional)
            if (usuarioExistente != null && (usuario.getPassword() == null || usuario.getPassword().isEmpty())) {
                usuario.setPassword(usuarioExistente.getPassword());
            }
        }

        Usuario guardado = usuarioRepository.save(usuario);
        
        // Solo enviamos correo si es un usuario NUEVO (id es null antes de guardar)
        // Para evitar que llegue un correo cada vez que editamos una foto.
        if (usuario.getId() == null) {
            try {
                correoService.enviarBienvenida(guardado.getEmail(), guardado.getNombre());
            } catch (Exception e) {
                System.out.println("No se pudo enviar correo: " + e.getMessage());
            }
        }
        
        return guardado;
    }

    public void eliminar(Long id) {
        usuarioRepository.deleteById(id);
    }

    public Usuario findByEmail(String email) {
        return usuarioRepository.findByEmail(email);
    }
}