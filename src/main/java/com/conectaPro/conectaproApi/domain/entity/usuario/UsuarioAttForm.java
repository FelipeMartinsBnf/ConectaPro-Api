package com.conectaPro.conectaproApi.domain.entity.usuario;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UsuarioAttForm(@NotBlank String nome, @NotBlank @Size(min = 6) String senha, @NotBlank TipoUsuario tipo) {
}
