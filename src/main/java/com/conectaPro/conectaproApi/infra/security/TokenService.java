package com.conectaPro.conectaproApi.infra.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;

import com.conectaPro.conectaproApi.domain.entity.usuario.Usuario;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
@Service // Indica que esta classe é um serviço do Spring, gerenciado pelo contexto da aplicação, permitindo a injeção onde necessário.
public class TokenService {

    @Value("${api.security.secret}") // Injeta o valor do segredo de segurança definido nas configurações da aplicação (application.properties).
    private String secret;

    public String gerarToken(Usuario usuario) { // Método para gerar um token JWT com informações do usuário.
        try {
            var algoritmo = Algorithm.HMAC256(secret); // Define o algoritmo de assinatura HMAC256 com o segredo para garantir a integridade e autenticidade do token.
            return JWT.create()
                    .withIssuer("API Conecta Pro") // Define o emissor do token, útil para verificar a origem durante a validação.
                    .withSubject(usuario.getEmail()) // Define o "subject" do token como o email do usuário, identificando o proprietário do token.
                    .withExpiresAt(dataExpiracao()) // Define a data de expiração do token para limitar seu tempo de validade.
                    .sign(algoritmo); // Assina o token com o algoritmo definido.
        } catch (JWTCreationException exception) { // Captura qualquer erro na criação do token JWT.
            throw new RuntimeException("Erro ao gerar token JWT", exception); // Lança uma exceção com uma mensagem de erro se a geração falhar.
        }
    }

    public String getSubject(String tokenJWT) { // Método para extrair o "subject" (neste caso, o email) de um token JWT.
        try {
            var algoritmo = Algorithm.HMAC256(secret); // Usa o mesmo algoritmo e segredo para verificar a assinatura do token.
            return JWT.require(algoritmo)
                    .withIssuer("API Conecta Pro") // Verifica se o token foi emitido pelo emissor correto.
                    .build()
                    .verify(tokenJWT) // Verifica a validade do token (assinatura, emissor e expiração).
                    .getSubject(); // Retorna o "subject" do token, que identifica o usuário.
        } catch (JWTVerificationException exception) { // Captura qualquer erro de verificação do token JWT.
            throw new RuntimeException("Token JWT inválido ou expirado!"); // Lança uma exceção se o token for inválido ou expirado.
        }
    }

    private Instant dataExpiracao() { // Define a data de expiração do token JWT.
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00")); // Configura o token para expirar em 2 horas com o fuso horário -03:00.
    }
}
