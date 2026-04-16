/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Caso2_kendal_lopez.demo.controllers;


import Caso2_kendal_lopez.demo.service.EventoService;
import Caso2_kendal_lopez.demo.service.RolService;
import Caso2_kendal_lopez.demo.service.UsuarioService;
import Caso2_kendal_lopez.demo.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;

@Controller
@RequestMapping("/consultas")
public class ConsultaController {

    @Autowired
    private EventoService eventoService;

    @Autowired
    private RolService rolService;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @GetMapping
    public String mostrarConsultas(Model model) {
        model.addAttribute("roles", rolService.listarTodos());
        model.addAttribute("totalActivos", eventoService.contarActivos());
        return "consultas/index";
    }

    @GetMapping("/por-estado")
    public String porEstado(@RequestParam Boolean activo, Model model) {
        model.addAttribute("resultados", eventoService.buscarPorEstado(activo));
        model.addAttribute("roles", rolService.listarTodos());
        model.addAttribute("totalActivos", eventoService.contarActivos());
        return "consultas/index";
    }

    @GetMapping("/por-fechas")
    public String porFechas(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate inicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fin,
            Model model) {
        model.addAttribute("resultados", eventoService.buscarPorRangoFechas(inicio, fin));
        model.addAttribute("roles", rolService.listarTodos());
        model.addAttribute("totalActivos", eventoService.contarActivos());
        return "consultas/index";
    }

    @GetMapping("/por-nombre")
    public String porNombre(@RequestParam String nombre, Model model) {
        model.addAttribute("resultados", eventoService.buscarPorNombre(nombre));
        model.addAttribute("roles", rolService.listarTodos());
        model.addAttribute("totalActivos", eventoService.contarActivos());
        return "consultas/index";
    }

    @GetMapping("/usuarios-por-rol")
    public String usuariosPorRol(@RequestParam Long rolId, Model model) {
        rolService.buscarPorId(rolId).ifPresent(rol -> {
            model.addAttribute("usuariosResultado", usuarioRepository.findByRol(rol));
        });
        model.addAttribute("roles", rolService.listarTodos());
        model.addAttribute("totalActivos", eventoService.contarActivos());
        return "consultas/index";
    }
}
