package com.conectaPro.conectaproApi.infra.filtro;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
public class FiltroForm {
    private String nome;
    private String localizacao;
    private List<String> tags;
}
