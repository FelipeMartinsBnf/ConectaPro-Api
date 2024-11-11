package com.conectaPro.conectaproApi.domain.entity.servico;

import java.util.List;

public record ServicoAttDto(
        String nome,
        String descricao,
        String localizacao,
        Boolean remoto,
        List<String> imagens,
        List<String> tags
) {
}
