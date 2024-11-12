package com.conectaPro.conectaproApi.controller;

import com.conectaPro.conectaproApi.domain.entity.usuario.Usuario;
import com.conectaPro.conectaproApi.infra.security.DadosAuthDto;
import com.conectaPro.conectaproApi.infra.security.DadosTokenJWT;
import com.conectaPro.conectaproApi.infra.security.TokenService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//Controlador da tela de autenticação /login
@RestController // Indica que esta classe é um controlador REST, responsável por expor endpoints da API para o login.
@RequestMapping("/login") // Define o caminho "/login" como a URL base para todos os endpoints dentro deste controlador.
public class AuthController {

    @Autowired
    private AuthenticationManager manager; // Injeta o AuthenticationManager, que será usado para autenticar as credenciais do usuário.

    @Autowired
    private TokenService tokenService; // Injeta o TokenService, responsável pela geração de tokens JWT após a autenticação.

    @PostMapping // Indica que este método responderá a requisições POST no endpoint "/login".
    @SecurityRequirement(name = "bearer-key") // Especifica que este endpoint exige um requisito de segurança "bearer-key", conforme definido na documentação de segurança da API.
    public ResponseEntity efetuarLogin(@RequestBody @Valid DadosAuthDto dados) { // Método para efetuar o login, recebendo as credenciais no corpo da requisição.
        var Authtoken = new UsernamePasswordAuthenticationToken(dados.email(), dados.senha()); // Cria um token de autenticação baseado no email e senha fornecidos pelo usuário.
        var authenticaon = manager.authenticate(Authtoken); // Autentica o usuário com o AuthenticationManager, verificando se as credenciais são válidas.

        var tokenJWT = tokenService.gerarToken((Usuario) authenticaon.getPrincipal()); // Gera um token JWT chamando o TokenService e passando o usuário autenticado.

        return ResponseEntity.ok(new DadosTokenJWT(tokenJWT)); // Retorna o token JWT em uma resposta HTTP 200 (OK), encapsulado em um objeto DadosTokenJWT.
    }

}

