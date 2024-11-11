package com.conectaPro.conectaproApi.domain.entity.usuario;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "usuario")
@Entity
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "nome")
    private String nome;

    @Column(name = "email")
    private String email;

    @Column(name = "senha")
    private String senha;

    @Column(name = "cpf")
    private String cpf;

    @Column(name = "tipo")
    private TipoUsuario tipoUsuario;

    @Column(name = "data_criacao")
    private LocalDateTime dataCriacao;

    @Column(name = "avaliacao_media")
    private Float avalicaoMedia;

    public Usuario(UsuarioForm form){
        this.nome = form.nome();
        this.email = form.email();
        this.senha = form.senha();
        this.cpf = form.cpf();
        this.tipoUsuario = form.tipo();
        this.dataCriacao = LocalDateTime.now();
        this.avalicaoMedia = 0.0F;
    }

}
