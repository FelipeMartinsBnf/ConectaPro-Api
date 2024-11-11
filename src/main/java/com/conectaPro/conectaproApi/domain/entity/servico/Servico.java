package com.conectaPro.conectaproApi.domain.entity.servico;

import com.conectaPro.conectaproApi.domain.entity.usuario.Usuario;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.awt.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "servico")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Servico {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id ;
    private String nome;
    private String descricao;
    private String localizacao;
    private Boolean remoto;
    @ElementCollection
    private List<String> imagens;
    @ElementCollection
    private List<String> tags;
    @Column(name = "data_criacao")
    private LocalDateTime dataCriacao = LocalDateTime.now();
    @ManyToOne
    private Usuario usuario;

    public Servico(ServicoForm form, Usuario autor){
        this.nome = form.nome();
        this.descricao = form.descricao();
        this.localizacao = form.localizacao();
        this.remoto = form.remoto();
        this.imagens = form.imagens();
        this.tags = form.tags();
        this.usuario = autor;
    }
}
