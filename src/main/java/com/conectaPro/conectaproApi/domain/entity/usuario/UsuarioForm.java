package com.conectaPro.conectaproApi.domain.entity.usuario;

public record UsuarioForm(
        String nome, String email, String senha, String cpf, TipoUsuario tipo) {
}
