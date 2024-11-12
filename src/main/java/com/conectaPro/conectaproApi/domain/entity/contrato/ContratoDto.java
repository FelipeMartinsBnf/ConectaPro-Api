package com.conectaPro.conectaproApi.domain.entity.contrato;

import com.conectaPro.conectaproApi.domain.entity.servico.ServicoDetalhadoDto;
import com.conectaPro.conectaproApi.domain.entity.usuario.UsuarioDto;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ContratoDto {
    private Long id;
    private ServicoDetalhadoDto servico;
    private UsuarioDto contratador;
    private LocalDateTime data_inicio;
    private StatusContrato status;

    public ContratoDto(Contrato contrato){
        this.id =  contrato.getId();
        this.servico = new ServicoDetalhadoDto(contrato.getServico());
        this.contratador = new UsuarioDto(contrato.getContratador());
        this.data_inicio = contrato.getData_inicio();
        this.status = contrato.getStatus();
    }
}
