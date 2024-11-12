package com.conectaPro.conectaproApi.infra.security;

import com.conectaPro.conectaproApi.domain.repository.UsuarioRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
@Component // Indica que esta classe é um componente gerenciado pelo Spring, permitindo sua injeção em outras partes da aplicação.
public class SecurityFilter extends OncePerRequestFilter { // Extende OncePerRequestFilter, garantindo que este filtro seja executado uma vez por requisição.

    @Autowired
    private TokenService tokenService; // Injeta o TokenService para validar e obter informações do token JWT.

    @Autowired
    private UsuarioRepository usuarioRepository; // Injeta o UsuarioRepository para buscar os dados do usuário autenticado.

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        var tokenJWT = recuperarToken(request); // Recupera o token JWT da requisição.

        if (tokenJWT != null) { // Se o token não for nulo, procede com a autenticação.
            var subject = tokenService.getSubject(tokenJWT); // Obtém o email do usuário (subject) a partir do token JWT.
            var usuario = usuarioRepository.findByEmail(subject); // Busca o usuário no banco de dados usando o email.

            var auth = new UsernamePasswordAuthenticationToken(usuario, null, usuario.getAuthorities()); // Cria o objeto de autenticação do Spring Security.
            SecurityContextHolder.getContext().setAuthentication(auth); // Define o usuário autenticado no contexto de segurança.
        }

        filterChain.doFilter(request, response); // Continua a execução da cadeia de filtros.
    }

    private String recuperarToken(HttpServletRequest request) { // Método auxiliar para extrair o token JWT do cabeçalho da requisição.
        var authorizationHeader = request.getHeader("Authorization"); // Obtém o cabeçalho Authorization.
        if (authorizationHeader != null) {
            return authorizationHeader.replace("Bearer ", "").trim(); // Remove o prefixo "Bearer " e retorna apenas o token.
        }

        return null; // Retorna null se o cabeçalho Authorization estiver ausente.
    }
}

