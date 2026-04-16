/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Caso2_kendal_lopez.demo.repository;

import Caso2_kendal_lopez.demo.domain.Evento;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDate;
import java.util.List;

public interface EventoRepository extends JpaRepository<Evento, Long> {
    List<Evento> findByActivo(Boolean activo);
    List<Evento> findByFechaBetween(LocalDate inicio, LocalDate fin);
    List<Evento> findByNombreContainingIgnoreCase(String nombre);
    long countByActivo(Boolean activo);
}