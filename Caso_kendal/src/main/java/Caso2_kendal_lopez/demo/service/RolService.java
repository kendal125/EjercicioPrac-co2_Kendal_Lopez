/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Caso2_kendal_lopez.demo.service;

import Caso2_kendal_lopez.demo.domain.Rol;
import Caso2_kendal_lopez.demo.repository.RolRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class RolService {

    @Autowired
    private RolRepository rolRepository;

    public List<Rol> listarTodos() { return rolRepository.findAll(); }

    public Optional<Rol> buscarPorId(Long id) { return rolRepository.findById(id); }

    public Rol guardar(Rol rol) { return rolRepository.save(rol); }

    public void eliminar(Long id) { rolRepository.deleteById(id); }
}