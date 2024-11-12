package com.conectaPro.conectaproApi.controller;

import com.conectaPro.conectaproApi.domain.entity.servico.Servico;
import com.conectaPro.conectaproApi.domain.entity.servico.ServicoAttDto;
import com.conectaPro.conectaproApi.domain.entity.servico.ServicoDetalhadoDto;
import com.conectaPro.conectaproApi.domain.entity.servico.ServicoForm;
import com.conectaPro.conectaproApi.domain.entity.usuario.TipoUsuario;
import com.conectaPro.conectaproApi.domain.entity.usuario.Usuario;
import com.conectaPro.conectaproApi.domain.repository.ServicoRepository;
import com.conectaPro.conectaproApi.domain.repository.UsuarioRepository;
import com.conectaPro.conectaproApi.infra.filtro.FiltroForm;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/api/servico")
@SecurityRequirement(name = "bearer-key")
public class ServicoController {

    @Autowired
    private ServicoRepository servicoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @PostMapping
    public ResponseEntity cadastrarServico(@RequestBody ServicoForm form, UriComponentsBuilder uriBuilder){
        //Verificar se o usuario Existe e se ele é um Prestador
        Long idUsuario = form.id_autor();
        Optional<Usuario> servicoOptional = usuarioRepository.findById(idUsuario);
        if(servicoOptional.isPresent() && servicoOptional.get().getTipoUsuario() == TipoUsuario.Prestador){
            Servico servico = new Servico(form, servicoOptional.get());
            servicoRepository.save(servico);
            URI uri = uriBuilder.path("/api/servico/{id}").buildAndExpand(servico.getId()).toUri();
            return ResponseEntity.created(uri).body(new ServicoDetalhadoDto(servico));
        }else {
            return  ResponseEntity.badRequest().build();
        }
    }

    @GetMapping
    public ResponseEntity<List<ServicoDetalhadoDto>> listarServicos(){
        return ResponseEntity.ok().body(
                servicoRepository.findAll().stream().map(
                        ServicoDetalhadoDto:: new
                ).collect(Collectors.toList())
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<ServicoDetalhadoDto> detalharServico(@PathVariable Long id){
        return servicoRepository.findById(id).map(
                servico -> ResponseEntity.ok().body(new ServicoDetalhadoDto(servico))
        ).orElse(ResponseEntity.badRequest().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity AtualizarServico(@PathVariable Long id, @RequestBody ServicoAttDto servicoAttDto){
        return servicoRepository.findById(id).map(
                servico -> {
                    servico.setNome(servicoAttDto.nome());
                    servico.setDescricao(servicoAttDto.descricao());
                    servico.setLocalizacao(servicoAttDto.localizacao());
                    servico.setRemoto(servicoAttDto.remoto());
                    servico.setImagens(servicoAttDto.imagens());
                    servico.setTags(servicoAttDto.tags());
                    servicoRepository.save(servico);
                    return ResponseEntity.ok().build();
                }
        ).orElse(
                ResponseEntity.notFound().build()
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deletarServico(@PathVariable Long id) {
        return servicoRepository.findById(id).map(
                servico -> {
                    servicoRepository.deleteById(id);
                    return  ResponseEntity.ok().build();
                }
        ).orElse(ResponseEntity.badRequest().build());
    }

    @GetMapping("/filtrar")
    public ResponseEntity filtrarServicos(@RequestBody FiltroForm form){
        if(form.getTags() != null){
            List<Servico> resultadoTags = servicoRepository.procuraTags(form.getTags());
            List<Servico> resultadoFiltros = servicoRepository.procuraFiltrada(form.getNome(), form.getLocalizacao());
            List<Long> idsTags = new ArrayList<>();
            for(Servico servico: resultadoTags){
                idsTags.add(servico.getId());
            }

            List<Servico> resultado = new ArrayList<>();
            for (Servico servico : resultadoFiltros){
                if(idsTags.contains(servico.getId())){
                    resultado.add(servico);
                }
            }

            List<ServicoDetalhadoDto> resultadoDto = resultado.stream().map(ServicoDetalhadoDto:: new).collect(Collectors.toList());
            return  ResponseEntity.ok().body(resultadoDto);

        }
        return ResponseEntity.ok().body(servicoRepository.procuraFiltrada(form.getNome(), form.getLocalizacao()));
    }
}
