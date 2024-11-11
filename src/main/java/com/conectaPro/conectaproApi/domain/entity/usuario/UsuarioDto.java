package com.conectaPro.conectaproApi.domain.entity.usuario;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioDto {
    private String nome;
    private String email;
    private String cpf;
    private TipoUsuario tipo;
    private LocalDateTime dataCriacao;
    private Float avalicao;

    public UsuarioDto(Usuario usuario){
        this.nome = usuario.getNome();
        this.email = usuario.getNome();
        this.cpf = usuario.getCpf();
        this.tipo = usuario.getTipoUsuario();
        this.dataCriacao = usuario.getDataCriacao();
        this.avalicao = usuario.getAvalicaoMedia();
    }
}