package com.conectaPro.conectaproApi.repository;

import com.conectaPro.conectaproApi.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

}
