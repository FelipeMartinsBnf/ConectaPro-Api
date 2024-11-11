package com.conectaPro.conectaproApi.controller;

import com.conectaPro.conectaproApi.domain.entity.usuario.Usuario;
import com.conectaPro.conectaproApi.domain.entity.usuario.UsuarioAttForm;
import com.conectaPro.conectaproApi.domain.entity.usuario.UsuarioDto;
import com.conectaPro.conectaproApi.domain.entity.usuario.UsuarioForm;
import com.conectaPro.conectaproApi.domain.repository.UsuarioRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Optional;

@RestController
@RequestMapping("/api/usuario")
public class UsuariosController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @PostMapping
    @Transactional
    public ResponseEntity cadastrarUsuario(@RequestBody @Valid UsuarioForm form){
        usuarioRepository.save(new Usuario(form));
        return ResponseEntity.ok().build();
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<UsuarioDto> mostrarUsuario(@PathVariable Long id){
        Optional<Usuario> resposta = usuarioRepository.findById(id);
        return usuarioRepository.findById(id)
                .map(user -> ResponseEntity.ok(new UsuarioDto(user)))
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity atualizarUsuario(@PathVariable Long id , @RequestBody @Valid UsuarioAttForm form){
        return  usuarioRepository.findById(id).map(user -> {
            user.setNome(form.nome());
            user.setSenha(form.senha());
            user.setTipoUsuario(form.tipo());
            usuarioRepository.save(user);
            return ResponseEntity.noContent().build();
        })
                .orElse(ResponseEntity.notFound().build());
    }
}
