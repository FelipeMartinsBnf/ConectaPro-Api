package com.conectaPro.conectaproApi.domain.entity.servico;

import jakarta.validation.constraints.*;
import jakarta.validation.constraints.Size;

import java.util.List;

public record ServicoForm(
        @NotBlank @Size(min = 5)
         String nome,
         @NotBlank
         String descricao,
         String localizacao,
         Boolean remoto,
         List<String>imagens,
         @NotBlank
         List<String> tags,
         @NotBlank
         Long id_autor
) {
}
