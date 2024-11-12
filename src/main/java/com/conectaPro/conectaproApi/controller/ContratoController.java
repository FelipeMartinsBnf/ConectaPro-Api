package com.conectaPro.conectaproApi.controller;

import com.conectaPro.conectaproApi.domain.entity.contrato.Contrato;
import com.conectaPro.conectaproApi.domain.entity.contrato.ContratoDto;
import com.conectaPro.conectaproApi.domain.entity.contrato.ContratoForm;
import com.conectaPro.conectaproApi.domain.entity.servico.Servico;
import com.conectaPro.conectaproApi.domain.entity.usuario.Usuario;
import com.conectaPro.conectaproApi.domain.repository.ContratoRepository;
import com.conectaPro.conectaproApi.domain.repository.ServicoRepository;
import com.conectaPro.conectaproApi.domain.repository.UsuarioRepository;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/contrato")
@SecurityRequirement(name = "bearer-key")
public class ContratoController {

    @Autowired
    private ContratoRepository contratoRepository;
    @Autowired
    private ServicoRepository servicoRepository;
    @Autowired
    private UsuarioRepository usuarioRepository;

    @PostMapping
    public ResponseEntity criarContrato(@RequestBody ContratoForm form){
        Optional<Usuario> contratador = usuarioRepository.findById(form.idContratador());
        Optional<Servico> servico = servicoRepository.findById(form.idServico());
        if(contratador.isPresent() && servico.isPresent()){
            contratoRepository.save(new Contrato(contratador.get(), servico.get()));
            return ResponseEntity.ok().build();
        }else {
            return  ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity mostrarContrato(@PathVariable Long id){
        return contratoRepository.findById(id).map(
                contrato -> ResponseEntity.ok().body(new ContratoDto(contrato))
        ).orElse(ResponseEntity.notFound().build());
    }
}
