package com.conectaPro.conectaproApi.domain.entity.servico;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record ServicoAttDto(
        @NotBlank
        String nome,
        @NotBlank
        String descricao,
        @NotBlank
        String localizacao,
        @NotBlank
        Boolean remoto,
        @NotNull
        List<String> imagens,
        @NotBlank
        List<String> tags
) {
}
