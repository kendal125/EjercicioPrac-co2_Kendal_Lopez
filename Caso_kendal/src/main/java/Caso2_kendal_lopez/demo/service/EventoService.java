/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Caso2_kendal_lopez.demo.service;

import Caso2_kendal_lopez.demo.domain.Evento;
import Caso2_kendal_lopez.demo.repository.EventoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class EventoService {

    @Autowired
    private EventoRepository eventoRepository;

    public List<Evento> listarTodos() { return eventoRepository.findAll(); }

    public Optional<Evento> buscarPorId(Long id) { return eventoRepository.findById(id); }

    public Evento guardar(Evento evento) { return eventoRepository.save(evento); }

    public void eliminar(Long id) { eventoRepository.deleteById(id); }

    public List<Evento> buscarPorEstado(Boolean activo) {
        return eventoRepository.findByActivo(activo);
    }

    public List<Evento> buscarPorRangoFechas(LocalDate inicio, LocalDate fin) {
        return eventoRepository.findByFechaBetween(inicio, fin);
    }

    public List<Evento> buscarPorNombre(String nombre) {
        return eventoRepository.findByNombreContainingIgnoreCase(nombre);
    }

    public long contarActivos() {
        return eventoRepository.countByActivo(true);
    }
}