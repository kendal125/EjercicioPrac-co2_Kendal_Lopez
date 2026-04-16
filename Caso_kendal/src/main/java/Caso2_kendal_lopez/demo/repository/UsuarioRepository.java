/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Caso2_kendal_lopez.demo.repository;

import Caso2_kendal_lopez.demo.domain.Usuario;
import Caso2_kendal_lopez.demo.domain.Rol;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Usuario findByEmail(String email);
    List<Usuario> findByRol(Rol rol);
    List<Usuario> findByActivo(Boolean activo);
}
