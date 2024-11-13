package com.conectaPro.conectaproApi.domain.entity.servico;

import com.conectaPro.conectaproApi.domain.entity.usuario.Usuario;
import com.conectaPro.conectaproApi.domain.entity.usuario.UsuarioDto;
import com.conectaPro.conectaproApi.domain.repository.UsuarioRepository;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@AllArgsConstructor
public class ServicoDetalhadoDto {
    private Long id;
    private String nome;
    private String descricao;
    private String localizacao;
    private Boolean remoto;
    private List<String> imagens;
    private List<String> tags;
    private LocalDateTime dataCriacao;
    private UsuarioDto autor;

    public  ServicoDetalhadoDto(Servico servico){
        this.id = servico.getId();
        this.nome = servico.getNome();
        this.descricao = servico.getDescricao();
        this.localizacao = servico.getLocalizacao();
        this.remoto = servico.getRemoto();
        this.imagens = servico.getImagens();
        this.tags = servico.getTags();
        this.dataCriacao = servico.getDataCriacao();
        this.autor = new UsuarioDto(servico.getUsuario());
    }
}
