package com.conectaPro.conectaproApi.domain.entity.usuario;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UsuarioForm(
        @NotBlank
        String nome,
        @Email @NotBlank
        String email,
        @NotBlank @Size(min = 6)
        String senha,
        @NotBlank @Size(min = 11, max = 11)
        String cpf,
        @NotBlank
        TipoUsuario tipo) {
}
