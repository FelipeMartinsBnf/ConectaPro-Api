package com.conectaPro.conectaproApi.infra.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
@Configuration // Indica que essa classe é uma classe de configuração do Spring e que ela define beans e configurações para a aplicação.
@EnableWebSecurity // Habilita o suporte do Spring Security para definir configurações de segurança personalizadas.
public class SecurityConfigurations { // Classe de configuração do Spring Security responsável por definir as regras de segurança da aplicação.

    @Autowired
    private SecurityFilter securityFilter; // Injeção de dependência do filtro de segurança personalizado, que processará as requisições para aplicar regras de segurança específicas.

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception { // Método que define e configura a cadeia de filtros de segurança.
        return http.csrf(csrf -> csrf.disable()) // Desabilita a proteção contra CSRF (Cross-Site Request Forgery) porque o CSRF não é necessário em APIs REST que usam JWT, uma vez que são stateless.
                .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // Define a política de criação de sessão como STATELESS, indicando que não serão mantidas sessões para cada usuário. Essa configuração é comum em APIs que utilizam JWT para autenticação.
                .authorizeHttpRequests(req -> { // Inicia a configuração das regras de autorização para as requisições HTTP.
                    req.requestMatchers("/login").permitAll(); // Permite o acesso à rota de login sem necessidade de autenticação, possibilitando que usuários façam login.
                    req.requestMatchers("/v3/api-docs/**", "/swagger-ui.html", "/swagger-ui/**").permitAll(); // Permite o acesso às rotas de documentação da API (Swagger) sem autenticação, para que sejam acessíveis publicamente.
                    req.anyRequest().authenticated(); // Exige que todas as outras requisições sejam autenticadas, aumentando a segurança das demais rotas da aplicação.
                })
                .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class) // Adiciona o filtro de segurança personalizado antes do filtro padrão de autenticação baseado em nome de usuário e senha. Isso permite customizar a lógica de autenticação.
                .build(); // Finaliza a configuração da cadeia de filtros e retorna uma instância de SecurityFilterChain para ser usada pelo Spring Security.
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception { // Método que define o AuthenticationManager como um bean.
        return configuration.getAuthenticationManager(); // Obtém o gerenciador de autenticação padrão configurado pelo Spring e o disponibiliza como um bean para ser utilizado em toda a aplicação, facilitando a injeção e a reutilização.
    }

    @Bean
    public PasswordEncoder passwordEncoder() { // Método que define um PasswordEncoder como um bean para codificação de senhas.
        return new BCryptPasswordEncoder(); // Utiliza o BCryptPasswordEncoder, que é um algoritmo seguro de codificação de senha, tornando as senhas mais seguras contra ataques de força bruta.
    }

}
