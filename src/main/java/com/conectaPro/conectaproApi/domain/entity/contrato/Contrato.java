package com.conectaPro.conectaproApi.domain.entity.contrato;

import com.conectaPro.conectaproApi.domain.entity.servico.Servico;
import com.conectaPro.conectaproApi.domain.entity.usuario.Usuario;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Contrato {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private Servico servico;
    @ManyToOne
    private Usuario contratador; // Alterado para ManyToOne
    private LocalDateTime data_inicio; // Correção de digitação
    @Enumerated(EnumType.STRING)
    private StatusContrato status;

    public Contrato(Usuario contratador, Servico servico){
        this.servico = servico;
        this.contratador = contratador;
        this.data_inicio = LocalDateTime.now();
        this.status = StatusContrato.Em_Andamento;
    }
}