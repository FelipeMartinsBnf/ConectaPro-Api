package com.conectaPro.conectaproApi.domain.repository;


import com.conectaPro.conectaproApi.domain.entity.contrato.Contrato;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContratoRepository extends JpaRepository<Contrato, Long> {
}
