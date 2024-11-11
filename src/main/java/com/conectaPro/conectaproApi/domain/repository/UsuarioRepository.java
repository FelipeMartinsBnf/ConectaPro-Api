package com.conectaPro.conectaproApi.domain.repository;

import com.conectaPro.conectaproApi.domain.entity.usuario.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

}
