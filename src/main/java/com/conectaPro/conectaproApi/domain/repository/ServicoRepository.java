package com.conectaPro.conectaproApi.domain.repository;

import com.conectaPro.conectaproApi.domain.entity.servico.Servico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ServicoRepository extends JpaRepository<Servico, Long> {

    @Query("SELECT so FROM Servico so WHERE (:nome IS NULL OR so.nome LIKE %:nome%) " +
            "AND (:localizacao IS NULL OR so.localizacao LIKE %:localizacao%)")
    List<Servico> procuraFiltrada(
            @Param("nome") String nome,
            @Param("localizacao") String localizacao
    );

}
