/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Caso2_kendal_lopez.demo.controllers;

import Caso2_kendal_lopez.demo.domain.Usuario;
import Caso2_kendal_lopez.demo.service.RolService;
import Caso2_kendal_lopez.demo.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Controller
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private RolService rolService;

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("usuarios", usuarioService.listarTodos());
        return "usuarios/lista";
    }

    @GetMapping("/nuevo")
    public String mostrarFormularioCrear(Model model) {
        model.addAttribute("usuario", new Usuario());
        model.addAttribute("roles", rolService.listarTodos());
        return "usuarios/formulario";
    }

    @PostMapping("/guardar")
    public String guardar(@ModelAttribute Usuario usuario, @RequestParam("archivo") MultipartFile imagen) {
        
        // --- LÓGICA PARA LA FOTO DE PERFIL ---
        if (!imagen.isEmpty()) {
            // Ruta donde se guardarán las imágenes (Asegúrate de que la carpeta static/img exista)
            Path directorioImagenes = Paths.get("src//main//resources//static//img");
            String rutaAbsoluta = directorioImagenes.toFile().getAbsolutePath();

            try {
                byte[] bytesImg = imagen.getBytes();
                // Usamos el nombre original del archivo subido
                Path rutaCompleta = Paths.get(rutaAbsoluta + "//" + imagen.getOriginalFilename());
                Files.write(rutaCompleta, bytesImg);

                // Seteamos el nombre del archivo en el atributo 'foto' de nuestra entidad
                usuario.setFoto(imagen.getOriginalFilename());

            } catch (IOException e) {
                System.out.println("Error al subir la imagen: " + e.getMessage());
            }
        } else {
            // Si no suben foto y es usuario nuevo, le dejamos la default
            if (usuario.getId() == null) {
                usuario.setFoto("default.png");
            }
        }
        // -------------------------------------

        usuarioService.guardar(usuario);
        return "redirect:/usuarios";
    }

    @GetMapping("/editar/{id}")
    public String mostrarFormularioEditar(@PathVariable Long id, Model model) {
        usuarioService.buscarPorId(id).ifPresent(u -> model.addAttribute("usuario", u));
        model.addAttribute("roles", rolService.listarTodos());
        return "usuarios/formulario";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Long id) {
        usuarioService.eliminar(id);
        return "redirect:/usuarios";
    }

    @GetMapping("/detalle/{id}")
    public String detalle(@PathVariable Long id, Model model) {
        usuarioService.buscarPorId(id).ifPresent(u -> model.addAttribute("usuario", u));
        return "usuarios/detalle";
    }
}